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

public final class CpuInstructionSharedTransferTxaTest extends CpuInstructionSharedTransferTestCase<CpuInstructionSharedTransferTxa> {

    @Test
    public void testExecuteZero() {
        final short pc = 0x1000;

        final CpuContext context = CpuContexts.basic(
            AddressBuses.fake()
        );
        context.setPc(pc);

        final byte x = 0x0;
        final byte a = 0x22;

        context.setX(x);
        context.setA(a);

        context.setFlags((byte) 0);

        this.executeAndCheck(
            this.createCpuInstruction(),
            context,
            x,
            x,
            context.y(),
            "-Z---1--",
            context.stackPointer(),
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

        final byte x = 0x11;
        final byte a = 0x22;

        context.setA(a);
        context.setX(x);

        context.setFlags((byte) 0);

        this.executeAndCheck(
            this.createCpuInstruction(),
            context,
            x,
            x,
            context.y(),
            "--------",
            context.stackPointer(),
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

        final byte x = (byte) 0xff;
        final byte a = 0x22;

        context.setA(a);
        context.setX(x);

        context.setFlags((byte) 0);

        this.executeAndCheck(
            this.createCpuInstruction(),
            context,
            x,
            x,
            context.y(),
            "-----1-N",
            context.stackPointer(),
            pc
        );
    }

    @Override
    public CpuInstructionSharedTransferTxa createCpuInstruction() {
        return CpuInstructionSharedTransferTxa.INSTANCE;
    }

    @Override
    public Class<CpuInstructionSharedTransferTxa> type() {
        return CpuInstructionSharedTransferTxa.class;
    }
}
