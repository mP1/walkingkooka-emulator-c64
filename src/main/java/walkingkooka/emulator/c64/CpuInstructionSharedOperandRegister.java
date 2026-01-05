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

abstract class CpuInstructionSharedOperandRegister extends CpuInstructionSharedOperand {

    CpuInstructionSharedOperandRegister() {
        super();
    }

    @Override //
    final int length() {
        return 0;
    }

    @Override final byte readValue(final CpuContext context) {
        return this.readRegister(context);
    }

    @Override
    void handleUnaryFunction(final CpuInstructionSharedUnaryFunction function,
                             final CpuContext context) {
        this.writeRegister(
            function.handle(
                this.readRegister(context),
                context
            ),
            context
        );
    }

    /**
     * Getter that returns the A, X or Y
     */
    abstract byte readRegister(final CpuContext context);

    /**
     * Writes the A, X, Y without updating any flags.
     */
    abstract void writeRegister(final byte value,
                                final CpuContext context);
}
