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

final class CpuInstructionSharedTransferTxa extends CpuInstructionSharedTransfer {

    static CpuInstructionSharedTransferTxa instance() {
        if (null == INSTANCE) {
            INSTANCE = new CpuInstructionSharedTransferTxa();
        }
        return INSTANCE;
    }

    private static CpuInstructionSharedTransferTxa INSTANCE;

    private CpuInstructionSharedTransferTxa() {
        super();
    }

    @Override
    public byte opcode() {
        return TXA;
    }

    @Override
    byte read(final CpuContext context) {
        return context.x();
    }

    @Override
    void write(final byte value,
               final CpuContext context) {
        context.setA(value);

        setMinusAndZero(
            value,
            context
        );
    }
}
