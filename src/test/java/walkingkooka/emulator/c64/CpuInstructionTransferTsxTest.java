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

public final class CpuInstructionTransferTsxTest extends CpuInstructionTransferTestCase<CpuInstructionTransferTsx> {

    @Test
    public void testExecuteZero() {
        final short pc = 0x1000;

        final CpuContext context = CpuContexts.basic(
            AddressBuses.fake()
        );
        context.setPc(pc);

        final byte stackPointer = 0x0;
        final byte x = 0x22;

        context.setStackPointer(stackPointer);
        context.setX(x);

        context.setFlags((byte) 0);

        this.executeAndCheck(
            this.createCpuInstruction(),
            context,
            context.a(),
            stackPointer,
            context.y(),
            CpuFlags.parse("-Z---1--"),
            stackPointer,
            pc
        );
    }

    @Test
    public void testExecuteNonZero() {
        final short pc = 0x1000;

        final CpuContext context = CpuContexts.basic(
            AddressBuses.fake()
        );
        context.setPc(pc);

        final byte stackPointer = 0x11;
        final byte x = 0x22;

        context.setStackPointer(stackPointer);
        context.setX(x);

        context.setFlags((byte) 0);

        this.executeAndCheck(
            this.createCpuInstruction(),
            context,
            context.a(),
            stackPointer,
            context.y(),
            CpuFlags.parse("-----1--"),
            stackPointer,
            pc
        );
    }

    @Test
    public void testExecuteNegative() {
        final short pc = 0x1000;

        final CpuContext context = CpuContexts.basic(
            AddressBuses.fake()
        );
        context.setPc(pc);

        final byte stackPointer = (byte) 0xff;
        final byte x = 0x22;

        context.setStackPointer(stackPointer);
        context.setX(x);

        context.setFlags((byte) 0);

        this.executeAndCheck(
            this.createCpuInstruction(),
            context,
            context.a(),
            stackPointer,
            context.y(),
            CpuFlags.parse("-----1-N"),
            stackPointer,
            pc
        );
    }

    @Override
    public CpuInstructionTransferTsx createCpuInstruction() {
        return CpuInstructionTransferTsx.INSTANCE;
    }

    @Override
    public Class<CpuInstructionTransferTsx> type() {
        return CpuInstructionTransferTsx.class;
    }
}
