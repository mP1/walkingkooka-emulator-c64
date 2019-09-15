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
import walkingkooka.collect.list.Lists;

import java.util.List;
import java.util.Objects;

/**
 * An {@link AddressBus} that dispatches to a page for a given larger address space.
 */
final class PagedAddressBus implements AddressBus {

    static PagedAddressBus with(final List<AddressBus> pages) {
        Objects.requireNonNull(pages, "pages");

        return new PagedAddressBus(Lists.immutable(pages));
    }

    private PagedAddressBus(final List<AddressBus> pages) {
        super();

        final int pageCount = pages.size();
        this.pages = pages.toArray(new AddressBus[pageCount]);
        this.mask = pageCount - 1;
    }

    @Override
    public byte read(final int offset) {
        return this.page(offset).read(offset);
    }

    @Override
    public void write(final int offset, final byte value) {
        this.page(offset).write(offset, value);
    }

    /**
     * Fetches the {@link AddressBus} at the given page.
     */
    private AddressBus page(final int offset) {
        return this.pages[this.mask & (offset >> 8)];
    }

    private final AddressBus[] pages;
    private final int mask;

    @Override
    public String toString() {
        return ToStringBuilder.empty()
                .value(this.pages)
                .build();
    }
}
