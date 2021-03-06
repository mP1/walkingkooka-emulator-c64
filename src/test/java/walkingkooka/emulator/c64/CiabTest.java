/*
 * Copyright 2019 Miroslav Pokorny (github.com/mP1)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package walkingkooka.emulator.c64;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

public final class CiabTest extends CiaTestCase<Ciab> {

    // setBank..........................................................................................................

    @Test
    public void testSetBank0() {
        final Ciab ciab = this.createCiaInterruptFails();

        this.writeAndReadCheck(ciab, Cia.PRA, (byte)0x3);

        this.readMemoryAndCheck(0x0000, 0x0fff);
        this.readCharacterAndCheck(0x1000, 0x1fff);
        this.readMemoryAndCheck(0x2000, 0x3fff);
    }

    @Test
    public void testSetBank1() {
        final Ciab ciab = this.createCiaInterruptFails();

        this.writeAndReadCheck(ciab, Cia.PRA, (byte)0x2);

        this.readMemoryAndCheck(0x4000, 0x7fff);
    }

    @Test
    public void testSetBank2() {
        final Ciab ciab = this.createCiaInterruptFails();

        this.writeAndReadCheck(ciab, Cia.PRA, (byte)0x1);

        this.readMemoryAndCheck(0x8000, 0x8fff);
        this.readCharacterAndCheck(0x9000, 0x9fff);
        this.readMemoryAndCheck(0xa000, 0xbfff);
    }

    @Test
    public void testSetBank3() {
        final Ciab ciab = this.createCiaInterruptFails();

        this.writeAndReadCheck(ciab, Cia.PRA, (byte)0x0);

        this.readMemoryAndCheck(0xc000, 0xffff);
    }

    @Test
    public void testSetBank3ThenSet2() {
        final AddressBus characters = this.characters();
        final AddressBus memory = this.memory();
        final VicMapper mapper = this.vicMapper(characters, memory);

        final Ciab ciab = this.createCia(mapper);

        mapper.setBank(VicBank.BANK3);
        this.readAndCheck(ciab, Cia.DDRA, ZERO);

        this.readMemoryAndCheck(0xc000, 0xffff);

        mapper.setBank(VicBank.BANK2);

        this.readMemoryAndCheck(0x8000, 0x8fff);
        this.readCharacterAndCheck(0x9000, 0x9fff);
        this.readMemoryAndCheck(0xa000, 0xbfff);
    }

    private void readCharacterAndCheck(final int lo, final int hi) {
        // vic memory reads should be between 1000 and 1fff
        for (int i = lo; i <= hi; i++) {
            this.readAndCheck(this.vicAddressBus, i, (byte) (~i & 0xff));
        }
    }

    private void readMemoryAndCheck(final int lo, final int hi) {
        this.lo = lo; // memory behind can see anything between 0 and ffff
        this.hi = hi;

        final int from = lo & VicMapper.BANK_MASK;
        final int to = hi & VicMapper.BANK_MASK;

        // vic memory reads should be between 0 and 4000
        for (int i = from; i <= to; i++) {
            this.readAndCheck(this.vicAddressBus, i, (byte) (i & 0xff));
        }
    }

    @Override
    Ciab createCia(final Runnable interrupts) {
        return this.createCia(this.vicMapper(), interrupts);
    }

    private Ciab createCia(final VicMapper mapper) {
        return this.createCia(mapper, this::interrupt);
    }

    private VicMapper vicMapper() {
        return this.vicMapper(characters(), memory());
    }

    private VicMapper vicMapper(final AddressBus characters,
                                final AddressBus memory) {
        return VicMapper.with(characters, memory, this::vicAddressBus);
    }

    private AddressBus characters() {
        return new FakeAddressBus() {
            @Override
            public byte read(final int offset) {
                if (offset < 0 || offset > 0x1000) {
                    fail("Invalid character generator offset: " + Integer.toHexString(offset) + " expected between 0x0000 - 0x0fff");
                }
                return (byte) ~offset;
            }
        };
    }

    private AddressBus memory() {
        return new FakeAddressBus() {
            @Override
            public byte read(final int offset) {
                final int masked = offset & 0xffff;
                if (masked < lo || masked > hi) {
                    fail("Invalid memory offset: " + Integer.toHexString(masked) + " expected between 0x" + Integer.toHexString(lo) + " - 0x" + Integer.toHexString(hi));
                }
                return (byte) offset;
            }
        };
    }

    private int lo = 0;
    private int hi = 0x10000;

    private void vicAddressBus(final AddressBus vicAddressBus) {
        this.vicAddressBus = vicAddressBus;
    }

    private AddressBus vicAddressBus;

    private Ciab createCia(final VicMapper mapper,
                           final Runnable interrupts) {
        return Ciab.with(mapper, interrupts);
    }

    @Override
    public Class<Ciab> type() {
        return Ciab.class;
    }
}
