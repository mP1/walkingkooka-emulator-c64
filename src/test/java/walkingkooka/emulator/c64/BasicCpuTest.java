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
import walkingkooka.collect.list.Lists;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.text.CharSequences;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class BasicCpuTest implements CpuTesting<BasicCpu> {

    // with.............................................................................................................

    @Test
    public void testWithNullInstructionsFails() {
        assertThrows(
            NullPointerException.class,
            () -> BasicCpu.with(null)
        );
    }

    @Test
    public void testIrq() {
        final CpuContext context = this.createCpuContext();
        context.setA((byte) 0xff);
        context.setX((byte) 0xfe);
        context.setY((byte) 0xfd);
        context.setInterruptDisabled(false);
        context.setStackPointer((byte) 0x80);
        context.setPc((short) 0x1234);

        final int flags = context.flags();

        final BasicCpu cpu = this.createCpu();
        cpu.irq();

        cpu.step(context);

        this.pcAndCheck(
            context,
            (short) 0xff49
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
            (byte) (flags | 0x20)
        );
    }

    @Test
    public void testReset() {
        final CpuContext context = this.createCpuContext();
        context.setA((byte) 0xff);
        context.setX((byte) 0xfe);
        context.setY((byte) 0xfd);
        context.setStackPointer((byte) 0x80);
        context.setPc((short) 0x1234);

        final BasicCpu cpu = this.createCpu();
        cpu.reset();

        cpu.step(context);

        this.pcAndCheck(
            context,
            (short) 0xfce3
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
        final CpuContext context = this.createCpuContext();
        context.setA((byte) 0xff);
        context.setX((byte) 0xfe);
        context.setY((byte) 0xfd);
        context.setStackPointer((byte) 0x80);
        context.setPc((short) 0x1234);

        context.setCarry(true);
        final int flags = context.flags();

        final BasicCpu cpu = this.createCpu();
        cpu.nmi();

        cpu.step(context);

        this.pcAndCheck(
            context,
            (short) 0xfe44
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
            (byte) (flags | 0x20)
        );
    }

    // Cpu..............................................................................................................

    @Override
    public BasicCpu createCpu() {
        final List<CpuInstruction> instructions = Lists.array();
        for (int i = 0; i < 256; i++) {
            final byte opcode = (byte) i;

            instructions.add(
                new CpuInstruction() {
                    @Override
                    public byte opcode() {
                        return opcode;
                    }

                    @Override
                    public int length() {
                        return 1;
                    }

                    @Override
                    public void execute(final CpuContext context) {

                    }
                }
            );
        }

        return BasicCpu.with(instructions);
    }

    @Override
    public CpuContext createCpuContext() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256)
        );

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

    // class............................................................................................................

    @Override
    public Class<BasicCpu> type() {
        return BasicCpu.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }
}
