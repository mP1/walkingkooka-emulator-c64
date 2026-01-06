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
 * https://www.masswerk.at/6502/6502_instruction_set.html#PLP
 * <pre>
 * PLP
 * Pull Processor Status from Stack
 *
 * The status register will be pulled with the break
 * flag and bit 5 ignored.
 *
 * pull SR
 * N	Z	C	I	D	V
 * from stack
 * addressing	assembler	opc	bytes	cycles
 * implied	PLP	28	1	4
 * </pre>
 */
final class CpuInstructionSharedTransferPlp extends CpuInstructionSharedTransfer {

    static CpuInstructionSharedTransferPlp instance() {
        if (null == INSTANCE) {
            INSTANCE = new CpuInstructionSharedTransferPlp();
        }
        return INSTANCE;
    }

    private static CpuInstructionSharedTransferPlp INSTANCE;

    private CpuInstructionSharedTransferPlp() {
        super();
    }

    @Override
    public byte opcode() {
        return PLP;
    }

    @Override
    byte read(final CpuContext context) {
        return context.pop();
    }

    @Override
    void write(final byte value,
               final CpuContext context) {
        // dont replace current break flag
        final CpuFlags flags = CpuFlags.create();
        flags.setValue(value);
        flags.setBreak(context.isBreak());

        context.setFlags(
            flags.value()
        );
    }
}
