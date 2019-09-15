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

import java.util.Objects;

/**
 * An {@link AddressBus} that provides read only memory, passing all writes to another {@link AddressBus}.
 */
final class Rom implements AddressBus {

    static Rom with(final byte[] values,
                    final AddressBus write) {
        Objects.requireNonNull(values, "values");
        Objects.requireNonNull(write, "write");

        return new Rom(values, write);
    }

    private Rom(final byte[] values,
                final AddressBus write) {
        super();
        this.values = values.clone();
        this.mask = values.length -1;
        this.write = write;
    }

    @Override
    public byte read(final int offset) {
        return this.values[offset & this.mask];
    }

    /**
     * The read only memory.
     */
    private final byte[] values;

    /**
     * Mask used to mask out unnecessary offset bits.
     */
    private final int mask;

    @Override
    public void write(final int offset, final byte value) {
        this.write.write(offset, value);
    }

    private final AddressBus write;

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
