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

public final class CpuInstructionSharedBinaryConsumerCompareTest extends CpuInstructionSharedBinaryConsumerTestCase<CpuInstructionSharedBinaryConsumerCompare> {

    @Test
    public void testHandleBinaryZero() {
        this.handleAndCheck(
            (byte) 0x0,
            (byte) 0x0,
            "-----1VN",
            "CZ---1V-"
        );
    }

    @Test
    public void testHandleBinaryPositive() {
        this.handleAndCheck(
            (byte) 0x34,
            (byte) 0x12,
            "-----1VN",
            "C----1V-"
        );
    }

    @Test
    public void testHandleBinaryNegative() {
        this.handleAndCheck(
            (byte) 0x12,
            (byte) 0x34,
            "-----1VN",
            "-----1VN"
        );
    }

    @Override
    CpuInstructionSharedBinaryConsumerCompare createCpuInstructionSharedBinaryConsumer() {
        return CpuInstructionSharedBinaryConsumerCompare.INSTANCE;
    }

    @Override
    public Class<CpuInstructionSharedBinaryConsumerCompare> type() {
        return CpuInstructionSharedBinaryConsumerCompare.class;
    }
}
