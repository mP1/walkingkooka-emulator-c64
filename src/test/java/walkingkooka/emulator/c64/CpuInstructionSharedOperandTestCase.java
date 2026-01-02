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

public abstract class CpuInstructionSharedOperandTestCase<T extends CpuInstructionSharedOperand> implements ClassTesting<T> {

    CpuInstructionSharedOperandTestCase() {
        super();
    }

    // length...........................................................................................................

    final void lengthAndCheck(final T operand,
                              final int expected) {
        this.checkEquals(
            expected,
            operand.length(),
            operand::toString
        );
    }

    // disssemble.......................................................................................................

    final void disassembleAndCheck(final T operand,
                                   final CpuContext context,
                                   final String expected) {
        this.checkEquals(
            operand.disassemble(context),
            expected,
            operand::toString
        );
    }

    abstract T createCpuInstructionSharedOperand();

    // helpers..........................................................................................................

    final void pcAndCheck(final CpuContext context,
                          final short expected) {
        this.checkEquals(
            hex(expected),
            hex(
                context.pc()
            ),
            context::toString
        );
    }

    static String hex(final byte value) {
        return hex(0xff & value);
    }

    static String hex(final short value) {
        return hex(0xffff & value);
    }

    // $2000 4096
    private static String hex(final int value) {
        return "$" + Integer.toHexString(value) +
            " " +
            value;
    }

    // class............................................................................................................

    @Override
    public final JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }
}
