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
 * Immediate operands, for instructions such as LDA #$12
 */
final class CpuInstructionSharedOperandMemoryImmediate extends CpuInstructionSharedOperandMemory {

    static CpuInstructionSharedOperandMemoryImmediate instance() {
        return new CpuInstructionSharedOperandMemoryImmediate();
    }

    private CpuInstructionSharedOperandMemoryImmediate() {
        super();
    }

    @Override //
    int length() {
        return 1; // just the zero page offset
    }

    @Override //
    short operandAddress(final CpuContext context) {
        final short pc = context.pc();
        context.setPc(
            (short) (pc + 1)
        );
        return pc;
    }

    @Override //
    String disassemble(final CpuContext context) {
        // #$3F
        return CpuInstructionShared.hexByte(
            this.readPcByte(context)
        );
    }
}
