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

public final class CpuInstructionSharedTransferPlaTest extends CpuInstructionSharedTransferTestCase<CpuInstructionSharedTransferPla> {

    @Test
    public void testExecuteZero() {
        final short pc = 0x1000;

        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(
                256 * 256
            )
        );
        context.setPc(pc);

        context.setStackPointer((byte) 0x40);
        context.setFlags((byte) 0);

        final byte value = 0;
        context.writeByte(
            (short) 0x141,
            value
        );

        this.executeAndCheck(
            this.createCpuInstruction(),
            context,
            value,
            context.x(),
            context.y(),
            "-Z---1--",
            (byte) 0x41, // stackPointer
            pc
        );
    }

    @Test
    public void testExecuteZero2() {
        final short pc = 0x1000;

        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(
                256 * 256
            )
        );
        context.setPc(pc);

        context.setStackPointer((byte) 0x40);
        context.setFlags((byte) 0xff);

        final byte value = 0;
        context.writeByte(
            (short) 0x141,
            value
        );

        this.executeAndCheck(
            this.createCpuInstruction(),
            context,
            value,
            context.x(),
            context.y(),
            "CZIDB1V-",
            (byte) 0x41, // stackPointer
            pc
        );
    }

    @Test
    public void testExecuteNegative() {
        final short pc = 0x1000;

        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(
                256 * 256
            )
        );
        context.setPc(pc);

        context.setStackPointer((byte) 0x40);
        context.setFlags((byte) 0);

        final byte value = (byte) 0xfe;
        context.writeByte(
            (short) 0x141,
            value
        );

        this.executeAndCheck(
            this.createCpuInstruction(),
            context,
            value,
            context.x(),
            context.y(),
            "-----1-N",
            (byte) 0x41, // stackPointer
            pc
        );
    }

    @Test
    public void testExecutePositive() {
        final short pc = 0x1000;

        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(
                256 * 256
            )
        );
        context.setPc(pc);

        context.setStackPointer((byte) 0x40);
        context.setFlags((byte) 0);

        final byte value = (byte) 0x12;
        context.writeByte(
            (short) 0x141,
            value
        );

        this.executeAndCheck(
            this.createCpuInstruction(),
            context,
            value,
            context.x(),
            context.y(),
            "-----1--",
            (byte) 0x41, // stackPointer
            pc
        );
    }

    @Override
    public CpuInstructionSharedTransferPla createCpuInstruction() {
        return CpuInstructionSharedTransferPla.INSTANCE;
    }

    @Override
    public Class<CpuInstructionSharedTransferPla> type() {
        return CpuInstructionSharedTransferPla.class;
    }
}
