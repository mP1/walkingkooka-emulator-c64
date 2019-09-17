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
 * The second CIAB chip.
 */
final class Ciab extends Cia {

    static Ciab with(final VicMapper mapper,
                     final Runnable interrupt) {
        Objects.requireNonNull(mapper, "mapper");
        Objects.requireNonNull(interrupt, "interrupt");

        return new Ciab(mapper, interrupt);
    }

    private Ciab(final VicMapper mapper,
                 final Runnable interrupt) {
        super(interrupt);
        this.mapper = mapper;
    }

    /**
     * <pre>
     * Port A, serial bus access. Bits:
     *
     * Bits #0-#1: VIC bank. Values:
     * %00, 0: Bank #3, $C000-$FFFF, 49152-65535.
     * %01, 1: Bank #2, $8000-$BFFF, 32768-49151.
     * %10, 2: Bank #1, $4000-$7FFF, 16384-32767.
     * %11, 3: Bank #0, $0000-$3FFF, 0-16383.
     * </pre>
     */
    @Override
    byte readDataPortA() {
        return this.portA;
    }

    @Override
    byte readDataPortB() {
        return this.portB;
    }

    @Override
    void writeDataPortA(final byte value) {
        this.portA = value;

        this.mapper.setBank(VicBank.fromDataPortByte(value));
    }

    @Override
    void writeDataPortB(final byte value) {
        this.portB = value;
    }

    private byte portA;
    private byte portB;

    private final VicMapper mapper;
}
