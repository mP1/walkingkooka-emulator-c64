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
 * https://www.masswerk.at/6502/6502_instruction_set.html#LDX
 * <pre>
 * LDX
 * Load Index X with Memory
 *
 * M -> X
 * N	Z	C	I	D	V
 * +	+	-	-	-	-
 * addressing	assembler	opc	bytes	cycles
 * zeropage	LDX oper	A6	2	3
 * </pre>
 */
final class CpuInstructionSharedLoadXLdxZp extends CpuInstructionSharedLoadX {

    static CpuInstructionSharedLoadXLdxZp instance() {
        if (null == INSTANCE) {
            INSTANCE = new CpuInstructionSharedLoadXLdxZp();
        }
        return INSTANCE;
    }

    private static CpuInstructionSharedLoadXLdxZp INSTANCE;

    private CpuInstructionSharedLoadXLdxZp() {
        super();
    }

    @Override
    public byte opcode() {
        return LDXZP;
    }

    @Override
    CpuInstructionSharedOperandMemory source() {
        return CpuInstructionSharedOperandMemory.ZP;
    }
}
