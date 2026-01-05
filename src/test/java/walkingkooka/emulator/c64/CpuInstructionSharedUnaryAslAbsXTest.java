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

public final class CpuInstructionSharedUnaryAslAbsXTest extends CpuInstructionSharedUnaryAslTestCase<CpuInstructionSharedUnaryAslAbsX> {

    @Test
    public void testExecute() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256)
        );

        context.setPc((short) 0x1000);
        context.setFlags(
            (byte) (0xff)
        );

        context.writeAddress(
            (short) 0x1000,
            (short) 0x2000
        );

        context.writeByte(
            (short) 0x2001,
            (byte) 0xC1
        );

        context.setX((byte) 0x1);

        this.executeAndCheck(
            this.createCpuInstruction(),
            context,
            CpuFlags.parse("C-IDB1VN")
        );

        this.readByteAndCheck(
            context,
            (short) 0X2001,
            (byte) 0x82
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

        context.writeAddress(
            (short) 0x1000,
            (short) 0x2000
        );

        context.writeByte(
            (short) 0x2001,
            (byte) 0xC1
        );

        this.disassembleAndCheck(
            this.createCpuInstruction(),
            context,
            "ASL $2000,X"
        );
    }

    @Override
    public CpuInstructionSharedUnaryAslAbsX createCpuInstruction() {
        return CpuInstructionSharedUnaryAslAbsX.instance();
    }

    // class............................................................................................................

    @Override
    public Class<CpuInstructionSharedUnaryAslAbsX> type() {
        return CpuInstructionSharedUnaryAslAbsX.class;
    }
}
