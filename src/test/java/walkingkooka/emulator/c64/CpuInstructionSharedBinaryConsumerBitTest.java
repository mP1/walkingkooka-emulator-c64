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

public final class CpuInstructionSharedBinaryConsumerBitTest extends CpuInstructionSharedBinaryConsumerTestCase<CpuInstructionSharedBinaryConsumerBit> {

    @Test
    public void testHandleBinaryZero() {
        this.handleAndCheck(
            (byte) 0x0,
            (byte) 0x0,
            "CZ---1VN",
            "CZ---1--"
        );
    }

    @Test
    public void testHandleBinaryZero2() {
        this.handleAndCheck(
            (byte) 0x0,
            (byte) 0x1,
            "CZ---1VN",
            "CZ---1--"
        );
    }

    @Test
    public void testHandleBinaryNonZero() {
        this.handleAndCheck(
            (byte) 0xF0,
            (byte) 0x55,
            "CZ---1VN",
            "C----1V-"
        );
    }

    @Test
    public void testHandleBinaryBit67() {
        this.handleAndCheck(
            (byte) 0x0,
            (byte) 0xC0,
            "CZ---1VN",
            "CZ---1VN"
        );
    }

    @Test
    public void testHandleBinaryBit672() {
        this.handleAndCheck(
            (byte) 0xFF,
            (byte) 0xC0,
            "CZ---1VN",
            "C----1VN"
        );
    }

    @Override
    CpuInstructionSharedBinaryConsumerBit createCpuInstructionSharedBinaryConsumer() {
        return CpuInstructionSharedBinaryConsumerBit.instance();
    }

    @Override
    public Class<CpuInstructionSharedBinaryConsumerBit> type() {
        return CpuInstructionSharedBinaryConsumerBit.class;
    }
}
