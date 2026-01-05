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

public final class CpuInstructionSharedBinaryFunctionSbcTest extends CpuInstructionSharedBinaryFunctionTestCase<CpuInstructionSharedBinaryFunctionSbc> {

    @Test
    public void testHandleBinaryZero() {
        this.handleAndCheck(
            (byte) 0x0,
            (byte) 0x0,
            "C----1--",
            (byte) 0,
            "CZ---1--"
        );
    }

    @Test
    public void testHandleBinaryZero2() {
        this.handleAndCheck(
            (byte) 0x5,
            (byte) 0x5,
            "C----1--",
            (byte) 0,
            "CZ---1--"
        );
    }

    @Test
    public void testHandleBinaryZeroWithCarryClear() {
        this.handleAndCheck(
            (byte) 0x0,
            (byte) 0x0,
            "-----1--",
            (byte) 0xff,
            "-----1-N"
        );
    }

    @Test
    public void testHandleBinaryPositive() {
        this.handleAndCheck(
            (byte) 0x04,
            (byte) 0x03,
            "C----1--",
            (byte) 0x01,
            "C----1--"
        );
    }

    @Test
    public void testHandleBinaryPositiveWithoutCarry() {
        this.handleAndCheck(
            (byte) 0x04,
            (byte) 0x03,
            "-----1V-",
            (byte) 0x00,
            "CZ---1--"
        );
    }

    @Test
    public void testHandleBinaryNegative() {
        this.handleAndCheck(
            (byte) 0xF0,
            (byte) 0x0F,
            "C----1V-",
            (byte) 0xE1,
            "C----1-N"
        );
    }

    @Test
    public void testHandleBinaryNegative2() {
        this.handleAndCheck(
            (byte) 0x0F,
            (byte) 0xF0,
            "C----1V-",
            (byte) 0x1F,
            "-----1--"
        );
    }

    @Test
    public void testHandleBinaryOverflow() {
        this.handleAndCheck(
            (byte) 0x80,
            (byte) 0x01,
            "C----1--",
            (byte) 0x7F,
            "C----1V-"
        );
    }

    @Test
    public void testHandleBinaryUnderflow() {
        this.handleAndCheck(
            (byte) 0x71,
            (byte) 0x09,
            "C----1--",
            (byte) 0x68,
            "C----1--"
        );
    }

    // BCD..............................................................................................................

    @Test
    public void testHandleDecimalZero() {
        this.handleAndCheck(
            (byte) 0x0,
            (byte) 0x0,
            "C--D-1VN",
            (byte) 0,
            "CZ-D-1--"
        );
    }

    @Test
    public void testHandleDecimalZeroWithCarryClear() {
        this.handleAndCheck(
            (byte) 0x0,
            (byte) 0x0,
            "---D-1VN",
            (byte) 0x99,
            "---D-1-N"
        );
    }

    @Test
    public void testHandleDecimal() {
        this.handleAndCheck(
            (byte) 0x05,
            (byte) 0x03,
            "C--D-1--",
            (byte) 0x02,
            "C--D-1--"
        );
    }

    @Test
    public void testHandleDecimal2() {
        this.handleAndCheck(
            (byte) 0x34,
            (byte) 0x12,
            "C--D-1--",
            (byte) 0x22,
            "C--D-1--"
        );
    }

    @Test
    public void testHandleDecimalWithCarryClear() {
        this.handleAndCheck(
            (byte) 0x34,
            (byte) 0x12,
            "---D-1--",
            (byte) 0x21,
            "C--D-1--"
        );
    }

    @Test
    public void testHandleDecimalUnitsUnderflow() {
        this.handleAndCheck(
            (byte) 0x80,
            (byte) 0x01,
            "C--D-1VN",
            (byte) 0x79,
            "C--D-1--"
        );
    }

    @Test
    public void testHandleDecimalTensUnderflow() {
        this.handleAndCheck(
            (byte) 0x50,
            (byte) 0x60,
            "C--D-1VN",
            (byte) 0x90,
            "---D-1-N"
        );
    }

    @Override
    CpuInstructionSharedBinaryFunctionSbc createCpuInstructionSharedBinaryFunction() {
        return CpuInstructionSharedBinaryFunctionSbc.INSTANCE;
    }

    @Override
    public Class<CpuInstructionSharedBinaryFunctionSbc> type() {
        return CpuInstructionSharedBinaryFunctionSbc.class;
    }
}
