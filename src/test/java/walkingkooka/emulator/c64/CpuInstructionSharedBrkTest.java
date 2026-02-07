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

public final class CpuInstructionSharedBrkTest extends CpuInstructionSharedTestCase<CpuInstructionSharedBrk> {

    @Test
    public void testStep() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256),
            SYMBOL_LOOKUP_UOE
        );

        final byte hi = 0x12;
        final byte lo = 0x34;

        context.writeByte(
            CpuContext.IRQ_VECTOR,
            lo
        );
        context.writeByte(
            (short) (CpuContext.IRQ_VECTOR + 1),
            hi
        );
        context.setPc((short) 0x1000);

        context.setStackPointer(
            (byte) 0xf3
        );

        this.executeAndCheck(
            CpuInstructionSharedBrk.instance(),
            context,
            context.a(),
            context.x(),
            context.y(),
            CpuFlags.parse("--I--1--"),
            (byte) 0xf0, // stackPointer
            (short) 0x1234
        );
    }

    @Test
    public void testDisassemble() {
        this.disassembleAndCheck(
            CpuInstructionSharedBrk.instance(),
            CpuContexts.fake(),
            "BRK"
        );
    }

    @Override
    public CpuInstructionSharedBrk createCpuInstruction() {
        return CpuInstructionSharedBrk.instance();
    }

    // class............................................................................................................

    @Override
    public Class<CpuInstructionSharedBrk> type() {
        return CpuInstructionSharedBrk.class;
    }
}
