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
 * https://www.c64-wiki.com/wiki/Indirect-indexed_addressing
 * <pre>
 * Indirect-indexed addressing
 * In this commonly used Addressing mode, the Y Index Register is used as an offset from the given zero page vector. The effective address is calculated as the vector plus the value in Y.
 *
 * Indirect-indexed addressing is written as follows:
 *
 *      LDY #$04
 *      LDA ($02),Y
 * In the above case, Y is loaded with four (4), and the vector is given as ($02). If zero page memory $02-$03 contains 00 80, then the effective address from the vector ($02) plus the offset (Y) would be $8004.
 *
 * This addressing mode is commonly used in array addressing, such that the array index is placed in Y and the array base address is stored in zero page as the vector. Typically, the value in Y is calculated as the array element size multiplied by the array index. For single byte-sized array elements (such as character strings), the value in Y is the array index without modification.
 *
 * The following 8 machine language instructions support indirect-indexed addressing: ADC, AND, CMP, EOR, LDA, ORA, SBC, and STA.
 * </pre>
 */
final class CpuInstructionSharedOperandMemoryIndirectIndexedY extends CpuInstructionSharedOperandMemory {

    static CpuInstructionSharedOperandMemoryIndirectIndexedY instance() {
        return new CpuInstructionSharedOperandMemoryIndirectIndexedY();
    }

    private CpuInstructionSharedOperandMemoryIndirectIndexedY() {
        super();
    }

    @Override
    int length() {
        return 1;
    }

    @Override
    short operandAddress(final CpuContext context) {
        return address(
            context.readZeroPageAddress(
                readByte(context)
            ),
            context.y()
        );
    }

    @Override
    String disassemble(final CpuContext context) {
        return "(" + this.readByte(context) + "),Y";
    }
}
