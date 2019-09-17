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
 * Holds a block of read/write memory.
 * Note the offset will be masked and only the bottom 8 bits used to identify the memory byte.
 */
final class Memory implements AddressBus {

    /**
     * Creates some memory. The size should be a power of two.
     */
    static Memory with(final int size) {
        if(size <= 0) {
            throw new IllegalArgumentException("Size " + size + " <= 0");
        }
        return new Memory(size);
    }

    private Memory(final int size) {
        super();
        this.values = new byte[size];
        this.mask = size - 1;
    }

    @Override
    public byte read(final int offset) {
        return this.values[offset & this.mask];
    }

    @Override
    public void write(final int offset, final byte value) {
        this.values[offset & this.mask] = value;
    }

    /**
     * The memory value.
     */
    private final byte[] values;

    /**
     * Mask used to mask out unnecessary offset bits.
     */
    private final int mask;

    /**
     * Reports memory and the read/write mask.
     */
    @Override
    public String toString() {
        return "Memory 0x" + Integer.toHexString(this.mask);
    }
}
