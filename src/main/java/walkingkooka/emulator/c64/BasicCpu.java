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

import java.util.Collection;
import java.util.Objects;

/**
 * A simple cpu that simply dispatches opcodes to {@link CpuInstruction}.
 */
final class BasicCpu implements Cpu {

    static BasicCpu with(final Collection<CpuInstruction> instructions) {
        return new BasicCpu(
            Objects.requireNonNull(instructions, "instructions")
        );
    }

    private BasicCpu(final Collection<CpuInstruction> instructions) {
        final CpuInstruction[] i = new CpuInstruction[256];

        for (final CpuInstruction instruction : instructions) {
            final byte opcode = instruction.opcode();
            i[mask(opcode)] = instruction;
        }

        this.instructions = i;
    }

    @Override
    public void step(final CpuContext context) {
        short pc = context.pc();

        switch (this.mode) {
            case NONE:
                break;
            case RESET:
                context.setA((byte) 0);
                context.setX((byte) 0);
                context.setY((byte) 0);

                context.setFlags((byte) 0);

                context.setStackPointer((byte) 0xff);

                pc = context.readAddress(RESET_VECTOR);
                this.mode = NONE;
                break;
            case NMI:
                // push hi(pc), lo(pc), flags with break=0
                context.push(
                    hi(pc)
                );
                context.push(
                    lo(pc)
                );
                context.setBreak(false); // clear
                context.push(
                    context.flags()
                );

                pc = context.readAddress(NMI_VECTOR);
                this.mode = NONE;
                break;
            case IRQ:
                // if interrupts are enabled push hi(pc), lo(pc), flags with break=0
                if (false == context.isInterruptDisabled()) {
                    context.push(
                        hi(pc)
                    );
                    context.push(
                        lo(pc)
                    );
                    context.setBreak(false); // clear
                    context.push(
                        context.flags()
                    );

                    pc = context.readAddress(IRQ_VECTOR);

                    context.setInterruptDisabled(true); // disable interrupts
                    this.mode = NONE;
                }
                break;
            default:
                throw new IllegalStateException("Unknown mode: " + this.mode);
        }

        final byte opcode = context.readByte(pc);
        context.setPc(
            (short) (pc + 1)
        );

        this.instructions[mask(opcode)].execute(context);
    }

    private final CpuInstruction[] instructions;

    @Override
    public void reset() {
        this.mode = RESET;
    }

    @Override
    public void nmi() {
        this.mode = NMI;
    }

    @Override
    public void irq() {
        this.mode = IRQ;
    }

    private final static int NONE = 0;
    private final static int RESET = 1;
    private final static int NMI = 2;
    private final static int IRQ = 3;

    private int mode = NONE;

    private byte hi(final short value) {
        return (byte) (value >> 8);
    }

    private byte lo(final short value) {
        return (byte) (0xff & value);
    }

    private int mask(final byte value) {
        return 0xff & value;
    }
}
