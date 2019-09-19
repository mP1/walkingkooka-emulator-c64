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

import walkingkooka.ToStringBuilder;
import walkingkooka.ToStringBuilderOption;

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
        this.number = bitNumber;
        this.mask = (byte)(1 << bitNumber);
        this.notMask = (byte) ~ this.mask;
    }

    /**
     * Returns the next bit to the right with {@link Bit#BIT0} returning {@link Bit#BIT7}.
     */
    final Bit right() {
        return of(7 & (this.number - 1));
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
     * Combines this bit and the parameters into a single byte value.
     */
    byte or(final Bit bit,
            final Bit... bits) {
        byte value = this.mask;
        value |= bit.mask;

        for (Bit b : bits) {
            value |= b.mask;
        }

        return value;
    }

    /**
     * Combines this bit and the parameters into a single byte value.
     */
    byte andNot(final Bit bit,
                final Bit... bits) {
        byte value = this.notMask;
        value &= bit.notMask;

        for (Bit b : bits) {
            value &= b.notMask;
        }

        return value;
    }

    /**
     * Returns the text if the bit is set or empty String.
     */
    String text(final byte value,
                final String text) {
        return this.read(value) ?
                text :
                "";
    }

    /**
     * Mask that selects this bit.
     */
    private final byte mask;
    private final byte notMask;

    int number() {
        return this.number;
    }

    private final int number;

    /**
     * Returns a string testing all the bits in the bit and using the text for that match separated by a space.
     */
    static String byteText(final byte value,
                           final String bit7,
                           final String bit6,
                           final String bit5,
                           final String bit4,
                           final String bit3,
                           final String bit2,
                           final String bit1,
                           final String bit0) {
        return ToStringBuilder.empty()
                .separator(" ")
                .enable(ToStringBuilderOption.SKIP_IF_DEFAULT_VALUE)
                .disable(ToStringBuilderOption.QUOTE)
                .value(BIT7.read(value) ? bit7 : "")
                .value(BIT6.read(value) ? bit6 : "")
                .value(BIT5.read(value) ? bit5 : "")
                .value(BIT4.read(value) ? bit4 : "")
                .value(BIT3.read(value) ? bit3 : "")
                .value(BIT2.read(value) ? bit2 : "")
                .value(BIT1.read(value) ? bit1 : "")
                .value(BIT0.read(value) ? bit0 : "")
                .build();
    }

    /**
     * Returns the {@link Bit} for the given number.
     */
    static Bit of(final int number) {
        return NUMBER[number];
    }

    private final static Bit[] NUMBER = valuesToNumber();

    private static Bit[] valuesToNumber() {
        final Bit[] number = new Bit[8];

        for (Bit bit : values()) {
            number[bit.number] = bit;
        }

        return number;
    }
}
