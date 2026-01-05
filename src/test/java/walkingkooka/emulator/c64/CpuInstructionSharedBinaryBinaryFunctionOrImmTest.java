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

public final class CpuInstructionSharedBinaryBinaryFunctionOrImmTest extends CpuInstructionSharedBinaryBinaryFunctionOrTestCase<CpuInstructionSharedBinaryBinaryFunctionOrImm> {

    @Test
    public void testExecuteZero() {
        this.executeImmediateAndCheck(
            (byte) 0x0,
            (byte) 0x0,
            "-----1--",
            (byte) 0,
            "-Z---1--"
        );
    }

    @Test
    public void testExecutePositive() {
        this.executeImmediateAndCheck(
            (byte) 0x0F,
            (byte) 0x03,
            "-----1--",
            (byte) 0x0F,
            "-----1--"
        );
    }

    @Test
    public void testExecuteNegative() {
        this.executeImmediateAndCheck(
            (byte) 0xF0,
            (byte) 0x8F,
            "-----1--",
            (byte) 0xFF,
            "-----1-N"
        );
    }

    @Override
    public CpuInstructionSharedBinaryBinaryFunctionOrImm createCpuInstruction() {
        return CpuInstructionSharedBinaryBinaryFunctionOrImm.instance();
    }

    // class............................................................................................................

    @Override
    public Class<CpuInstructionSharedBinaryBinaryFunctionOrImm> type() {
        return CpuInstructionSharedBinaryBinaryFunctionOrImm.class;
    }
}
