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

import walkingkooka.text.CharSequences;

interface Numbers {

    default byte hi(final short value) {
        return (byte) (value >> 8);
    }

    default byte lo(final short value) {
        return (byte) (0xff & value);
    }

    default int mask(final byte value) {
        return value & 0xFF;
    }

    default int mask(final int value) {
        return value & 0xFF;
    }

    // $00
    // $01
    // $FF
    default CharSequence hexByte(final byte value) {
        return "$" + CharSequences.padLeft(
            Integer.toHexString(
                mask(value)
            ).toUpperCase(),
            2,
            '0'
        );
    }

    // $0000
    // $0001
    // $F000
    default String hexAddress(final short value) {
        return "$" +
            CharSequences.padLeft(
                Integer.toHexString(0xFFFF & value)
                    .toUpperCase(),
                4,
                '0'
            );
    }

    default void setMinusAndZero(final byte value,
                                 final CpuContext context) {
        context.setZero(0 == value);
        context.setMinus(value < 0);
    }
}
