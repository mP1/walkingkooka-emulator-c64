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

import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * An interface with default methods that allow mixins
 */
public interface AddressBusTesting2<A extends AddressBus> extends AddressBusTesting {

    @Test
    default void testSetBaseOffsetZero() {
        final AddressBus bus = this.createAddressBus();
        assertSame(bus, bus.setBaseOffset(0));
    }

    @Test
    default void testSetBaseOffsetDifferentAndRead() {
        final AddressBus bus = this.createAddressBus();

        final int base = 256;
        final AddressBus bus2 = bus.setBaseOffset(-base);

        final int offset = 1;
        final byte read = bus.read(base + offset);
        this.readAndCheck(bus2, 0 + offset, read);
    }

    A createAddressBus();

    default void readAndCheck(final int offset,
                              final byte expected) {
        this.readAndCheck(this.createAddressBus(), offset, expected);
    }

    default void writeAndReadCheck(final int offset,
                                   final byte value) {
        this.writeAndReadCheck(this.createAddressBus(),
            offset,
            value);
    }
}
