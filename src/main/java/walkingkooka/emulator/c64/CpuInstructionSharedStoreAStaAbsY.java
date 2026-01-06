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
 * https://www.masswerk.at/6502/6502_instruction_set.html#STA
 * <pre>
 * STA
 * Store Accumulator in Memory
 *
 * A -> M
 * N	Z	C	I	D	V
 * -	-	-	-	-	-
 * addressing	assembler	opc	bytes	cycles
 * absolute,Y	STA oper,Y	99	3	5
 * </pre>
 */
final class CpuInstructionSharedStoreAStaAbsY extends CpuInstructionSharedStoreA {

    static CpuInstructionSharedStoreAStaAbsY instance() {
        if (null == INSTANCE) {
            INSTANCE = new CpuInstructionSharedStoreAStaAbsY();
        }
        return INSTANCE;
    }

    private static CpuInstructionSharedStoreAStaAbsY INSTANCE;

    private CpuInstructionSharedStoreAStaAbsY() {
        super();
    }

    @Override
    public byte opcode() {
        return STAABSY;
    }

    @Override
    CpuInstructionSharedOperandMemory destination() {
        return CpuInstructionSharedOperandMemory.ABSY;
    }
}
