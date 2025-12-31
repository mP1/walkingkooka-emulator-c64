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

import java.util.function.Supplier;

public abstract class CpuInstructionSharedBinaryFunctionTestCase<T extends CpuInstructionSharedBinaryFunction> implements ClassTesting<T> {

    CpuInstructionSharedBinaryFunctionTestCase() {
        super();
    }

    void handleAndCheck(final byte left,
                        final byte right,
                        final String flags,
                        final byte expectedValue,
                        final String expectedFlags) {
        this.handleAndCheck(
            left,
            right,
            CpuFlags.parse(flags),
            expectedValue,
            CpuFlags.parse(expectedFlags)
        );
    }

    void handleAndCheck(final byte left,
                        final byte right,
                        final CpuFlags flags,
                        final byte expectedValue,
                        final CpuFlags expectedFlags) {
        this.handleAndCheck(
            this.createCpuInstructionSharedBinaryFunction(),
            left,
            right,
            flags,
            expectedValue,
            expectedFlags
        );
    }

    void handleAndCheck(final T function,
                        final byte left,
                        final byte right,
                        final CpuFlags flags,
                        final byte expectedValue,
                        final CpuFlags expectedFlags) {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.fake()
        );

        context.setFlags(
            flags.value()
        );

        this.checkEquals(
            expectedValue,
            function.handle(
                left,
                right,
                context
            ),
            () -> function + " " + Integer.toHexString(left) + " " + Integer.toHexString(right)
        );

        final CpuFlags actualFlags = CpuFlags.create();
        actualFlags.setValue(context.flags());

        this.checkEquals(
            expectedFlags,
            actualFlags
        );
    }

    void checkEquals(final Byte expected,
                     final Byte actual,
                     final Supplier<String> message) {
        this.checkEquals(
            "$" + Integer.toHexString(0xff & expected),
            "$" + Integer.toHexString(0xff & actual),
            message
        );
    }

    abstract T createCpuInstructionSharedBinaryFunction();

    // class............................................................................................................

    @Override
    public final JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }
}
