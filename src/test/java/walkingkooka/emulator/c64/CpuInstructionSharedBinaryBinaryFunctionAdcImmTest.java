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

public final class CpuInstructionSharedBinaryBinaryFunctionAdcImmTest extends CpuInstructionSharedBinaryBinaryFunctionAdcTestCase<CpuInstructionSharedBinaryBinaryFunctionAdcImm> {

    @Test
    public void testExecute() {
        this.executeImmediateAndCheck(
            (byte) 0x34, // a
            (byte) 0x12, // immediate
            "C----1--",
            (byte) 0x47, // expected A
            "-----1--" // expectedFlags
        );
    }

    @Test
    public void testExecuteWithDecimalMode() {
        this.executeImmediateAndCheck(
            (byte) 0x35, // a
            (byte) 0x15, // immediate
            "C--D-1--",
            (byte) 0x51, // expected A
            "---D-1--" // expectedFlags
        );
    }

    @Override
    public CpuInstructionSharedBinaryBinaryFunctionAdcImm createCpuInstruction() {
        return CpuInstructionSharedBinaryBinaryFunctionAdcImm.instance();
    }

    // class............................................................................................................

    @Override
    public Class<CpuInstructionSharedBinaryBinaryFunctionAdcImm> type() {
        return CpuInstructionSharedBinaryBinaryFunctionAdcImm.class;
    }
}
