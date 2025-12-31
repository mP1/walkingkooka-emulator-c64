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
 * https://www.masswerk.at/6502/6502_instruction_set.html#LSR
 * <p>
 * LSR
 * Shift One Bit Right (Memory or Accumulator)
 * <p>
 * 0 -> [76543210] -> C
 * N	Z	C	I	D	V
 * 0	+	+	-	-	-
 */
final class CpuInstructionSharedUnaryFunctionLsr extends CpuInstructionSharedUnaryFunction {

    final static CpuInstructionSharedUnaryFunctionLsr INSTANCE = new CpuInstructionSharedUnaryFunctionLsr();

    private CpuInstructionSharedUnaryFunctionLsr() {
        super();
    }

    @Override
    byte handle(final byte value,
                final CpuContext context) {
        final byte out = (byte) ((value >> 1) & 0x7f);

        setMinusAndZero(
            out,
            context
        );

        context.setCarry(
            Bit.BIT0.test(value)
        );

        return out;
    }
}
