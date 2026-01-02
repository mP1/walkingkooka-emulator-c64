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
 * https://www.masswerk.at/6502/6502_instruction_set.html#INX
 * <pre>
 * INX
 * Increment Index X by One
 *
 * X + 1 -> X
 * N	Z	C	I	D	V
 * +	+	-	-	-	-
 * addressing	assembler	opc	bytes	cycles
 * implied	INX	E8	1	2
 * </pre>
 */
final class CpuInstructionSharedUnaryInx extends CpuInstructionSharedUnaryInc {

    /**
     * Singleton
     */
    final static CpuInstructionSharedUnaryInx INSTANCE = new CpuInstructionSharedUnaryInx();

    private CpuInstructionSharedUnaryInx() {
        super();
    }

    @Override
    public byte opcode() {
        return INX;
    }

    @Override
    CpuInstructionSharedOperand operand() {
        return CpuInstructionSharedOperand.X;
    }
}
