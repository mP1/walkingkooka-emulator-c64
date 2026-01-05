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

abstract class CpuInstructionSharedBinaryConsumerBit extends CpuInstructionSharedBinaryConsumer {

    CpuInstructionSharedBinaryConsumerBit() {
        super();
    }

    @Override //
    final String mnemonic() {
        return "BIT";
    }

    @Override //
    final CpuInstructionSharedOperandRegister register() {
        return CpuInstructionSharedOperandRegister.A;
    }

    @Override //
    final void handle(final byte left,
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
