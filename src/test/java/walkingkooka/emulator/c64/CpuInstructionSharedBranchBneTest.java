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

public final class CpuInstructionSharedBranchBneTest extends CpuInstructionSharedBranchTestCase<CpuInstructionSharedBranchBne> {

    @Override
    void setFlagFalse(final CpuContext context) {
        context.setZero(true);
    }

    @Override
    void setFlagTrue(final CpuContext context) {
        context.setZero(false);
    }

    @Test
    public void testDisassemble() {
        final CpuContext context = CpuContexts.basic(
            new FakeAddressBus() {
                @Override
                public byte read(final int o) {
                    checkEquals(0x1001, o);
                    return 0x12;
                }
            }
        );
        context.setPc(
            (short) 0x1000
        );

        this.disassembleAndCheck(
            this.createCpuInstruction(),
            context,
            "BNE $12 ($1014)"
        );
    }

    @Override
    public CpuInstructionSharedBranchBne createCpuInstruction() {
        return CpuInstructionSharedBranchBne.INSTANCE;
    }

    // class............................................................................................................

    @Override
    public Class<CpuInstructionSharedBranchBne> type() {
        return CpuInstructionSharedBranchBne.class;
    }
}
