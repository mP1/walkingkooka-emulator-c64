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

public final class CpuInstructionSharedUnaryRorZpXTest extends CpuInstructionSharedUnaryRorTestCase<CpuInstructionSharedUnaryRorZpX> {

    @Test
    public void testExecute() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256),
            SYMBOL_LOOKUP_UOE
        );

        context.setPc((short) 0x1000);
        context.setFlags(
            (byte) (0xff)
        );

        final byte offset = (byte) 0x40;

        context.writeByte(
            (short) 0x1000,
            offset
        );

        final byte x = 2;
        context.setX(x);

        final byte valueAddress = (byte) (offset + x);

        context.writeZeroPageByte(
            valueAddress,
            (byte) 0x83
        );

        this.executeAndCheck(
            this.createCpuInstruction(),
            context,
            CpuFlags.parse("C-IDB1VN")
        );

        this.readZeroPageByteAndCheck(
            context,
            valueAddress,
            (byte) 0xC1
        );
    }

    @Test
    public void testExecuteZeroPageOffsetWrap() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256),
            SYMBOL_LOOKUP_UOE
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

        final byte x = (byte) 0x82;
        context.setX(x);

        final byte valueAddress = (byte) (offset + x);

        context.writeZeroPageByte(
            valueAddress,
            (byte) 0x88
        );

        this.executeAndCheck(
            this.createCpuInstruction(),
            context,
            CpuFlags.parse("--IDB1VN")
        );

        this.readZeroPageByteAndCheck(
            context,
            valueAddress,
            (byte) 0xC4
        );
    }

    @Test
    public void testDisassemble() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256),
            SYMBOL_LOOKUP_DISASSEMBLE
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

        final byte x = (byte) 0x82;
        context.setX(x);

        final short valueAddress = 0xff & (offset + x);

        context.writeByte(
            valueAddress,
            (byte) 0x03
        );

        this.disassembleAndCheck(
            this.createCpuInstruction(),
            context,
            "ROR LABEL3,X"
        );
    }

    @Override
    public CpuInstructionSharedUnaryRorZpX createCpuInstruction() {
        return CpuInstructionSharedUnaryRorZpX.instance();
    }

    // class............................................................................................................

    @Override
    public Class<CpuInstructionSharedUnaryRorZpX> type() {
        return CpuInstructionSharedUnaryRorZpX.class;
    }
}
