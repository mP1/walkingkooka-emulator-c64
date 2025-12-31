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

public final class CpuInstructionSharedUnaryFunctionLoadTest extends CpuInstructionSharedUnaryFunctionTestCase<CpuInstructionSharedUnaryFunctionLoad> {

    @Test
    public void testHandleZero() {
        this.handleAndCheck(
            (byte) 0,
            "-Z---1-N",
            (byte) 0,
            "-Z---1--"
        );
    }

    @Test
    public void testHandlePositive() {
        this.handleAndCheck(
            (byte) 0x12,
            "-Z---1-N",
            (byte) 0x12,
            "-----1--"
        );
    }

    @Test
    public void testHandleNegative() {
        this.handleAndCheck(
            (byte) 0xF0,
            "-Z---1-N",
            (byte) 0xF0,
            "-----1-N"
        );
    }

    @Override
    CpuInstructionSharedUnaryFunctionLoad createCpuInstructionSharedUnaryFunction() {
        return CpuInstructionSharedUnaryFunctionLoad.INSTANCE;
    }

    // class............................................................................................................

    @Override
    public Class<CpuInstructionSharedUnaryFunctionLoad> type() {
        return CpuInstructionSharedUnaryFunctionLoad.class;
    }
}
