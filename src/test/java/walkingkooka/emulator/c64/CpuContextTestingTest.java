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

public final class CpuContextTestingTest implements CpuContextTesting {

    @Test
    public void testFlagsAndCheckWithByte() {
        final byte value = (byte) 0xFF;
        this.flagsAndCheck(
            new FakeCpuContext() {

                @Override
                public byte flags() {
                    return value;
                }
            },
            value
        );
    }

    @Test
    public void testFlagsAndCheckWithString() {
        final byte value = (byte) 0xFF;

        final CpuFlags expected = CpuFlags.create();
        expected.setValue(value);

        this.flagsAndCheck(
            new FakeCpuContext() {

                @Override
                public byte flags() {
                    return value;
                }
            },
            expected
        );
    }

    @Test
    public void testPopAndCheck() {
        final byte value = 0x55;

        this.popAndCheck(
            new FakeCpuContext() {

                @Override
                public byte pop() {
                    try {
                        return value;
                    } finally {
                        this.stackPointer = 0x11;
                    }
                }

                @Override
                public byte readByte(final short address) {
                    return 0x111 == address ?
                        value :
                        (byte) 0xff;
                }

                @Override
                public void setStackPointer(final byte stackPointer) {
                    this.stackPointer = stackPointer;
                }

                @Override
                public byte stackPointer() {
                    return this.stackPointer;
                }

                private byte stackPointer = (byte) 0x10;
            },
            value
        );
    }

    @Test
    public void testPushAndCheck() {
        final byte value = 0x55;

        this.pushAndCheck(
            new FakeCpuContext() {

                @Override
                public void push(final byte v) {
                    checkEquals(value, v);
                    this.stackPointer = 0x0f;
                }

                @Override
                public byte readByte(final short address) {
                    return 0x110 == address ?
                        value :
                        (byte) 0xff;
                }

                @Override
                public void setStackPointer(final byte stackPointer) {
                    this.stackPointer = stackPointer;
                }

                @Override
                public byte stackPointer() {
                    return this.stackPointer;
                }

                private byte stackPointer = (byte) 0x10;
            },
            value
        );
    }
}
