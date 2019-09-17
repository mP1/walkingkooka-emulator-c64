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
 * <pre>
 * Bit 0..1: Select the position of the VIC-memory
 *  %00, 0: Bank 3: $C000-$FFFF, 49152-65535
 *  %01, 1: Bank 2: $8000-$BFFF, 32768-49151
 *  %10, 2: Bank 1: $4000-$7FFF, 16384-32767
 *  %11, 3: Bank 0: $0000-$3FFF, 0-16383 (standard)
 * </pre>
 */
enum VicBank {
    BANK0(0),
    BANK1(1),
    BANK2(2),
    BANK3(3);

    VicBank(final int number) {
        this.number = number;
    }

    final int number;

    static VicBank fromDataPortByte(final byte value) {
        VicBank bank;

        switch (value) {
            case 0:
                bank = BANK3;
                break;
            case 1:
                bank = BANK2;
                break;
            case 2:
                bank = BANK1;
                break;
            case 3:
                bank = BANK0;
                break;
            default:
                throw new UnsupportedOperationException();
        }
        return bank;
    }
}
