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
 * Interface for all devices including memory that support reading and writing bytes.
 */
public interface AddressBus {

    /**
     * Reads a single byte at the offset.
     */
    byte read(final int offset);

    /**
     * Writes a single byte to the given offset.
     */
    void write(final int offset, final byte value);

    /**
     * Creates a view of this {@link AddressBus} where reads/writes have the given base offset added.
     * This is useful to break up a large memory into smaller chunks.
     */
    default AddressBus setBaseOffset(final int offset) {
        return 0 == offset ?
                this :
                AddressBuses.baseOffset(offset, this);
    }
}
