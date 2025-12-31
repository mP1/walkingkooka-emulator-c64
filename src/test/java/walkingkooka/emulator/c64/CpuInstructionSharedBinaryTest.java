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

public final class CpuInstructionSharedBinaryTest implements ClassTesting<CpuInstructionSharedBinary> {

    private void processValueAndCheck(final BinaryFunction function,
                                      final byte left,
                                      final byte right,
                                      final String flags,
                                      final byte expectedValue,
                                      final String expectedFlags) {
        this.processValueAndCheck(
            function,
            left,
            right,
            CpuFlags.parse(flags),
            expectedValue,
            CpuFlags.parse(expectedFlags)
        );
    }

    private void processValueAndCheck(final BinaryFunction function,
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
            function.apply(
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

    interface BinaryFunction {
        byte apply(final byte left,
                   final byte right,
                   final CpuContext context);
    }

    private void checkEquals(final Byte expected,
                             final Byte actual,
                             final Supplier<String> message) {
        this.checkEquals(
            "$" + Integer.toHexString(0xff & expected),
            "$" + Integer.toHexString(0xff & actual),
            message
        );
    }

    // class............................................................................................................

    @Override
    public Class<CpuInstructionSharedBinary> type() {
        return CpuInstructionSharedBinary.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }
}
