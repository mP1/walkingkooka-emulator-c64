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

import walkingkooka.emulator.c64.text.PetsciiVisitorTesting;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.text.printer.Printers;

public final class C64ExpressionFunctionC64BasicChroutPetsciiVisitorTest implements PetsciiVisitorTesting<C64ExpressionFunctionC64BasicChroutPetsciiVisitor> {
    @Override
    public C64ExpressionFunctionC64BasicChroutPetsciiVisitor createVisitor() {
        return new C64ExpressionFunctionC64BasicChroutPetsciiVisitor(Printers.fake());
    }

    @Override
    public Class<C64ExpressionFunctionC64BasicChroutPetsciiVisitor> type() {
        return C64ExpressionFunctionC64BasicChroutPetsciiVisitor.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }
}
