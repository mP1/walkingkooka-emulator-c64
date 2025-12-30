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

public final class CpuInstructionSharedJmpAbsTest extends CpuInstructionSharedTestCase<CpuInstructionSharedJmpAbs> {

    @Test
    public void testStep() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256)
        );

        final short pc = 0x5000;

        final byte hi = 0x12;
        final byte lo = 0x34;

        context.writeByte(
            pc,
            lo
        );
        context.writeByte(
            (short) (pc + 1),
            hi
        );

        context.setPc(pc);

        this.executeAndCheck(
            CpuInstructionSharedJmpAbs.INSTANCE,
            context,
            context.a(),
            context.x(),
            context.y(),
            CpuFlags.parse("-----1--"),
            context.stackPointer(), // stackPointer
            (short) 0x1234
        );
    }

    @Test
    public void testDisassemble() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256)
        );

        final short pc = 0x5000;

        final byte hi = 0x12;
        final byte lo = 0x34;

        context.writeByte(
            pc,
            lo
        );
        context.writeByte(
            (short) (pc + 1),
            hi
        );

        context.setPc(pc);

        this.disassembleAndCheck(
            CpuInstructionSharedJmpAbs.INSTANCE,
            context,
            "JMP $1234"
        );
    }

    @Override
    public CpuInstructionSharedJmpAbs createCpuInstruction() {
        return CpuInstructionSharedJmpAbs.INSTANCE;
    }

    // class............................................................................................................

    @Override
    public Class<CpuInstructionSharedJmpAbs> type() {
        return CpuInstructionSharedJmpAbs.class;
    }
}
