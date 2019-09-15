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
 * Holds a page of memory, where page means 256 bytes in C6510 talk. Note the offset will be masked and only the bottom 8 bits used to identify the memory byte.
 */
final class MemoryPage implements AddressBus {

    /**
     * Creates a new page of memory
     */
    static MemoryPage create() {
        return new MemoryPage();
    }

    private MemoryPage() {
        super();
    }

    @Override
    public byte read(final int offset) {
        return this.values[offset & MASK];
    }

    @Override
    public void write(final int offset, final byte value) {
        this.values[offset & MASK] = value;
    }

    /**
     * The memory value.
     */
    private final byte[] values = new byte[256];

    /**
     * Mask used to mask out unnecessary offset bits.
     */
    private final static int MASK = 0xff;

    /**
     * Dumps the page memory as hex bytes.
     */
    @Override
    public String toString() {
        return ToStringBuilder.empty()
                .disable(ToStringBuilderOption.SKIP_IF_DEFAULT_VALUE)
                .enable(ToStringBuilderOption.HEX_BYTES)
                .value(this.values)
                .build();
    }
}
