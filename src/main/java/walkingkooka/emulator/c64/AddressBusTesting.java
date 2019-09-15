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

import walkingkooka.test.Testing;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * An interface with default methods that allow mixins
 */
public interface AddressBusTesting extends Testing {

    default void readAndCheck(final AddressBus bus,
                              final int offset,
                              final byte expected) {
        assertEquals(expected, bus.read(offset), () -> bus + " read " + offset);
    }

    default void writeAndReadCheck(final AddressBus bus,
                                   final int offset,
                                   final byte value) {
        bus.write(offset, value);
        this.readAndCheck(bus, offset, value);
    }
}
