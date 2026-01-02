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

public final class CpuInstructionSharedJmpIndirectTest extends CpuInstructionSharedTestCase<CpuInstructionSharedJmpIndirect> {

    @Test
    public void testStep() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256)
        );

        final short pc = (byte) 0xF000;

        final short indirect = 0x1234;
        context.writeAddress(
            pc,
            indirect
        );

        final short indirectTarget = 0x5678;
        context.writeAddress(
            indirect,
            indirectTarget
        );

        context.setPc(pc);

        this.executeAndCheck(
            CpuInstructionSharedJmpIndirect.INSTANCE,
            context,
            context.a(),
            context.x(),
            context.y(),
            CpuFlags.parse("-----1--"),
            context.stackPointer(), // stackPointer
            indirectTarget
        );
    }

    @Test
    public void testDisassemble() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256)
        );

        final short pc = (byte) 0xF000;

        final short indirect = 0x1234;
        context.writeAddress(
            pc,
            indirect
        );

        final short indirectTarget = 0x5678;
        context.writeAddress(
            indirect,
            indirectTarget
        );

        context.setPc(pc);

        this.disassembleAndCheck(
            CpuInstructionSharedJmpIndirect.INSTANCE,
            context,
            "JMP ($1234) $5678"
        );
    }

    @Override
    public CpuInstructionSharedJmpIndirect createCpuInstruction() {
        return CpuInstructionSharedJmpIndirect.INSTANCE;
    }

    // class............................................................................................................

    @Override
    public Class<CpuInstructionSharedJmpIndirect> type() {
        return CpuInstructionSharedJmpIndirect.class;
    }
}
