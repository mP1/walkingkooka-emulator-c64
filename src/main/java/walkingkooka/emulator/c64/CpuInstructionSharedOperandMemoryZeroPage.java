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
 * Base class for any of the zero page offset operands such as ZP, ZP,X, ZP,Y
 */
abstract class CpuInstructionSharedOperandMemoryZeroPage extends CpuInstructionSharedOperandMemory {

    CpuInstructionSharedOperandMemoryZeroPage() {
        super();
    }

    @Override //
    final int length() {
        return 1; // just the zero page offset
    }

    @Override //
    final short operandAddress(final CpuContext context) {
        return zeroPageOffsetAddress(
            this.zeroPageOffset(context),
            this.operandAddressIndex(context)
        );
    }

    /**
     * Only ZP,X and ZP,Y will return either the X or Y register, plain ZP will return 0
     */
    abstract byte operandAddressIndex(final CpuContext context);

    @Override //
    final String disassemble(final CpuContext context) {
        // zp $3F
        // zp $3F,x
        return "zp " +
            CpuInstructionShared.hexByte(
                this.zeroPageOffset(context)
            ) +
            this.disassembleIndex(context);
    }

    final byte zeroPageOffset(final CpuContext context) {
        final short pc = context.pc();

        context.setPc(
            (short) (pc + 1)
        );
        return context.readByte(pc);
    }

    abstract String disassembleIndex(final CpuContext context);
}
