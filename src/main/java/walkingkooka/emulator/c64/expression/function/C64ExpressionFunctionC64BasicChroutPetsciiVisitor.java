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

package walkingkooka.emulator.c64.expression.function;

import walkingkooka.emulator.c64.text.PetsciiVisitor;
import walkingkooka.text.printer.Printer;

/**
 * A {@link PetsciiVisitor} which currently handles printing only basic characters. Support for other control characters
 * like changing text colour is not yet implemented.
 */
final class C64ExpressionFunctionC64BasicChroutPetsciiVisitor extends PetsciiVisitor {

    static void chrout(final byte character,
                       final Printer printer) {
        new C64ExpressionFunctionC64BasicChroutPetsciiVisitor(printer)
            .accept(character);
    }

    // @VisibleForTesting
    C64ExpressionFunctionC64BasicChroutPetsciiVisitor(final Printer printer) {
        this.printer = printer;
    }

    @Override
    protected void visitCharacter(final char c) {
        this.printer.print(
            Character.valueOf(c)
                .toString()
        );
        this.printer.flush();
    }

    @Override
    protected void visitReturn() {
        this.printer.println();
        this.printer.flush();
    }

    private final Printer printer;

    // Object...........................................................................................................

    @Override
    public String toString() {
        return "CHROUT";
    }
}
