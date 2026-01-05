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
 * https://www.masswerk.at/6502/6502_instruction_set.html#AND
 * <pre>
 * AND
 * AND Memory with Accumulator
 *
 * A AND M -> A
 * N	Z	C	I	D	V
 * +	+	-	-	-	-
 * addressing	assembler	opc	bytes	cycles
 * immediate	AND #oper	29	2	2
 * </pre>
 */
final class CpuInstructionSharedBinaryBinaryFunctionAndImm extends CpuInstructionSharedBinaryBinaryFunctionAnd {

    static CpuInstructionSharedBinaryBinaryFunctionAndImm instance() {
        if (null == INSTANCE) {
            INSTANCE = new CpuInstructionSharedBinaryBinaryFunctionAndImm();
        }
        return INSTANCE;
    }

    private static CpuInstructionSharedBinaryBinaryFunctionAndImm INSTANCE;

    private CpuInstructionSharedBinaryBinaryFunctionAndImm() {
        super();
    }

    @Override
    public byte opcode() {
        return ANDIMM;
    }

    @Override
    CpuInstructionSharedOperand memory() {
        return CpuInstructionSharedOperand.IMMEDIATE;
    }
}
