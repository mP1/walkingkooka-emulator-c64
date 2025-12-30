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
import walkingkooka.collect.list.Lists;

public final class CpuInstructionSharedJsrTest extends CpuInstructionTestCase<CpuInstructionSharedJsr> {

    @Test
    public void testStep() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256)
        );

        final short pc = 0x5678;

        final byte hi = 0x12;
        final byte lo = 0x34;

        context.writeByte(
            pc,
            lo
        );
        context.writeByte(
            (short) (pc + 1),
            hi
        );

        context.setPc(pc);
        context.setStackPointer(
            (byte) 0xF2
        );

        this.executeAndCheck(
            CpuInstructionSharedJsr.INSTANCE,
            context,
            context.a(),
            context.x(),
            context.y(),
            CpuFlags.parse("-----1--"),
            (byte) 0xF0, // stackPointer
            (short) 0x1234
        );

        this.checkEquals(
            Integer.toHexString(0x5679),
            Integer.toHexString(
                context.readAddress(
                    (short) 0x1f1
                )
            )
        );
    }

    // 5000: JSR 1234
    // 1234: RTS
    @Test
    public void testJsrThenRts() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256)
        );

        final short pc = 0x5005;

        final byte hi = 0x12;
        final byte lo = 0x34;

        context.writeByte(
            pc,
            (byte) 0x20 // JSR $1234
        );

        context.writeByte(
            (short) (pc + 1),
            lo
        );
        context.writeByte(
            (short) (pc + 2),
            hi
        );

        context.setPc(pc);

        final byte stackPointer = (byte) 0xF0;
        context.setStackPointer(stackPointer);

        context.writeByte(
            (short) 0x1234,
            (byte) 0x60 // RTS
        );

        final Cpu cpu = Cpus.basic(
            Lists.of(
                CpuInstructionSharedJsr.INSTANCE,
                CpuInstructions.rts()
            )
        );

        cpu.step(context);
        cpu.step(context);

        this.pcAndCheck(
            context,
            (short) 0x5008
        );

        this.stackPointerAndCheck(
            context,
            stackPointer
        );
    }

    @Test
    public void testDisassemble() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256)
        );

        final short pc = 0x5000;

        final byte hi = 0x12;
        final byte lo = 0x34;

        context.writeByte(
            pc,
            lo
        );
        context.writeByte(
            (short) (pc + 1),
            hi
        );

        context.setPc(pc);

        this.disassembleAndCheck(
            CpuInstructionSharedJsr.INSTANCE,
            context,
            "JSR $1234"
        );
    }

    @Override
    public CpuInstructionSharedJsr createCpuInstruction() {
        return CpuInstructionSharedJsr.INSTANCE;
    }

    // class............................................................................................................

    @Override
    public Class<CpuInstructionSharedJsr> type() {
        return CpuInstructionSharedJsr.class;
    }
}
