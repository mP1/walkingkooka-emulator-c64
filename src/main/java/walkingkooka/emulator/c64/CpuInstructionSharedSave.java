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

import java.util.Objects;

/**
 * Base class for all SAVE instructions.
 */
abstract class CpuInstructionSharedSave extends CpuInstructionShared {

    CpuInstructionSharedSave() {
        super();
    }

    @Override
    public final int length() {
        // the #register is encoded within the opcode, only the #destination adds bytes.
        return 1 + this.destination()
            .length();
    }

    @Override
    public final void execute(final CpuContext context) {
        Objects.requireNonNull(context, "context");

        this.destination()
            .writeValue(
                this.register()
                    .readRegister(
                        context
                    ),
                context
            );
    }

    // STA #$12
    // STA $34,X
    @Override
    public final String disassemble(final CpuContext context) {
        return "ST" +
            this.register()
                .disassemble(context) +
            " " +
            this.destination()
                .disassemble(context);
    }

    /**
     * The source being stored which will be A, X or Y.
     */
    abstract CpuInstructionSharedOperandRegister register();

    /**
     * The operand that provides the destination address, such as ZP, ABS etc.
     */
    abstract CpuInstructionSharedOperandMemory destination();
}
