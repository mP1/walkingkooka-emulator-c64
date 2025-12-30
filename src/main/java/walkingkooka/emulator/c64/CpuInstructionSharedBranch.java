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

abstract class CpuInstructionSharedBranch extends CpuInstructionShared {

    CpuInstructionSharedBranch() {
        super();
    }

    @Override
    public final int length() {
        return 2;
    }

    @Override
    public final void execute(final CpuContext context) {
        final short nextInstructionPc = (short)
            (context.pc() + 1);

        if (this.testFlag(context)) {
            context.setPc(
                (short)
                    (nextInstructionPc + context.readByte(
                        (short) (nextInstructionPc - 1)
                    ))
            );
        } else {
            context.setPc(nextInstructionPc);
        }
    }

    abstract boolean testFlag(final CpuContext context);

    @Override
    public final String disassemble(final CpuContext context) {
        final short pc = context.pc();

        final byte offset = context.readByte(
            (short) (pc + 1)
        );

        // BPL $FF ($F000)
        return this.conditionText() +
            " $" +
            Integer.toHexString(offset) +
            " ($" + Integer.toHexString(pc + 2 + offset) + ")";
    }

    abstract String conditionText();
}
