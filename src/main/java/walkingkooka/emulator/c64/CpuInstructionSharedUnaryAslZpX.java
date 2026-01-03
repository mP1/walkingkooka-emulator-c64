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
 * https://www.masswerk.at/6502/6502_instruction_set.html#ASL
 * <pre>
 * ASL
 * Shift Left One Bit (Memory or Accumulator)
 *
 * C <- [76543210] <- 0
 * N	Z	C	I	D	V
 * +	+	+	-	-	-
 * addressing	assembler	opc	bytes	cycles
 * zeropage,X	ASL oper,X	16	2	6
 * </pre>
 */
final class CpuInstructionSharedUnaryAslZpX extends CpuInstructionSharedUnaryAsl {

    /**
     * Singleton
     */
    final static CpuInstructionSharedUnaryAslZpX INSTANCE = new CpuInstructionSharedUnaryAslZpX();

    private CpuInstructionSharedUnaryAslZpX() {
        super();
    }

    @Override
    public byte opcode() {
        return ASLZPX;
    }

    @Override
    CpuInstructionSharedOperand operand() {
        return CpuInstructionSharedOperand.ZPX;
    }
}
