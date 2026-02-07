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

import walkingkooka.text.CharSequences;

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

        for (int opcode = 0; opcode <= 0xff; opcode++) {
            i[opcode] = CpuInstructions.invalidOpcode(
                (byte) opcode
            );
        }

        for (final CpuInstruction instruction : instructions) {
            final byte opcode = instruction.opcode();
            i[mask(opcode)] = instruction;
        }

        this.instructions = i;
    }

    @Override
    public String disassemble(final CpuContext context) {
        Objects.requireNonNull(context, "context");

        final short pc = context.pc();

        final byte opcode = context.readByte(pc);
        context.setPc(
            (short) (pc + 1)
        );

        final CpuInstruction instruction = this.instructions[
            mask(opcode)
            ];

        // include opcode and any parameters in HEX form
        final StringBuilder b = new StringBuilder();

        b.append(
            hexByte(opcode)
        );

        final int opcodeCount = instruction.length() - 1;

        switch (opcodeCount) {
            case 2:
                b.append(' ');
                b.append(
                    hexByte(
                        context.readByte(
                            (short) (pc + 1)
                        )
                    )
                );
                b.append(' ');
                b.append(
                    hexByte(
                        context.readByte(
                            (short) (pc + 2)
                        )
                    )
                );
                b.append(' ');
                break;
            case 1:
                b.append(' ');
                b.append(
                    hexByte(
                        context.readByte(
                            (short) (pc + 1)
                        )
                    )
                );
                b.append("    ");
                break;
            case 0:
                b.append("       ");
                break;
            default:
                throw new IllegalArgumentException("Invalid opcode length " + opcodeCount + 1 + " for " + hexByte(opcode));
        }

        try {
            b.append(
                instruction.disassemble(context)
            );
        } finally {
            context.setPc(pc);
        }

        return b.toString();
    }

    private static CharSequence hexByte(final byte value) {
        return CharSequences.padLeft(
            Integer.toHexString(
                mask(value)
            ).toUpperCase(),
            2,
            '0' // leading zero if necessary
        );
    }

    @Override
    public void step(final CpuContext context) {
        context.handleBreakpoints();

        // breakpoints above might have changed pc - so load it again
        final short pc = context.pc();
        final byte opcode = context.readByte(pc);
        context.setPc(
            (short) (pc + 1)
        );

        this.instructions[mask(opcode)].execute(context);
    }

    private final CpuInstruction[] instructions;

    private static int mask(final byte value) {
        return 0xff & value;
    }
}
