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
 * https://www.masswerk.at/6502/6502_instruction_set.html#LDA
 * <pre>
 * LDA
 * Load Accumulator with Memory
 *
 * M -> A
 * N	Z	C	I	D	V
 * +	+	-	-	-	-
 * addressing	assembler	opc	bytes	cycles
 * (indirect,X)	LDA (oper,X)	A1	2	6
 * </pre>
 */
final class CpuInstructionSharedLoadALdaIndX extends CpuInstructionSharedLoadA {

    static CpuInstructionSharedLoadALdaIndX instance() {
        if (null == INSTANCE) {
            INSTANCE = new CpuInstructionSharedLoadALdaIndX();
        }
        return INSTANCE;
    }

    private static CpuInstructionSharedLoadALdaIndX INSTANCE;

    private CpuInstructionSharedLoadALdaIndX() {
        super();
    }

    @Override
    public byte opcode() {
        return LDAINDX;
    }

    @Override
    CpuInstructionSharedOperandMemory source() {
        return CpuInstructionSharedOperandMemory.INDX;
    }
}
