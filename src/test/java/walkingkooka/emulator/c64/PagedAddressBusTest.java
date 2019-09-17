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

import org.junit.jupiter.api.Test;
import walkingkooka.collect.list.Lists;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class PagedAddressBusTest extends AddressBusTestCase<PagedAddressBus> {

    @Test
    public void testWithNullPagesFails() {
        assertThrows(NullPointerException.class, () -> {
            PagedAddressBus.with(null);
        });
    }

    @Test
    public void testRead() {
        final AddressBus zero = AddressBuses.fake();
        final AddressBus one = AddressBuses.fake();
        final AddressBus three = AddressBuses.fake();

        final AddressBus two = AddressBuses.memory(256);

        final byte value1 = 12;
        final byte value2 = 34;

        final int offset1 = 1;
        final int offset2 = 2;

        two.write(offset1, value1);
        two.write(offset2, value2);

        final PagedAddressBus paged = PagedAddressBus.with(Lists.of(zero, one, two, three));
        this.readAndCheck(paged, 512 + offset1, value1);
        this.readAndCheck(paged, 512 + offset2, value2);
    }

    @Test
    public void testRead2() {
        final AddressBus one = AddressBuses.fake();
        final AddressBus three = AddressBuses.fake();

        final AddressBus zero = AddressBuses.memory(256);
        final byte value1 = 12;
        final int offset1 = 1;
        zero.write(offset1, value1);

        final AddressBus two = AddressBuses.memory(256);
        final byte value2 = 34;
        final int offset2 = 2;
        two.write(offset2, value2);

        final PagedAddressBus paged = PagedAddressBus.with(Lists.of(zero, one, two, three));
        this.readAndCheck(paged, 0 + offset1, value1);
        this.readAndCheck(paged, 512 + offset2, value2);
    }

    @Test
    public void testRead3() {
        final AddressBus one = AddressBuses.fake();
        final AddressBus two = AddressBuses.fake();

        final AddressBus zero = AddressBuses.memory(256);
        final byte value1 = 12;
        final int offset1 = 1;
        zero.write(offset1, value1);

        final AddressBus three = AddressBuses.memory(256);
        final byte value2 = 34;
        final int offset2 = 2;
        three.write(offset2, value2);

        final PagedAddressBus paged = PagedAddressBus.with(Lists.of(zero, one, two, three));
        this.readAndCheck(paged, 0 + offset1, value1);
        this.readAndCheck(paged, 3 * 256 + offset2, value2);
    }

    @Test
    public void testReadHighbitsIgnored() {
        final AddressBus zero = AddressBuses.fake();
        final AddressBus one = AddressBuses.fake();
        final AddressBus three = AddressBuses.fake();

        final AddressBus two = AddressBuses.memory(256);
        final byte value = 34;
        final int offset = 2;
        two.write(offset, value);

        final PagedAddressBus paged = PagedAddressBus.with(Lists.of(zero, one, two, three));
        this.readAndCheck(paged, 16384 + 512 + offset, value);
        this.readAndCheck(paged, 512 + offset, value);
    }

    @Test
    public void testWrite() {
        final AddressBus zero = AddressBuses.fake();
        final AddressBus one = AddressBuses.fake();
        final AddressBus three = AddressBuses.fake();

        final AddressBus two = AddressBuses.memory(256);

        final byte value1 = 12;
        final byte value2 = 34;

        final int offset1 = 1;
        final int offset2 = 2;

        two.write(offset1, value1);
        two.write(offset2, value2);

        final PagedAddressBus paged = PagedAddressBus.with(Lists.of(zero, one, two, three));
        this.writeAndReadCheck(paged, 512 + offset1, value1);
        this.writeAndReadCheck(paged, 512 + offset2, value2);

        this.readAndCheck(two, offset1, value1);
        this.readAndCheck(two, offset2, value2);
    }

    @Test
    public void testWrite2() {
        final AddressBus one = AddressBuses.fake();
        final AddressBus three = AddressBuses.fake();

        final AddressBus zero = AddressBuses.memory(256);
        final byte value1 = 12;
        final int offset1 = 1;

        final AddressBus two = AddressBuses.memory(256);
        final byte value2 = 34;
        final int offset2 = 2;

        zero.write(offset1, value1);
        two.write(offset2, value2);

        final PagedAddressBus paged = PagedAddressBus.with(Lists.of(zero, one, two, three));
        this.writeAndReadCheck(paged, 0 + offset1, value1);
        this.writeAndReadCheck(paged, 512 + offset2, value2);

        this.readAndCheck(zero, offset1, value1);
        this.readAndCheck(two, offset2, value2);
    }

    @Test
    public void testWriteHighbitsIgnored() {
        final AddressBus zero = AddressBuses.fake();
        final AddressBus one = AddressBuses.fake();
        final AddressBus three = AddressBuses.fake();

        final AddressBus two = AddressBuses.memory(256);
        final byte value = 34;
        final int offset = 2;
        two.write(offset + 16384, value);

        final PagedAddressBus paged = PagedAddressBus.with(Lists.of(zero, one, two, three));
        this.readAndCheck(paged, 512 + offset, value);
    }

    @Test
    public void testToString() {
        final AddressBus zero = AddressBuses.fake();
        final AddressBus one = AddressBuses.fake();

        this.toStringAndCheck(PagedAddressBus.with(Lists.of(zero, one)), zero + ", " + one);
    }

    @Test
    public PagedAddressBus createAddressBus() {
        final AddressBus zero = AddressBuses.memory(256);
        final AddressBus one = AddressBuses.memory(256);

        return PagedAddressBus.with(Lists.of(zero, one));
    }

    @Override
    public Class<PagedAddressBus> type() {
        return PagedAddressBus.class;
    }
}
