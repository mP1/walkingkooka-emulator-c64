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
 * Base class for any of the absolute addressing modes, such as ABS, ABS,X and ABS,Y
 */
abstract class CpuInstructionSharedOperandMemoryAbsolute extends CpuInstructionSharedOperandMemory {

    CpuInstructionSharedOperandMemoryAbsolute() {
        super();
    }

    @Override //
    final int length() {
        return 2;// lo and hi byte of address
    }

    @Override //
    final short operandAddress(final CpuContext context) {
        return address(
            this.readPcAddress(
                context
            ),
            this.operandAddressIndex(context)
        );
    }

    /**
     * Only ABS,X and ABS,Y will return either the X or Y register, plain ABS will return 0
     */
    abstract byte operandAddressIndex(final CpuContext context);

    @Override //
    final String disassemble(final CpuContext context) {
        // $1234
        // $ABCD,X
        return context.addressSymbol(
            (short)
                (
                    this.readPcAddress(context) + this.operandAddressIndex(context)
                )
        ) +
            this.disassembleIndex();
    }

    final short readPcAddress(final CpuContext context) {
        final short pc = context.pc();

        context.setPc(
            (short) (pc + 2)
        );
        return context.readAddress(pc);
    }

    abstract String disassembleIndex();
}
