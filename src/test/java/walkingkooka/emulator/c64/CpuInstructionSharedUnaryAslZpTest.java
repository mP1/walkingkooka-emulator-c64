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

public final class CpuInstructionSharedUnaryAslZpTest extends CpuInstructionSharedUnaryAslTestCase<CpuInstructionSharedUnaryAslZp> {

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

        context.writeByte(
            offset,
            (byte) 0x00
        );

        this.executeAndCheck(
            this.createCpuInstruction(),
            context,
            CpuFlags.parse("-ZIDB1V-")
        );

        this.readByteAndCheck(
            context,
            offset,
            (byte) 0
        );
    }

    @Test
    public void testExecute2() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256)
        );

        context.setPc((short) 0x1000);
        context.setFlags(
            (byte) (0xff)
        );

        final byte offset = (byte) 0x81;
        final short address = 0xff & offset;

        context.writeByte(
            (short) 0x1000,
            offset
        );

        context.writeByte(
            address,
            (byte) 0x0f
        );

        this.executeAndCheck(
            this.createCpuInstruction(),
            context,
            CpuFlags.parse("--IDB1V-")
        );

        this.readByteAndCheck(
            context,
            address,
            (byte) 0x1E
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
            "ASL $81"
        );
    }

    @Test
    public void testDisassemble2() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256)
        );

        context.setPc((short) 0x1000);
        context.setFlags(
            (byte) (0xff)
        );

        final byte offset = (byte) 0x23;

        context.writeByte(
            (short) 0x1000,
            offset
        );

        this.disassembleAndCheck(
            this.createCpuInstruction(),
            context,
            "ASL $23"
        );
    }

    @Override
    public CpuInstructionSharedUnaryAslZp createCpuInstruction() {
        return CpuInstructionSharedUnaryAslZp.INSTANCE;
    }

    // class............................................................................................................

    @Override
    public Class<CpuInstructionSharedUnaryAslZp> type() {
        return CpuInstructionSharedUnaryAslZp.class;
    }
}
