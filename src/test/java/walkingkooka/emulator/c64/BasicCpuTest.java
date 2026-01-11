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

    // step.............................................................................................................

    @Test
    public void testStep() {
        final CpuInstruction ldaImm = CpuInstructions.ldaImm();

        final BasicCpu cpu = BasicCpu.with(
            Lists.of(
                ldaImm
            )
        );

        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(
                256 * 256
            )
        );

        final short pc = 0x1000;
        context.setPc(pc);

        context.writeByte(
            pc,
            ldaImm.opcode()
        );

        final byte value = 0x12;
        context.writeByte(
            (short) (pc + 1),
            value
        );

        context.setA(
            (byte) 0x99
        );
        cpu.step(context);

        this.pcAndCheck(
            context,
            (short) (pc + 2)
        );

        this.aAndCheck(
            context,
            value
        );
    }

    @Test
    public void testStepWithBreakpointDifferentPc() {
        final CpuInstruction inx = CpuInstructions.inx();

        final BasicCpu cpu = BasicCpu.with(
            Lists.of(
                inx
            )
        );

        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(
                256 * 256
            )
        );

        final short pc = 0x1000;
        context.setPc(pc);

        context.writeByte(
            pc,
            inx.opcode()
        );

        context.setX(
            (byte) 0x1
        );

        cpu.addWatcher(
            new CpuWatcher() {
                @Override
                public void onBreakpoint(final CpuContext context) {
                    throw new UnsupportedOperationException();
                }
            }
        );

        cpu.addBreakpoint(
            (short) 0x2000
        );

        cpu.step(context);

        this.pcAndCheck(
            context,
            (short) (pc + 1)
        );

        this.xAndCheck(
            context,
            (byte) 0x2
        );
    }

    @Test
    public void testStepFiresBreakpoint() {
        final CpuInstruction inx = CpuInstructions.inx();

        final BasicCpu cpu = BasicCpu.with(
            Lists.of(
                inx
            )
        );

        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(
                256 * 256
            )
        );

        context.writeByte(
            (short) 0x1000,
            inx.opcode()
        );

        context.setX(
            (byte) 0x1
        );
        context.setY(
            (byte) 0x0
        );

        final short pc = 0x1000;
        context.setPc(pc);

        final byte newYValue = 1;

        // breakpoint is fired first and changes pc to 0x2000
        cpu.addWatcher(
            new CpuWatcher() {
                @Override
                public void onBreakpoint(final CpuContext context) {
                    context.setY(newYValue);
                }
            }
        );

        cpu.addBreakpoint(
            (short) 0x1000
        );

        cpu.step(context);

        this.pcAndCheck(
            context,
            (short) 0x1001
        );

        this.xAndCheck(
            context,
            (byte) 0x2
        );
        this.yAndCheck(
            context,
            newYValue
        );
    }

    @Test
    public void testStepFiresBreakpointWhichChangesPc() {
        final CpuInstruction inx = CpuInstructions.inx();

        final BasicCpu cpu = BasicCpu.with(
            Lists.of(
                inx
            )
        );

        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(
                256 * 256
            )
        );

        context.setX(
            (byte) 0x1
        );

        final short initialPc = 0x1000;
        context.setPc(initialPc);
        cpu.addBreakpoint(
            initialPc
        );

        final short pc = 0x2000;
        context.writeByte(
            pc,
            inx.opcode()
        );

        // breakpoint is fired first and changes pc to 0x2000
        cpu.addWatcher(
            new CpuWatcher() {
                @Override
                public void onBreakpoint(final CpuContext context) {
                    context.setPc(pc);
                }
            }
        );

        cpu.step(context);

        this.pcAndCheck(
            context,
            (short) 0x2001
        );

        this.xAndCheck(
            context,
            (byte) 0x2
        );
    }

    // disassemble......................................................................................................

    @Test
    public void testDisassemble() {
        final BasicCpu cpu = BasicCpu.with(
            CpuInstructions.all()
        );
        final AddressBus ram = AddressBuses.memory(
            256 * 256
        );
        final CpuContext cpuContext = CpuContexts.basic(
            AddressBuses.cpu(
                ram, // writable memory
                AddressBuses.basicRom(
                    AddressBuses.baseOffset(
                        AddressBus.BASIC_BASE,
                        ram
                    ) // write
                ),
                AddressBuses.characterGenerator(
                    AddressBuses.baseOffset(
                        AddressBus.CHARACTER_GENERATOR_BASE,
                        ram
                    ) // write
                ),
                AddressBuses.memory(4 * 1024), // i,oDevices
                AddressBuses.kernalRom(
                    AddressBuses.baseOffset(
                        AddressBus.KERNAL_BASE,
                        ram
                    ) // write
                )
            )
        );

        cpuContext.setPc(
            (short) 0xFCE2
        );

        cpuContext.writeByte(
            (short) 0x0,
            AddressBus.DEFAULT_CPU_DATA_DIRECTION
        );

        cpuContext.writeByte(
            (short) 0x1,
            AddressBus.DEFAULT_CPU_PORT
        );

        this.readByteAndCheck(
            cpuContext,
            AddressBus.BASIC_BASE,
            (byte) 60
        );

        this.readByteAndCheck(
            cpuContext,
            AddressBus.KERNAL_BASE,
            (byte) 133
        );

        this.disassembleAndCheck(
            cpu,
            cpuContext,
            "LDX #$ff"
        );

        cpuContext.setPc(
            (short) (cpuContext.pc() + 2)
        );

        this.disassembleAndCheck(
            cpu,
            cpuContext,
            "SEI"
        );

        cpuContext.setPc(
            (short) (cpuContext.pc() + 1)
        );

        this.disassembleAndCheck(
            cpu,
            cpuContext,
            "TXS"
        );

        cpuContext.setPc(
            (short) (cpuContext.pc() + 1)
        );

        this.disassembleAndCheck(
            cpu,
            cpuContext,
            "CLD"
        );

        cpuContext.setPc(
            (short) (cpuContext.pc() + 1)
        );

        this.disassembleAndCheck(
            cpu,
            cpuContext,
            "JSR $fd02"
        );
    }

    // Cpu..............................................................................................................

    @Override
    public BasicCpu createCpu() {
        final List<CpuInstruction> instructions = Lists.array();
        for (int i = 0; i < 256; i++) {
            final byte opcode = (byte) i;

            instructions.add(
                new FakeCpuInstruction() {
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
        return CpuContexts.basic(
            AddressBuses.memory(256 * 256)
        );
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
