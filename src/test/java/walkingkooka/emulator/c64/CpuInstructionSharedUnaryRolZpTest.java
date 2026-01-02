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

public final class CpuInstructionSharedUnaryRolZpTest extends CpuInstructionSharedUnaryRolTestCase<CpuInstructionSharedUnaryRolZp> {

    @Test
    public void testExecute() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256)
        );

        context.setPc((short) 0x1000);
        context.setFlags(
            (byte) (0xff)
        );

        final byte offset = (byte) 0x81;

        context.writeByte(
            (short) 0x1000,
            offset
        );

        context.writeZeroPageByte(
            offset,
            (byte) 0xC1
        );

        this.executeAndCheck(
            this.createCpuInstruction(),
            context,
            CpuFlags.parse("C-IDB1VN")
        );

        this.readZeroPageByteAndCheck(
            context,
            offset,
            (byte) 0x83
        );
    }

    @Test
    public void testDisassemble() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256)
        );

        context.setPc((short) 0x1000);
        context.setFlags(
            (byte) (0xff)
        );

        final byte offset = (byte) 0x81;

        context.writeByte(
            (short) 0x1000,
            offset
        );

        this.disassembleAndCheck(
            this.createCpuInstruction(),
            context,
            "ROL $81"
        );
    }

    @Override
    public CpuInstructionSharedUnaryRolZp createCpuInstruction() {
        return CpuInstructionSharedUnaryRolZp.INSTANCE;
    }

    // class............................................................................................................

    @Override
    public Class<CpuInstructionSharedUnaryRolZp> type() {
        return CpuInstructionSharedUnaryRolZp.class;
    }
}
