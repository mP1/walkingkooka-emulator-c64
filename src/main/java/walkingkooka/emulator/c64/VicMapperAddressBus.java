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

/**
 * Provides a {@link AddressBus} a VIC for BANKS0 and BANK2 which provides a view mixing memory and the character generator ROM.
 * Reads from the character generator will appear to be between 0 and 0xfff and memory reads will be from 0x0000 and 0x3fff
 * and fixed to be between the full 64K.
 * <br>
 * If BANK = 2 (0x8000 and 0xbfff) a read of 1 will result in a memory.read(0x8000 + 1).
 * If BANK = 2 (0x8000 and 0xbfff) a read of 0x1001 will result in a characterGenerator.read(1).
 * if BANK = 3 (0xc000 and 0xffff) a read of 2 will result in a memory.read(0xc000 + 1).
 */
final class VicMapperAddressBus implements AddressBus {

    static VicMapperAddressBus with(final int baseOffset,
                                    final AddressBus characters,
                                    final AddressBus memory) {
        return new VicMapperAddressBus(baseOffset,
            characters,
            memory);
    }

    private VicMapperAddressBus(final int baseOffset,
                                final AddressBus characters,
                                final AddressBus memory) {
        this.baseOffset = baseOffset;
        this.characters = characters;
        this.memory = memory;
    }

    @Override
    public byte read(final int offset) {
        byte read;

        final int masked = offset & VicMapper.BANK_MASK;

        if (masked >= CHARGEN_LO && masked < CHARGEN_HI) {
            read = this.characters.read(offset & CHARGEN_MASK);
        } else {
            read = this.memory.read(this.baseOffset + offset);
        }

        return read;
    }

    // a VIC never writes...
    @Override
    public void write(final int offset, final byte value) {
        final int masked = offset & VicMapper.BANK_MASK;

        if (masked >= CHARGEN_LO && masked < CHARGEN_HI) {
            this.characters.write(offset & CHARGEN_MASK, value);
        } else {
            this.memory.write(this.baseOffset + offset, value);
        }
    }

    private final static int CHARGEN_LO = 0x1000; // inc
    private final static int CHARGEN_HI = 0x2000; // excl
    private final static int CHARGEN_MASK = 0x0fff;

    private final int baseOffset;
    private final AddressBus characters;
    private final AddressBus memory;

    @Override
    public String toString() {
        return "Vic 0x" + Integer.toHexString(this.baseOffset & 0xffff);
    }
}
