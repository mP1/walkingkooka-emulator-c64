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

import walkingkooka.collect.set.SortedSets;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

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
    public Runnable addBreakpoint(final short address) {
        this.breakpoints.add(address);

        return () -> this.breakpoints.remove(address);
    }

    private final Set<Short> breakpoints = SortedSets.tree();

    @Override
    public String disassemble(final CpuContext context) {
        Objects.requireNonNull(context, "context");

        final short pc = context.pc();

        final byte opcode = context.readByte(pc);
        context.setPc(
            (short) (pc + 1)
        );

        try {
            return this.instructions[mask(opcode)]
                .disassemble(context);
        } finally {
            context.setPc(pc);
        }
    }

    @Override
    public void step(final CpuContext context) {
        if (this.breakpoints.contains(context.pc())) {
            context.fireBreakpoints();
        }

        // breakpoints above might have changed pc - so load it again
        final short pc = context.pc();
        final byte opcode = context.readByte(pc);
        context.setPc(
            (short) (pc + 1)
        );

        this.instructions[mask(opcode)].execute(context);
    }

    private final CpuInstruction[] instructions;

    private int mask(final byte value) {
        return 0xff & value;
    }
}
