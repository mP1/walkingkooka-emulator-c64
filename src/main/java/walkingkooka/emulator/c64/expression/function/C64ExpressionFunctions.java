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

import walkingkooka.reflect.PublicStaticHelper;

/**
 * A collection of {@link walkingkooka.tree.expression.function.ExpressionFunction} that probably use the C64 emulator.
 */
public final class C64ExpressionFunctions implements PublicStaticHelper {

    /**
     * Stop creation
     */
    private C64ExpressionFunctions() {
        throw new UnsupportedOperationException();
    }
}
