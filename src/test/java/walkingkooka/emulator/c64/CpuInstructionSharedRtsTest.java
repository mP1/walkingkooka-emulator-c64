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

public final class CpuInstructionSharedRtsTest extends CpuInstructionTestCase<CpuInstructionSharedRts> {

    @Test
    public void testStep() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256)
        );

        final byte hi = (byte) 0xFF;
        final byte lo = (byte) 0xEE;

        final byte stackPointer = (byte) 0xf3;

        context.setStackPointer(stackPointer);
        context.push(hi);
        context.push(lo);

        final short pc = 0x1234;
        context.setPc(pc);

        this.executeAndCheck(
            CpuInstructionSharedRts.INSTANCE,
            context,
            context.a(),
            context.x(),
            context.y(),
            CpuFlags.parse("-----1--"),
            stackPointer,
            (short) 0xFFEF
        );
    }

    @Test
    public void testStep2() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256)
        );

        final byte hi = (byte) 0xFF;
        final byte lo = (byte) 0xEE;

        final byte stackPointer = (byte) 0xf3;

        context.setStackPointer(stackPointer);
        context.push(hi);
        context.push(lo);

        final short pc = 0x1234;
        context.setPc(pc);

        context.setFlags((byte) 0xff);

        this.executeAndCheck(
            CpuInstructionSharedRts.INSTANCE,
            context,
            context.a(),
            context.x(),
            context.y(),
            CpuFlags.parse("CZIDB1VN"),
            stackPointer,
            (short) 0xFFEF
        );
    }

    @Test
    public void testStep3() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256)
        );

        final byte hi = 0x12;
        final byte lo = 0x34;

        final byte stackPointer = (byte) 0xf3;

        context.setStackPointer(stackPointer);
        context.push(hi);
        context.push(lo);

        final short pc = 0x6789;
        context.setPc(pc);

        context.setFlags((byte) 0xff);

        this.executeAndCheck(
            CpuInstructionSharedRts.INSTANCE,
            context,
            context.a(),
            context.x(),
            context.y(),
            CpuFlags.parse("CZIDB1VN"),
            stackPointer,
            (short) 0x1235
        );
    }

    @Test
    public void testStep4() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256)
        );

        final byte hi = 0x02;
        final byte lo = 0x03;

        final byte stackPointer = (byte) 0xf3;

        context.setStackPointer(stackPointer);
        context.push(hi);
        context.push(lo);

        final short pc = 0x6789;
        context.setPc(pc);

        context.setFlags((byte) 0xff);

        this.executeAndCheck(
            CpuInstructionSharedRts.INSTANCE,
            context,
            context.a(),
            context.x(),
            context.y(),
            CpuFlags.parse("CZIDB1VN"),
            stackPointer,
            (short) 0x0204
        );
    }

    @Test
    public void testDisassemble() {
        this.disassembleAndCheck(
            CpuInstructionSharedRts.INSTANCE,
            CpuContexts.fake(),
            "RTS"
        );
    }

    @Override
    public CpuInstructionSharedRts createCpuInstruction() {
        return CpuInstructionSharedRts.INSTANCE;
    }

    // class............................................................................................................

    @Override
    public Class<CpuInstructionSharedRts> type() {
        return CpuInstructionSharedRts.class;
    }
}
