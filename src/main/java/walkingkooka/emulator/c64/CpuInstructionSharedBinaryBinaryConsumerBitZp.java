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
 * https://www.masswerk.at/6502/6502_instruction_set.html#BIT
 * <pre>
 * BIT
 * Test Bits in Memory with Accumulator
 *
 * bits 7 and 6 of operand are transfered to bit 7 and 6 of SR (N,V);
 * the zero-flag is set according to the result of the operand AND
 * the accumulator (set, if the result is zero, unset otherwise).
 * This allows a quick check of a few bits at once without affecting
 * any of the registers, other than the status register (SR).
 *
 * → Further details.
 *
 * A AND M -> Z, M7 -> N, M6 -> V
 * N	Z	C	I	D	V
 * M7	+	-	-	-	M6
 * addressing	assembler	opc	bytes	cycles
 * zeropage	BIT oper	24	2	3
 * </pre>
 */
final class CpuInstructionSharedBinaryBinaryConsumerBitZp extends CpuInstructionSharedBinaryBinaryConsumerBit {

    static CpuInstructionSharedBinaryBinaryConsumerBitZp instance() {
        if (null == INSTANCE) {
            INSTANCE = new CpuInstructionSharedBinaryBinaryConsumerBitZp();
        }
        return INSTANCE;
    }

    private static CpuInstructionSharedBinaryBinaryConsumerBitZp INSTANCE;

    private CpuInstructionSharedBinaryBinaryConsumerBitZp() {
        super();
    }

    @Override
    public byte opcode() {
        return BITZP;
    }

    @Override
    CpuInstructionSharedOperandMemory memory() {
        return CpuInstructionSharedOperand.ZP;
    }
}
