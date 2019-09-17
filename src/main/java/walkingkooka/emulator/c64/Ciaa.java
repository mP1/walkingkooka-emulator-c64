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

import java.util.Objects;

/**
 * The first CIA.
 */
final class Ciaa extends Cia {

    static Ciaa with(final Runnable interrupt) {
        Objects.requireNonNull(interrupt);

        return new Ciaa(interrupt);
    }

    private Ciaa(final Runnable interrupt) {
        super(interrupt);
    }

    @Override
    byte readDataPortA() {
        return 0;
    }

    @Override
    byte readDataPortB() {
        return 0;
    }

    @Override
    void writeDataPortA(final byte value) {

    }

    @Override
    void writeDataPortB(final byte value) {

    }
}