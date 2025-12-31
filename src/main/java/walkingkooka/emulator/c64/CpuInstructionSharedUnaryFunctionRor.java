/*
 * Copyright 2019 Miroslav Pokorny (github.com/mP1)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
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
 * https://www.masswerk.at/6502/6502_instruction_set.html#ROR
 * <p>
 * ROR
 * Rotate One Bit Right (Memory or Accumulator)
 * <p>
 * C -> [76543210] -> C
 * N	Z	C	I	D	V
 * +	+	+	-	-	-
 */
final class CpuInstructionSharedUnaryFunctionRor extends CpuInstructionSharedUnaryFunction {

    final static CpuInstructionSharedUnaryFunctionRor INSTANCE = new CpuInstructionSharedUnaryFunctionRor();

    private CpuInstructionSharedUnaryFunctionRor() {
        super();
    }

    @Override
    byte handle(final byte value,
                final CpuContext context) {
        final byte in = context.isCarry() ?
            (byte) 0x80 :
            0;

        final byte out = (byte) (in |
            ((value & 0xff) >> 1));

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
