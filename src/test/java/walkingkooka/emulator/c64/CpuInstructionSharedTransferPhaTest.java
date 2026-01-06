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

public final class CpuInstructionSharedTransferPhaTest extends CpuInstructionSharedTransferTestCase<CpuInstructionSharedTransferPha> {

    @Test
    public void testExecute() {
        final short pc = 0x1000;

        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(
                256 * 256
            )
        );
        context.setPc(pc);

        final byte a = 0x55;
        context.setA(a);

        context.setStackPointer((byte) 0x40);

        context.setFlags((byte) 0);

        this.executeAndCheck(
            this.createCpuInstruction(),
            context,
            a,
            context.x(),
            context.y(),
            "-----1--",
            (byte) 0x3f, // stackPointer
            pc
        );

        this.readByteAndCheck(
            context,
            (short) 0x140,
            a
        );
    }

    @Test
    public void testDisassemble() {
        this.disassembleAndCheck(
            CpuInstructionSharedTransferPha.instance(),
            CpuContexts.fake(),
            "PHA"
        );
    }

    @Override
    public CpuInstructionSharedTransferPha createCpuInstruction() {
        return CpuInstructionSharedTransferPha.instance();
    }

    @Override
    public Class<CpuInstructionSharedTransferPha> type() {
        return CpuInstructionSharedTransferPha.class;
    }
}
