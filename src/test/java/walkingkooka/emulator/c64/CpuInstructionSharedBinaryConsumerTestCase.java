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

import walkingkooka.reflect.ClassTesting;
import walkingkooka.reflect.JavaVisibility;

public abstract class CpuInstructionSharedBinaryConsumerTestCase<T extends CpuInstructionSharedBinaryConsumer> implements ClassTesting<T> {

    CpuInstructionSharedBinaryConsumerTestCase() {
        super();
    }

    final void handleAndCheck(final byte left,
                              final byte right,
                              final String flags,
                              final String expectedFlags) {
        this.handleAndCheck(
            left,
            right,
            CpuFlags.parse(flags),
            CpuFlags.parse(expectedFlags)
        );
    }

    final void handleAndCheck(final byte left,
                              final byte right,
                              final CpuFlags flags,
                              final CpuFlags expectedFlags) {
        this.handleAndCheck(
            this.createCpuInstructionSharedBinaryConsumer(),
            left,
            right,
            flags,
            expectedFlags
        );
    }

    final void handleAndCheck(final T function,
                              final byte left,
                              final byte right,
                              final CpuFlags flags,
                              final CpuFlags expectedFlags) {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.fake()
        );

        final byte a = context.a();
        final byte x = context.x();
        final byte y = context.y();

        context.setFlags(
            flags.value()
        );

        function.handle(
            left,
            right,
            context
        );

        final CpuFlags actualFlags = CpuFlags.create();
        actualFlags.setValue(context.flags());

        this.checkEquals(
            a,
            context.a(),
            "a"
        );

        this.checkEquals(
            x,
            context.x(),
            "x"
        );

        this.checkEquals(
            y,
            context.y(),
            "y"
        );

        this.checkEquals(
            expectedFlags,
            actualFlags
        );
    }

    abstract T createCpuInstructionSharedBinaryConsumer();

    // class............................................................................................................

    @Override
    public final JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }
}
