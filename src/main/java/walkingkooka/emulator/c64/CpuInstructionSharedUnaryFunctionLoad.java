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
 * LDA
 * Load Accumulator with Memory
 * <p>
 * M -> A
 * N	Z	C	I	D	V
 * +	+	-	-	-	-
 */
final class CpuInstructionSharedUnaryFunctionLoad extends CpuInstructionSharedUnaryFunction {

    static CpuInstructionSharedUnaryFunctionLoad instance() {
        return new CpuInstructionSharedUnaryFunctionLoad();
    }

    private CpuInstructionSharedUnaryFunctionLoad() {
        super();
    }

    @Override
    byte handle(final byte value,
                final CpuContext context) {
        setMinusAndZero(
            value,
            context
        );
        return value;
    }

    @Override
    String mnemonic() {
        return "LOAD";
    }
}
