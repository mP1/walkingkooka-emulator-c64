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

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class BaseOffsetAddressBusTest extends AddressBusTestCase<BaseOffsetAddressBus> {

    private final static int BASE_OFFSET = 123;
    private final static byte VALUE = 0x55;

    @Test
    public void testWithNullFails() {
        assertThrows(NullPointerException.class, () -> {
            BaseOffsetAddressBus.with(BASE_OFFSET, null);
        });
    }

    @Test
    public void testWithZeroBaseOffset() {
        final AddressBus wrap = AddressBuses.fake();
        assertSame(wrap, BaseOffsetAddressBus.with(0, wrap));
    }

    @Test
    public void testWriteThenRead() {
        final AddressBus memory = AddressBuses.memory(256);
        final BaseOffsetAddressBus base = (BaseOffsetAddressBus) BaseOffsetAddressBus.with(BASE_OFFSET, memory);

        final int offset = 1;
        this.writeAndReadCheck(base, offset, VALUE);
        this.readAndCheck(memory, offset + BASE_OFFSET, VALUE);
    }

    @Test
    public void testWriteAndRead() {
        final AddressBus memory = AddressBuses.memory(256);
        final BaseOffsetAddressBus base = (BaseOffsetAddressBus) BaseOffsetAddressBus.with(BASE_OFFSET, memory);

        for (int i = 0; i < 256; i++) {
            this.writeAndReadCheck(base, BASE_OFFSET + i, (byte) i);
        }

        for (int i = 0; i < 256; i++) {
            this.writeAndReadCheck(memory, i, (byte) i);
        }
    }

    @Test
    public void testPagedMemoryRead() {
        final AddressBus[] all = new AddressBus[256];
        for (int hi = 0; hi < 256; hi++) {
            final AddressBus memory = AddressBuses.memory(256);
            all[hi] = memory;

            for (int lo = 0; lo < 256; lo++) {
                memory.write(lo, (byte) hi);
            }
        }
        final AddressBus paged = AddressBuses.paged(Lists.of(all));
        for (int hi = 0; hi < 256; hi++) {
            for (int lo = 0; lo < 256; lo++) {
                this.readAndCheck(paged, hi * 256 + lo, (byte) hi);
            }
        }
    }

    @Test
    public void testPagedMemoryWrite() {
        final AddressBus[] all = new AddressBus[256];
        for (int hi = 0; hi < 256; hi++) {
            final AddressBus memory = AddressBuses.memory(256);
            all[hi] = memory;

            for (int lo = 0; lo < 256; lo++) {
                memory.write(lo, (byte) hi);
            }
        }
        final AddressBus paged = AddressBuses.paged(Lists.of(all));
        for (int hi = 0; hi < 256; hi++) {
            for (int lo = 0; lo < 256; lo++) {
                paged.write(hi * 256 + lo, (byte) hi);
            }
        }

        for (int hi = 0; hi < 256; hi++) {
            final AddressBus memory = all[hi];

            for (int lo = 0; lo < 256; lo++) {
                this.readAndCheck(memory, lo, (byte) hi);
            }
        }
    }

    @Test
    public void testToString() {
        final AddressBus memory = AddressBuses.memory(256);
        final BaseOffsetAddressBus base = (BaseOffsetAddressBus) BaseOffsetAddressBus.with(2, memory);
        base.write(1, (byte) 1);
        base.write(2, (byte) 2);
        this.toStringAndCheck(base, "baseOffset: 2 Memory 0xff");
    }

    @Test
    public BaseOffsetAddressBus createAddressBus() {
        final AddressBus memory = AddressBuses.memory(256);
        return (BaseOffsetAddressBus) BaseOffsetAddressBus.with(2, memory);
    }

    @Override
    public Class<BaseOffsetAddressBus> type() {
        return BaseOffsetAddressBus.class;
    }
}
