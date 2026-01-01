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

import static walkingkooka.emulator.c64.CpuInstructionShared.setMinusAndZero;

/**
 * https://www.masswerk.at/6502/6502_instruction_set.html#ASL
 * Shift Left One Bit (Memory or Accumulator)
 * <p>
 * C <- [76543210] <- 0
 * N	Z	C	I	D	V
 * +	+	+	-	-	-
 */
final class CpuInstructionSharedUnaryFunctionAsl extends CpuInstructionSharedUnaryFunction {

    final static CpuInstructionSharedUnaryFunctionAsl INSTANCE = new CpuInstructionSharedUnaryFunctionAsl();

    private CpuInstructionSharedUnaryFunctionAsl() {
        super();
    }

    @Override
    byte handle(final byte value,
                final CpuContext context) {
        final byte out = (byte) (value << 1);

        setMinusAndZero(
            out,
            context
        );

        context.setCarry(
            Bit.BIT7.test(value)
        );

        return out;
    }

    @Override
    String mnemonic() {
        return "ASL";
    }
}
