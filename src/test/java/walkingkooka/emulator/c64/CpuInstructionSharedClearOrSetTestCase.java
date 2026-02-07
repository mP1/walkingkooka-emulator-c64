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

public abstract class CpuInstructionSharedClearOrSetTestCase<I extends CpuInstructionSharedClearOrSet> extends CpuInstructionSharedTestCase<I> {

    CpuInstructionSharedClearOrSetTestCase() {
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
    public final void testExecuteFlagsSet() {
        final I instruction = this.createCpuInstruction();
        final CpuContext context = CpuContexts.basic(
            AddressBuses.fake(),
            SYMBOL_LOOKUP_UOE
        );

        final short pc = 0x2000;
        context.setPc(pc);

        context.setFlags((byte) 0xff);

        this.executeAndCheck(
            instruction,
            context,
            context.a(),
            context.x(),
            context.y(),
            this.setOrClearFlag(context),
            context.stackPointer(),
            pc
        );
    }

    abstract CpuFlags setOrClearFlag(final CpuContext context);
}
