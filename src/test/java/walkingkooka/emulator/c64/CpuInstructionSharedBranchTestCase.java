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

public abstract class CpuInstructionSharedBranchTestCase<I extends CpuInstructionSharedBranch> extends CpuInstructionSharedTestCase<I> {

    CpuInstructionSharedBranchTestCase() {
        super();
    }

    @Test
    public final void testOpcodeNotZero() {
        this.checkNotEquals(
            0,
            this.createCpuInstruction()
                .opcode()
        );
    }

    @Test
    public final void testBranchTaken() {
        final short pc = 0x1000;
        final byte offset = 0x12;

        final CpuContext context = CpuContexts.basic(
            new FakeAddressBus() {
                @Override
                public byte read(final int o) {
                    checkEquals(pc, (short) o);
                    return offset;
                }
            }
        );
        context.setPc(pc);

        this.setFlagTrue(context);

        this.executeBranchOrJumpAndCheck(
            context,
            (short) (pc + 1 + offset)
        );
    }

    abstract void setFlagFalse(final CpuContext context);

    abstract void setFlagTrue(final CpuContext context);

    @Test
    public final void testBranchNotTaken() {
        final short pc = 0x1000;

        final CpuContext context = CpuContexts.basic(
            AddressBuses.fake()
        );
        context.setPc(pc);

        this.setFlagFalse(context);

        this.executeBranchOrJumpAndCheck(
            context,
            (short) (pc + 1)
        );
    }
}
