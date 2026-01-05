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

public final class CpuInstructionSharedBinaryFunctionAdcTest extends CpuInstructionSharedBinaryFunctionTestCase<CpuInstructionSharedBinaryFunctionAdc> {

    @Test
    public void testHandleBinaryZero() {
        this.handleAndCheck(
            (byte) 0x0,
            (byte) 0x0,
            "-----1--",
            (byte) 0,
            "-Z---1--"
        );
    }

    @Test
    public void testHandleBinaryPositive() {
        this.handleAndCheck(
            (byte) 0x04,
            (byte) 0x03,
            "-----1--",
            (byte) 0x07,
            "-----1--"
        );
    }

    @Test
    public void testHandleBinaryPositiveWithCarry() {
        this.handleAndCheck(
            (byte) 0x04,
            (byte) 0x03,
            "C----1V-",
            (byte) 0x08,
            "-----1--"
        );
    }

    @Test
    public void testHandleBinaryNegative() {
        this.handleAndCheck(
            (byte) 0xF0,
            (byte) 0x0F,
            "-----1V-",
            (byte) 0xFF,
            "-----1-N"
        );
    }

    @Test
    public void testHandleBinaryNegative2() {
        this.handleAndCheck(
            (byte) 0x0F,
            (byte) 0xF0,
            "-----1V-",
            (byte) 0xFF,
            "-----1-N"
        );
    }

    @Test
    public void testHandleBinarySetsCarry() {
        this.handleAndCheck(
            (byte) 0x81,
            (byte) 0x82,
            "-----1--",
            (byte) 0x03,
            "C----1V-"
        );
    }

    @Test
    public void testHandleBinaryOverflow() {
        this.handleAndCheck(
            (byte) 0x41,
            (byte) 0x42,
            "-----1--",
            (byte) 0x83,
            "-----1VN"
        );
    }

    // BCD..............................................................................................................

    @Test
    public void testHandleDecimalZero() {
        this.handleAndCheck(
            (byte) 0x0,
            (byte) 0x0,
            "---D-1VN",
            (byte) 0,
            "-Z-D-1--"
        );
    }

    @Test
    public void testHandleDecimalZeroWithCarry() {
        this.handleAndCheck(
            (byte) 0x0,
            (byte) 0x0,
            "C--D-1VN",
            (byte) 1,
            "---D-1--"
        );
    }

    @Test
    public void testHandleDecimal() {
        this.handleAndCheck(
            (byte) 0x05,
            (byte) 0x03,
            "---D-1--",
            (byte) 0x08,
            "---D-1--"
        );
    }

    @Test
    public void testHandleDecimal2() {
        this.handleAndCheck(
            (byte) 0x25,
            (byte) 0x33,
            "---D-1--",
            (byte) 0x58,
            "---D-1--"
        );
    }

    @Test
    public void testHandleDecimalWithCarry() {
        this.handleAndCheck(
            (byte) 0x25,
            (byte) 0x33,
            "C--D-1--",
            (byte) 0x59,
            "---D-1--"
        );
    }

    @Test
    public void testHandleDecimalUnitsOverflow() {
        this.handleAndCheck(
            (byte) 0x05,
            (byte) 0x06,
            "---D-1VN",
            (byte) 0x11,
            "---D-1--"
        );
    }

    @Test
    public void testHandleDecimalTensOverflow() {
        this.handleAndCheck(
            (byte) 0x50,
            (byte) 0x60,
            "---D-1VN",
            (byte) 0x10,
            "C--D-1--"
        );
    }

    @Override
    CpuInstructionSharedBinaryFunctionAdc createCpuInstructionSharedBinaryFunction() {
        return CpuInstructionSharedBinaryFunctionAdc.INSTANCE;
    }

    @Override
    public Class<CpuInstructionSharedBinaryFunctionAdc> type() {
        return CpuInstructionSharedBinaryFunctionAdc.class;
    }
}
