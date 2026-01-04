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
 * BIT
 * Test Bits in Memory with Accumulator
 * <p>
 * bits 7 and 6 of operand are transfered to bit 7 and 6 of SR (N,V);
 * the zero-flag is set according to the result of the operand AND
 * the accumulator (set, if the result is zero, unset otherwise).
 * This allows a quick check of a few bits at once without affecting
 * any of the registers, other than the status register (SR).
 * <p>
 * → Further details.
 * <pre>
 * A AND M -> Z, M7 -> N, M6 -> V
 * N	Z	C	I	D	V
 * M7	+	-	-	-	M6
 * </pre>
 */
final class CpuInstructionSharedBinaryConsumerBit extends CpuInstructionSharedBinaryConsumer {

    /**
     * Singleton
     */
    static CpuInstructionSharedBinaryConsumerBit instance() {
        if (null == INSTANCE) {
            INSTANCE = new CpuInstructionSharedBinaryConsumerBit();
        }
        return INSTANCE;
    }

    private static CpuInstructionSharedBinaryConsumerBit INSTANCE;

    private CpuInstructionSharedBinaryConsumerBit() {
        super();
    }

    @Override
    void handle(final byte left,
                final byte right,
                final CpuContext context) {
        context.setZero(
            0 == (left & right)
        );
        context.setMinus(
            Bit.BIT7.test(right)
        );
        context.setOverflow(
            Bit.BIT6.test(right)
        );
    }

}
