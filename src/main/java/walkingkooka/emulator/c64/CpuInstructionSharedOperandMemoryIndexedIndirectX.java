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
 * https://www.c64-wiki.com/wiki/Indexed-indirect_addressing
 * <pre>
 * Indexed-indirect addressing
 * In this rarely used Addressing mode, the X index register is used to offset the zero page vector used to determine the effective address. Put another way, the vector is chosen by adding the value in the X index register to the given zero page address. The resulting zero page address is the vector from which the effective address is read.
 *
 * Indexed-indirect addressing is written as follows:
 *
 *      LDX #$04
 *      LDA ($02,X)
 * In the above case, X is loaded with four (4), so the vector is calculated as $02 plus four (4). The resulting vector is ($06). If zero page memory $06 contains 00 80, then the effective address from the vector (06) would be $8000.
 *
 * This addressing mode would only be useful to select a vector from an array of zero page vectors, and as such is very rarely used.
 *
 * The following 8 machine language instructions support indexed-indirect addressing: ADC, AND, CMP, EOR, LDA, ORA, SBC, STA.
 * </pre>
 */
final class CpuInstructionSharedOperandMemoryIndexedIndirectX extends CpuInstructionSharedOperandMemory {

    static CpuInstructionSharedOperandMemoryIndexedIndirectX instance() {
        return new CpuInstructionSharedOperandMemoryIndexedIndirectX();
    }

    private CpuInstructionSharedOperandMemoryIndexedIndirectX() {
        super();
    }

    @Override
    int length() {
        return 1;
    }

    @Override
    short operandAddress(final CpuContext context) {
        return context.readZeroPageAddress(
            (byte) zeroPageOffset(
                readByte(context),
                context.x()
            )
        );
    }

    @Override
    String disassemble(final CpuContext context) {
        return "(" + this.readByte(context) + ",X)";
    }
}
