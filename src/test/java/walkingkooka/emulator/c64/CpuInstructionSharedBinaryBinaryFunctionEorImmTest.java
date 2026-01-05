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

public final class CpuInstructionSharedBinaryBinaryFunctionEorImmTest extends CpuInstructionSharedBinaryBinaryFunctionEorTestCase<CpuInstructionSharedBinaryBinaryFunctionEorImm> {

    @Test
    public void testExecute() {
        this.executeImmediateAndCheck(
            (byte) 0x0F, // a
            (byte) 0xA0, // immediate
            "C--D-1--",
            (byte) 0xAF, // expected A
            "C--D-1-N" // expectedFlags
        );
    }

    @Override
    public CpuInstructionSharedBinaryBinaryFunctionEorImm createCpuInstruction() {
        return CpuInstructionSharedBinaryBinaryFunctionEorImm.instance();
    }

    // class............................................................................................................

    @Override
    public Class<CpuInstructionSharedBinaryBinaryFunctionEorImm> type() {
        return CpuInstructionSharedBinaryBinaryFunctionEorImm.class;
    }
}
