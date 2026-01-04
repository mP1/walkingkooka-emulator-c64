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
 * Base class for instructions that accept operate on a register and an operand updating a register and updating flags.
 * Sub-classes implement instructions like ADC, AND, EOR, OR and SBC.
 */
abstract class CpuInstructionSharedBinaryBinaryFunction extends CpuInstructionSharedBinary {

    CpuInstructionSharedBinaryBinaryFunction() {
        super();
    }

    @Override
    public final void execute(final CpuContext context) {
        final CpuInstructionSharedOperandRegister register = this.register();

        register.writeRegister(
            this.function()
                .handle(
                    register.readValue(context),
                    this.memory()
                        .readValue(context),
                    context
                ),
            context
        );
    }

    abstract CpuInstructionSharedBinaryFunction function();
}
