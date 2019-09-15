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

public final class MemoryPageTest extends AddressBusTestCase<MemoryPage> {

    @Test
    public void testWrite() {
        final MemoryPage memory = MemoryPage.create();
        this.writeAndReadCheck(memory, 1, (byte) 1);
    }

    @Test
    public void testWrite2() {
        final MemoryPage memory = MemoryPage.create();

        for (int i = 0; i < 256; i++) {
            this.writeAndReadCheck(memory, i, (byte) i);
        }
    }

    @Test
    public void testReadMasksHighBits() {
        final MemoryPage memory = MemoryPage.create();
        memory.write(0x10, (byte) 0xff);
        this.readAndCheck(memory, 0x4010, (byte) 0xff);
    }

    @Test
    public void testWriteMasksHighBits() {
        final MemoryPage memory = MemoryPage.create();
        memory.write(0x4010, (byte) 0xff);
        this.readAndCheck(memory, 0x10, (byte) 0xff);
    }

    @Test
    public void testToString() {
        final MemoryPage memory = MemoryPage.create();
        memory.write(1, (byte) 1);
        memory.write(2, (byte) 2);
        this.toStringAndCheck(memory, "00, 01, 02, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, ");
    }

    @Override
    public Class<MemoryPage> type() {
        return MemoryPage.class;
    }
}
