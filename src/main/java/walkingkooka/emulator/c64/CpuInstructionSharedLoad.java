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
 * Base class for all load instructions.
 */
abstract class CpuInstructionSharedLoad extends CpuInstructionShared {

    CpuInstructionSharedLoad() {
        super();
    }

    @Override
    public final int length() {
        // the #register is encoded within the opcode, only the #source adds bytes.
        return 1 + this.source()
            .length();
    }

    @Override
    public final void execute(final CpuContext context) {
        Objects.requireNonNull(context, "context");

        final byte value = this.source()
            .readValue(context);

        setMinusAndZero(
            value,
            context
        );

        this.register()
            .writeRegister(
                value,
                context
            );
    }

    // LDA #$12
    // LDA $34,X
    @Override
    public final String disassemble(final CpuContext context) {
        return "LD" +
            this.register()
                .disassemble(context) +
            " " +
            this.source()
                .disassemble(context);
    }

    /**
     * The operand that provides the source value, such as IMM, ZP, ABS etc.
     */
    abstract CpuInstructionSharedOperandMemory source();

    /**
     * The target of the load which will be A, X or Y.
     */
    abstract CpuInstructionSharedOperandRegister register();
}
