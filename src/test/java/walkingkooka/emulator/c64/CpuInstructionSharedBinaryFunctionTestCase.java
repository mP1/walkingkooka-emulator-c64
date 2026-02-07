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

public abstract class CpuInstructionSharedBinaryFunctionTestCase<T extends CpuInstructionSharedBinaryFunction> extends CpuInstructionSharedBinaryTestCase<T> {

    CpuInstructionSharedBinaryFunctionTestCase() {
        super();
    }

    final void executeImmediateAndCheck(final byte a,
                                        final byte immediateValue,
                                        final String flags,
                                        final byte expectedA,
                                        final String expectedFlags) {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256),
            SYMBOL_LOOKUP_UOE
        );

        final short pc = 0x1000;
        context.setPc(pc);

        context.setA(a);
        context.setFlags(
            CpuFlags.parse(flags)
                .value()
        );

        context.writeByte(
            pc,
            immediateValue
        );

        this.executeAccumulatorAndCheck(
            context,
            expectedA,
            expectedFlags
        );
    }
}
