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

/**
 * Helper that assists reading and writing bits within a byte.
 */
enum Bit {

    BIT7(7),
    BIT6(6),
    BIT5(5),
    BIT4(4),
    BIT3(3),
    BIT2(2),
    BIT1(1),
    BIT0(0);

    Bit(final int bitNumber) {
        this.mask = (byte)(1 << bitNumber);
        this.notMask = (byte) ~ this.mask;
    }

    /**
     * Read a bit from the byte.
     */
    final boolean read(final byte value) {
        return 0 != (value & this.mask);
    }

    final byte not() {
        return this.notMask;
    }

    final byte set() {
        return this.mask;
    }

    /**
     * Sets a bit in the byte returning the result
     */
    final byte set(final byte value) {
        return (byte)(value | this.mask);
    }

    /**
     * Clear a bit in the byte returning the result
     */
    final byte clear(final byte value) {
        return (byte)(value & this.notMask);
    }

    /**
     * Mask that selects this bit.
     */
    private final byte mask;
    private final byte notMask;
}
