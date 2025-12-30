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

import java.util.Objects;

final class CpuInstructionSharedBrk extends CpuInstructionShared {

    final static CpuInstructionSharedBrk INSTANCE = new CpuInstructionSharedBrk();

    private CpuInstructionSharedBrk() {
        super();
    }

    @Override
    public byte opcode() {
        return BRK;
    }

    @Override
    public int length() {
        return 1;
    }

    @Override
    public void execute(final CpuContext context) {
        Objects.requireNonNull(context, "context");

        final short pc = context.pc();

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

        context.setPc(
            context.readAddress(
                CpuContext.IRQ_VECTOR
            )
        );

        context.setInterruptDisabled(true); // disable interrupts
    }

    @Override
    public String disassemble(final CpuContext context) {
        Objects.requireNonNull(context, "context");
        return "BRK";
    }
}
