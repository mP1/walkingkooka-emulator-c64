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
 * Compare Memory with Accumulator
 * <pre>
 * A - M
 * N	Z	C	I	D	V
 * +	+	+	-	-	-
 * </pre>
 */
final class CpuInstructionSharedBinaryConsumerCompare extends CpuInstructionSharedBinaryConsumer {

    /**
     * Singleton
     */
    final static CpuInstructionSharedBinaryConsumerCompare INSTANCE = new CpuInstructionSharedBinaryConsumerCompare();

    private CpuInstructionSharedBinaryConsumerCompare() {
        super();
    }

    @Override
    void handle(final byte left,
                final byte right,
                final CpuContext context) {
        setMinusAndZero(
            (byte) (mask(left) - mask(right)),
            context
        );
        context.setCarry(
            mask(left) >= mask(right)
        );
    }

    private static int mask(final byte value) {
        return 0xff & value;
    }
}
