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

public final class CpuInstructionSharedUnaryFunctionAslTest extends CpuInstructionSharedUnaryFunctionTestCase<CpuInstructionSharedUnaryFunctionAsl> {

    @Test
    public void testHandleCarryClear() {
        this.handleAndCheck(
            (byte) 0,
            "-----1--",
            (byte) 0,
            "-Z---1--"
        );
    }

    @Test
    public void testHandleCarryClear2() {
        this.handleAndCheck(
            (byte) 0x80,
            "-----1--",
            (byte) 0,
            "CZ---1--"
        );
    }

    @Test
    public void testHandleCarrySet() {
        this.handleAndCheck(
            (byte) 0,
            "C----1--",
            (byte) 0x00,
            "-Z------"
        );
    }

    @Test
    public void testHandleCarrySet2() {
        this.handleAndCheck(
            (byte) 0x80,
            "C----1--",
            (byte) 0x00,
            "CZ------"
        );
    }

    @Test
    public void testHandle() {
        this.handleAndCheck(
            (byte) 0xF0,
            "C----1--",
            (byte) 0xE0,
            "C------N"
        );
    }

    @Test
    public void testHandle2() {
        this.handleAndCheck(
            (byte) 0x0F,
            "-----1--",
            (byte) 0x1E,
            "--------"
        );
    }

    @Override
    CpuInstructionSharedUnaryFunctionAsl createCpuInstructionSharedUnaryFunction() {
        return CpuInstructionSharedUnaryFunctionAsl.instance();
    }

    // class............................................................................................................

    @Override
    public Class<CpuInstructionSharedUnaryFunctionAsl> type() {
        return CpuInstructionSharedUnaryFunctionAsl.class;
    }
}
