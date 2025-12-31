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

public final class CpuInstructionSharedUnaryFunctionDecTest extends CpuInstructionSharedUnaryFunctionTestCase<CpuInstructionSharedUnaryFunctionDec> {

    @Test
    public void testHandleCarryClear() {
        this.handleAndCheck(
            (byte) 2,
            "-----1--",
            (byte) 1,
            "-----1--"
        );
    }

    @Test
    public void testHandleCarrySet() {
        this.handleAndCheck(
            (byte) 4,
            "C----1--",
            (byte) 0x03,
            "C----1--"
        );
    }

    @Test
    public void testHandleDecimalModeSet() {
        this.handleAndCheck(
            (byte) 2,
            "---D-1--",
            (byte) 0x01,
            "---D-1--"
        );
    }

    @Test
    public void testHandleZero() {
        this.handleAndCheck(
            (byte) 1,
            "C----1--",
            (byte) 0x00,
            "CZ---1--"
        );
    }

    @Test
    public void testHandlePositive() {
        this.handleAndCheck(
            (byte) 0x06,
            "-----1--",
            (byte) 0x05,
            "-----1--"
        );
    }

    @Test
    public void testHandleNegative() {
        this.handleAndCheck(
            (byte) 0xFF,
            "-----1--",
            (byte) 0xFE,
            "-----1-N"
        );
    }

    @Override
    CpuInstructionSharedUnaryFunctionDec createCpuInstructionSharedUnaryFunction() {
        return CpuInstructionSharedUnaryFunctionDec.INSTANCE;
    }

    // class............................................................................................................

    @Override
    public Class<CpuInstructionSharedUnaryFunctionDec> type() {
        return CpuInstructionSharedUnaryFunctionDec.class;
    }
}
