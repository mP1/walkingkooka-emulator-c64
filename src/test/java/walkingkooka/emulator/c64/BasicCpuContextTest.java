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
import walkingkooka.text.CharSequences;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class BasicCpuContextTest implements CpuContextTesting2<BasicCpuContext>,
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
            (byte) 0xaa
        );

        context.writeByte(
            (short) 0x1fd,
            (byte) 0x55
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
    public void testReadZeroPageAddress3() {
        final BasicCpuContext context = this.createContext();

        // lo-byte
        context.writeByte(
            (short) 0xf1,
            (byte) 0x0
        );

        // hi-byte
        context.writeByte(
            (short) 0xf2,
            (byte) 0x12
        );

        this.readAddressAndCheck(
            context,
            (short) 0xf1,
            (short) 0x1200
        );
    }

    @Test
    public void testReadZeroPageByte() {
        final BasicCpuContext context = this.createContext();

        context.writeByte(
            (short) 0x1,
            (byte) 0xff
        );

        this.readByteAndCheck(
            context,
            (short) 0x1,
            (byte) 0xff
        );
    }

    @Test
    public void testReadZeroPageByte2() {
        final BasicCpuContext context = this.createContext();

        context.writeByte(
            (short) 0xf0,
            (byte) 0xff
        );

        this.readByteAndCheck(
            context,
            (short) 0xf0,
            (byte) 0xff
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

    @Test
    public void testWriteZeroPageByte() {
        final BasicCpuContext context = this.createContext();

        context.writeByte(
            (short) 0x1,
            (byte) 0xff
        );

        this.readByteAndCheck(
            context,
            (short) 0x1,
            (byte) 0xff
        );
    }

    @Test
    public void testWriteZeroPageByte2() {
        final BasicCpuContext context = this.createContext();

        context.writeByte(
            (short) 0xf0,
            (byte) 0xff
        );

        this.readByteAndCheck(
            context,
            (short) 0xf0,
            (byte) 0xff
        );
    }

    @Test
    public void testIrq() {
        final CpuContext context = this.createCpuContextWithRoms();
        context.setA((byte) 0xff);
        context.setX((byte) 0xfe);
        context.setY((byte) 0xfd);
        context.setInterruptDisabled(false);
        context.setStackPointer((byte) 0x80);
        context.setPc((short) 0x1234);

        final int flags = context.flags();

        context.irq();

        context.handleInterrupts();

        this.pcAndCheck(
            context,
            (short) 0xff48
        );
        this.flagsAndCheck(
            context,
            "--I--1--"
        );

        this.aAndCheck(
            context,
            (byte) 0xff
        );
        this.xAndCheck(
            context,
            (byte) 0xfe
        );
        this.yAndCheck(
            context,
            (byte) 0xfd
        );
        this.stackPointerAndCheck(
            context,
            (byte) (0x80 - 3) // pc + flags
        );

        this.readAddressAndCheck(
            context,
            (short) 0x17f,
            (short) 0x1234
        );
        this.readByteAndCheck(
            context,
            (short) 0x17e,
            (byte) (flags | CpuFlags.UNUSED.set())
        );
    }

    @Test
    public void testReset() {
        final BasicCpuContext context = this.createCpuContextWithRoms();
        context.setA((byte) 0xff);
        context.setX((byte) 0xfe);
        context.setY((byte) 0xfd);
        context.setStackPointer((byte) 0x80);
        context.setPc((short) 0x1234);

        context.reset();

        context.handleInterrupts();

        this.pcAndCheck(
            context,
            (short) 0xfce2
        );
        this.flagsAndCheck(
            context,
            (byte) 0
        );

        this.aAndCheck(
            context,
            (byte) 0
        );
        this.xAndCheck(
            context,
            (byte) 0
        );
        this.yAndCheck(
            context,
            (byte) 0
        );
        this.stackPointerAndCheck(
            context,
            (byte) 0xff
        );
    }

    @Test
    public void testNmi() {
        final BasicCpuContext context = this.createCpuContextWithRoms();
        context.setA((byte) 0xff);
        context.setX((byte) 0xfe);
        context.setY((byte) 0xfd);
        context.setStackPointer((byte) 0x80);
        context.setPc((short) 0x1234);

        context.setCarry(true);
        final int flags = context.flags();

        context.nmi();

        context.handleInterrupts();

        this.pcAndCheck(
            context,
            (short) 0xfe43
        );

        this.flagsAndCheck(
            context,
            "C----1--"
        );

        this.aAndCheck(
            context,
            (byte) 0xff
        );
        this.xAndCheck(
            context,
            (byte) 0x0fe
        );
        this.yAndCheck(
            context,
            (byte) 0xfd
        );
        this.stackPointerAndCheck(
            context,
            (byte) (0x80 - 3) // pc + flags
        );

        this.readAddressAndCheck(
            context,
            (short) 0x17f,
            (short) 0x1234
        );
        this.readByteAndCheck(
            context,
            (short) 0x17e,
            (byte) (flags | CpuFlags.UNUSED.set())
        );
    }

    @Override
    public BasicCpuContext createContext() {
        return BasicCpuContext.with(
            AddressBuses.memory(256 * 256)
        );
    }

    private BasicCpuContext createCpuContextWithRoms() {
        final BasicCpuContext context = this.createContext();

        this.loadAndCopyRom("basic.901226-01.bin", Cpu.BASIC_ROM_BASE, context);
        this.loadAndCopyRom("kernal.901227-03.bin", Cpu.KERNAL_ROM_BASE, context);

        return context;
    }

    private void loadAndCopyRom(final String name,
                                final short base,
                                final CpuContext context) {
        final byte[] bytes = this.loadRom(name);

        for (int i = 0; i < bytes.length; i++) {
            context.writeByte(
                (short) (base + i),
                bytes[i]
            );
        }
    }

    private byte[] loadRom(final String name) {
        try {
            final InputStream inputStream = this.getClass()
                .getResourceAsStream(
                    "/walkingkooka/emulator/c64/" + name
                );
            if (null == inputStream) {
                throw new IllegalStateException("Unable to load ROM file " + CharSequences.quote(name));
            }
            return inputStream.readAllBytes();
        } catch (final IOException cause) {
            throw new IllegalStateException("Reading ROM file " + CharSequences.quote(name) + " failed: " + cause.getMessage(), cause);
        }
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
            "A: 0x12, X: 0x34, Y: 0x56, SP: 0xfe, PC: 0xabcd, CpuFlags: C----1--"
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
            "A: 0x12, X: 0x34, Y: 0x56, SP: 0xfe, PC: 0xabcd, CpuFlags: -----1V-"
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
            "A: 0x12, X: 0x34, Y: 0x56, SP: 0xfe, PC: 0xabcd, CpuFlags: CZIDB1-N"
        );
    }

    // class............................................................................................................

    @Override
    public Class<BasicCpuContext> type() {
        return BasicCpuContext.class;
    }
}
