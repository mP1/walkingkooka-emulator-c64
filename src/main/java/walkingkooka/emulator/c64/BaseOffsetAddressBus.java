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
 * An {@link AddressBus} that wraps a larger bus supporting a base offset.
 */
final class BaseOffsetAddressBus implements AddressBus {

    static AddressBus with(final int baseOffset,
                           final AddressBus bus) {
        Objects.requireNonNull(bus, "bus");
        return 0 == baseOffset ?
            bus :
            new BaseOffsetAddressBus(baseOffset, bus);
    }

    private BaseOffsetAddressBus(final int baseOffset,
                                 final AddressBus bus) {
        this.baseOffset = baseOffset;
        this.bus = bus;
    }

    @Override
    public byte read(final int offset) {
        return this.bus.read(this.baseOffset + offset);
    }

    @Override
    public void write(final int offset,
                      final byte value) {
        this.bus.write(this.baseOffset + offset,
            value);
    }

    @Override
    public int size() {
        return this.bus.size();
    }

    private final int baseOffset;
    private final AddressBus bus;

    @Override
    public AddressBus setBaseOffset(final int offset) {
        return 0 == offset ?
            this :
            new BaseOffsetAddressBus(offset - this.baseOffset, this.bus);
    }

    @Override
    public String toString() {
        return "baseOffset: " + this.baseOffset + " " + this.bus;
    }
}
