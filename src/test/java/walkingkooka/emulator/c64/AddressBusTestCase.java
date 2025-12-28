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

import walkingkooka.ToStringTesting;
import walkingkooka.reflect.ClassTesting2;
import walkingkooka.reflect.JavaVisibility;

public abstract class AddressBusTestCase<A extends AddressBus> implements AddressBusTesting2<A>,
    ClassTesting2<A>,
    ToStringTesting<A> {

    final static byte ZERO = 0;
    final static byte ONE = 1;
    final static byte TWO = 2;
    final static byte THREE = 3;
    final static byte FOUR = 4;
    final static byte FIVE = 5;
    final static byte SIX = 6;
    final static byte SEVEN = 7;
    final static byte EIGHT = 8;
    final static byte NINE = 9;
    final static byte FIFTY_EIGHT_BCD = 0x58;
    final static byte FIFTY_NINE_BCD = 0x59;
    final static byte NEGATIVE_ONE = -1;

    AddressBusTestCase() {
        super();
    }

    // Object..........................................................................................................

    @Override
    public final JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }
}
