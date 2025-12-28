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

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class RomTest extends AddressBusTestCase<Rom> {

    @Test
    public void testWithNullValuesFails() {
        assertThrows(NullPointerException.class, () -> {
            Rom.with(null, AddressBuses.fake());
        });
    }

    @Test
    public void testWithNullAddressBusFails() {
        assertThrows(NullPointerException.class, () -> {
            Rom.with(this.values(), null);
        });
    }

    @Test
    public void testRead() {
        final Rom rom = Rom.with(this.values(), AddressBuses.fake());
        this.readAndCheck(rom, 1, (byte) (128 ^ 1));
        this.readAndCheck(rom, 10, (byte) (128 ^ 10));
    }

    @Test
    public void testReadMultiplePageSize() {
        final byte[] values = new byte[8192];

        final int offset = 7890;
        final byte value = (byte) 0xfe;
        values[offset] = value;

        final int offset2 = 8191;
        final byte value2 = (byte) 0x7f;
        values[offset2] = value2;

        final Rom rom = Rom.with(values, AddressBuses.fake());
        this.readAndCheck(rom, offset, value);
    }

    @Test
    public void testReadMasksHighBitsIgnored() {
        final byte[] values = new byte[8192];

        final int offset = 7890;
        final byte value = (byte) 0xfe;
        values[offset] = value;

        final Rom rom = Rom.with(values, AddressBuses.fake());
        this.readAndCheck(rom, offset, value);
        this.readAndCheck(rom, 16384 | offset, value);
    }

    @Test
    public void testWriteAndReadBack() {
        final AddressBus memory = AddressBuses.memory(256);

        final byte offset = 12;
        final byte value = 34;

        this.writeAndReadCheck(memory, offset, value);

        final Rom rom = Rom.with(this.values(), memory);

        final byte romWrite = (byte) 0xff;
        rom.write(offset, romWrite);

        this.readAndCheck(memory, offset, romWrite);
    }

    @Test
    public void testBaseOffsetReadAndWrite() {
        final Rom rom = this.createAddressBus();

        final int baseOffset = 128;
        final Rom base = rom.setBaseOffset(-baseOffset);
        for (int i = 0; i < 128; i++) {
            this.readAndCheck(base, i, rom.read(baseOffset + i));
        }
    }

    @Test
    public void testToString() {
        this.toStringAndCheck(Rom.with(this.values(), AddressBuses.fake()),
            "80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 8a, 8b, 8c, 8d, 8e, 8f, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 9a, 9b, 9c, 9d, 9e, 9f, a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, aa, ab, ac, ad, ae, af, b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, ba, bb, bc, bd, be, bf, c0, c1, c2, c3, c4, c5, c6, c7, c8, c9, ca, cb, cc, cd, ce, cf, d0, d1, d2, d3, d4, d5, d6, d7, d8, d9, da, db, dc, dd, de, df, e0, e1, e2, e3, e4, e5, e6, e7, e8, e9, ea, eb, ec, ed, ee, ef, f0, f1, f2, f3, f4, f5, f6, f7, f8, f9, fa, fb, fc, fd, fe, ff, 00, 01, 02, 03, 04, 05, 06, 07, 08, 09, 0a, 0b, 0c, 0d, 0e, 0f, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 1a, 1b, 1c, 1d, 1e, 1f, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 2a, 2b, 2c, 2d, 2e, 2f, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 3a, 3b, 3c, 3d, 3e, 3f, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 4a, 4b, 4c, 4d, 4e, 4f, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 5a, 5b, 5c, 5d, 5e, 5f, 60, ");
    }

    private byte[] values() {
        final byte[] values = new byte[256];
        for (int i = 0; i < values.length; i++) {
            values[i] = (byte) (128 ^ i);
        }
        return values;
    }

    @Test
    public Rom createAddressBus() {
        return Rom.with(this.values(), AddressBuses.memory(256));
    }

    @Override
    public Class<Rom> type() {
        return Rom.class;
    }
}
