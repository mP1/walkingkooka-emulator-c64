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

import org.junit.jupiter.api.Test;
import walkingkooka.reflect.ClassTesting2;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.util.FunctionTesting;

import java.util.Optional;

public final class PageTableComAddressSymbolsFunctionTest implements FunctionTesting<PageTableComAddressSymbolsFunction, Short, Optional<String>>,
    ClassTesting2<PageTableComAddressSymbolsFunction> {

    @Test
    public void testApply0000() {
        this.applyAndCheck(
            (short) 0x0000,
            "D6510"
        );
    }

    @Test
    public void testApply0001() {
        this.applyAndCheck(
            (short) 0x0001,
            "R6510"
        );
    }

    @Test
    public void testApply0003() {
        this.applyAndCheck(
            (short) 0x0003,
            "ADRAY1"
        );
    }

    @Test
    public void testApply0004() {
        this.applyAndCheck(
            (short) 0x0004,
            "ADRAY1+1"
        );
    }

    private void applyAndCheck(final short value) {
        this.applyAndCheck(
            value,
            Optional.empty()
        );
    }

    private void applyAndCheck(final short value,
                               final String expected) {
        this.applyAndCheck(
            value,
            Optional.of(expected)
        );
    }


    @Override
    public PageTableComAddressSymbolsFunction createFunction() {
        return PageTableComAddressSymbolsFunction.INSTANCE;
    }

    // class............................................................................................................

    @Override
    public Class<PageTableComAddressSymbolsFunction> type() {
        return PageTableComAddressSymbolsFunction.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }
}
