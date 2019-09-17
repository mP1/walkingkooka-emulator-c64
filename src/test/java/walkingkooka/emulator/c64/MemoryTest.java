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

public final class MemoryTest extends AddressBusTestCase<Memory> {

    @Test
    public void testWrite() {
        final Memory memory = Memory.with(256);
        this.writeAndReadCheck(memory, 1, (byte) 1);
    }

    @Test
    public void testWrite2() {
        final Memory memory = Memory.with(256);

        for (int i = 0; i < 256; i++) {
            this.writeAndReadCheck(memory, i, (byte) i);
        }
    }

    @Test
    public void testReadMasksHighBits() {
        final Memory memory = Memory.with(256);
        memory.write(0x10, (byte) 0xff);
        this.readAndCheck(memory, 0x4010, (byte) 0xff);
    }

    @Test
    public void testWriteMasksHighBits() {
        final Memory memory = Memory.with(256);
        memory.write(0x4010, (byte) 0xff);
        this.readAndCheck(memory, 0x10, (byte) 0xff);
    }

    @Test
    public void testBaseOffsetReadAndWrite() {
        final Memory memory = Memory.with(256);

        final int baseOffset = 128;
        for (int i = 0; i < 128; i++) {
            memory.write(baseOffset + i, (byte) i);
        }

        final Memory base = memory.setBaseOffset(-baseOffset);
        for (int i = 0; i < 128; i++) {
            this.readAndCheck(base, i, (byte) i);
            base.write(i, (byte) (~i));
        }

        for (int i = 0; i < 128; i++) {
            this.readAndCheck(memory, baseOffset + i, (byte) ~i);
        }
    }

    @Test
    public void testToString() {
        final Memory memory = Memory.with(256);
        memory.write(1, (byte) 1);
        memory.write(2, (byte) 2);
        this.toStringAndCheck(memory, "Memory 0xff");
    }

    @Test
    public Memory createAddressBus() {
        return Memory.with(256);
    }

    @Override
    public Class<Memory> type() {
        return Memory.class;
    }
}
