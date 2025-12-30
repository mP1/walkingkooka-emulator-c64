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

final class CpuInstructionTransferTax extends CpuInstructionTransfer {

    final static CpuInstructionTransferTax INSTANCE = new CpuInstructionTransferTax();

    private CpuInstructionTransferTax() {
        super();
    }

    @Override
    public byte opcode() {
        return (byte) 0xAA;
    }

    @Override
    byte read(final CpuContext context) {
        return context.a();
    }

    @Override
    void write(final byte value,
               final CpuContext context) {
        context.setX(value);

        this.setMinusAndZero(
            value,
            context
        );
    }
}
