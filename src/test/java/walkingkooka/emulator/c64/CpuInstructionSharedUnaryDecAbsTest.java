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

public final class CpuInstructionSharedUnaryDecAbsTest extends CpuInstructionSharedUnaryDecTestCase<CpuInstructionSharedUnaryDecAbs> {

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

        final short valueAddress = 0x4567;

        context.writeAddress(
            (short) 0x1000,
            valueAddress
        );

        context.writeByte(
            valueAddress,
            (byte) 0x40
        );

        this.executeAndCheck(
            this.createCpuInstruction(),
            context,
            CpuFlags.parse("C-IDB1V-")
        );

        this.readByteAndCheck(
            context,
            (short) 0x4567,
            (byte) 0x3F
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

        final short valueAddress = 0x4567;

        context.writeAddress(
            (short) 0x1000,
            valueAddress
        );

        context.writeByte(
            valueAddress,
            (byte) 0x01
        );

        this.disassembleAndCheck(
            this.createCpuInstruction(),
            context,
            "DEC LABEL4567"
        );
    }

    @Override
    public CpuInstructionSharedUnaryDecAbs createCpuInstruction() {
        return CpuInstructionSharedUnaryDecAbs.instance();
    }

    // class............................................................................................................

    @Override
    public Class<CpuInstructionSharedUnaryDecAbs> type() {
        return CpuInstructionSharedUnaryDecAbs.class;
    }
}
