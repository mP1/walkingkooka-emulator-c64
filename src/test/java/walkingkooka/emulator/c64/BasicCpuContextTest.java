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
import walkingkooka.ToStringTesting;
import walkingkooka.collect.list.Lists;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class BasicCpuContextTest implements CpuContextTesting<BasicCpuContext>,
    ToStringTesting<BasicCpuContext> {

    @Test
    public void testWithNullFails() {
        assertThrows(
            NullPointerException.class,
            () -> BasicCpuContext.with(null)
        );
    }

    @Test
    public void testSetA() {
        this.setAAndCheck(
            this.createContext(),
            (byte) 12
        );
    }

    @Test
    public void testSetX() {
        this.setXAndCheck(
            this.createContext(),
            (byte) 12
        );
    }

    @Test
    public void testSetY() {
        this.setYAndCheck(
            this.createContext(),
            (byte) 12
        );
    }

    @Test
    public void testSetStackPointer() {
        this.setStackPointerAndCheck(
            this.createContext(),
            (byte) 12
        );
    }

    @Test
    public void testSetPC() {
        this.setPCAndCheck(
            this.createContext(),
            (short) 0x1234
        );
    }

    @Test
    public void testPush() {
        final BasicCpuContext context = this.createContext();

        context.writeByte(
            (short) 0x1ff,
            (byte) 0xff
        );

        context.writeByte(
            (short) 0x1fe,
            (byte) 0xfe
        );

        context.setStackPointer((byte) 0xff);

        context.push((byte) 0x55);

        this.readByteAndCheck(
            context,
            (short) 0x1ff,
            (byte) 0x55
        );

        this.readByteAndCheck(
            context,
            (short) 0x1fe,
            (byte) 0xfe
        );
    }

    @Test
    public void testPop() {
        final BasicCpuContext context = this.createContext();

        context.writeByte(
            (short) 0x1ff,
            (byte) 0xff
        );

        context.writeByte(
            (short) 0x1fe,
            (byte) 0xfe
        );

        context.setStackPointer((byte) 0xfe);

        this.popAndCheck(
            context,
            (byte) 0xff
        );
    }

    @Test
    public void testPushAndPopMany() {
        final BasicCpuContext context = this.createContext();

        context.setStackPointer((byte) 0xff);

        final List<Byte> pushed = Lists.array();
        pushed.add((byte) 1);
        pushed.add((byte) 2);
        pushed.add((byte) 3);

        for (final Byte push : pushed) {
            context.push(push);
        }

        final List<Byte> popped = Lists.array();
        popped.add(
            context.pop()
        );
        popped.add(
            context.pop()
        );
        popped.add(
            context.pop()
        );

        this.checkEquals(
            Lists.of(
                (byte) 3,
                (byte) 2,
                (byte) 1
            ),
            popped
        );
    }

    @Test
    public void testReadZeroPageAddress() {
        final BasicCpuContext context = this.createContext();

        // lo-byte
        context.writeByte(
            (short) 0x1,
            (byte) 0xff
        );

        // hi-byte
        context.writeByte(
            (short) 0x2,
            (byte) 0x11
        );

        this.readAddressAndCheck(
            context,
            (short) 0x1,
            (short) 0x11ff
        );
    }

    @Test
    public void testReadZeroPageAddress2() {
        final BasicCpuContext context = this.createContext();

        // lo-byte
        context.writeByte(
            (short) 0x1,
            (byte) 0x0
        );

        // hi-byte
        context.writeByte(
            (short) 0x2,
            (byte) 0x12
        );

        this.readAddressAndCheck(
            context,
            (short) 0x1,
            (short) 0x1200
        );
    }

    @Test
    public void testReadAddress() {
        final BasicCpuContext context = this.createContext();

        context.writeByte(
            (short) 0xf001,
            (byte) 0x11
        );

        context.writeByte(
            (short) 0xf002,
            (byte) 0x022
        );

        this.readAddressAndCheck(
            context,
            (short) 0xf001,
            (short) 0x2211
        );
    }

    @Test
    public void testReadAddress2() {
        final BasicCpuContext context = this.createContext();

        context.writeByte(
            (short) 0xf001,
            (byte) 0xfe
        );

        context.writeByte(
            (short) 0xf002,
            (byte) 0x12
        );

        this.readAddressAndCheck(
            context,
            (short) 0xf001,
            (short) 0x12fe
        );
    }

    @Override
    public BasicCpuContext createContext() {
        return BasicCpuContext.with(
            AddressBuses.memory(256 * 256)
        );
    }

    // toString.........................................................................................................

    @Test
    public void testToStringCarry() {
        final BasicCpuContext context = this.createContext();

        context.setCarry(true);

        context.setPc((short) 0xabcd);
        context.setStackPointer((byte) 0xfe);

        context.setA((byte) 0x12);
        context.setX((byte) 0x34);
        context.setY((byte) 0x56);

        this.toStringAndCheck(
            context,
            "A: 0x12, X: 0x34, Y: 0x56, SP: 0xfe, PC: 0xabcd, Flags: C----1--"
        );
    }

    @Test
    public void testToStringOverflow() {
        final BasicCpuContext context = this.createContext();

        context.setOverflow(true);

        context.setPc((short) 0xabcd);
        context.setStackPointer((byte) 0xfe);

        context.setA((byte) 0x12);
        context.setX((byte) 0x34);
        context.setY((byte) 0x56);

        this.toStringAndCheck(
            context,
            "A: 0x12, X: 0x34, Y: 0x56, SP: 0xfe, PC: 0xabcd, Flags: -----1O-"
        );
    }

    @Test
    public void testToString() {
        final BasicCpuContext context = this.createContext();

        context.setBreak(true);
        context.setCarry(true);
        context.setDecimalMode(true);
        context.setInterruptDisabled(true);
        context.setMinus(true);
        context.setZero(true);

        context.setPc((short) 0xabcd);
        context.setStackPointer((byte) 0xfe);

        context.setA((byte) 0x12);
        context.setX((byte) 0x34);
        context.setY((byte) 0x56);

        this.toStringAndCheck(
            context,
            "A: 0x12, X: 0x34, Y: 0x56, SP: 0xfe, PC: 0xabcd, Flags: CZIDB1-N"
        );
    }

    // class............................................................................................................

    @Override
    public Class<BasicCpuContext> type() {
        return BasicCpuContext.class;
    }
}
