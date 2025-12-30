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

public final class CpuInstructionSharedRtiTest extends CpuInstructionSharedTestCase<CpuInstructionSharedRti> {

    @Test
    public void testStepWithBreak() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256)
        );

        final CpuFlags cpuFlags = CpuFlags.create();
        cpuFlags.setValue((byte) 0xff);

        final byte hi = (byte) 0xFF;
        final byte lo = (byte) 0xEE;

        final byte stackPointer = (byte) 0xf3;

        context.setStackPointer(stackPointer);

        context.push(hi);
        context.push(lo);
        context.push(cpuFlags.value());

        final short pc = 0x1234;
        context.setPc(pc);

        cpuFlags.setBreak(false);

        this.executeAndCheck(
            CpuInstructionSharedRti.INSTANCE,
            context,
            context.a(),
            context.x(),
            context.y(),
            cpuFlags,
            stackPointer,
            (short) 0xFFEE
        );
    }

    @Test
    public void testStepWithoutBreak() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256)
        );

        final CpuFlags cpuFlags = CpuFlags.create();
        cpuFlags.setValue((byte) 0xff);
        cpuFlags.setBreak(false);

        final byte hi = (byte) 0xFF;
        final byte lo = (byte) 0xEE;

        final byte stackPointer = (byte) 0xf3;

        context.setStackPointer(stackPointer);

        context.push(hi);
        context.push(lo);
        context.push(cpuFlags.value());

        final short pc = 0x1234;
        context.setPc(pc);

        this.executeAndCheck(
            CpuInstructionSharedRti.INSTANCE,
            context,
            context.a(),
            context.x(),
            context.y(),
            cpuFlags,
            stackPointer,
            (short) 0xFFEE
        );
    }

    @Test
    public void testStepWithoutBreak2() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256)
        );

        final CpuFlags cpuFlags = CpuFlags.create();
        cpuFlags.setValue((byte) 0xff);
        cpuFlags.setBreak(false);

        final byte hi = (byte) 0x12;
        final byte lo = (byte) 0x34;

        final byte stackPointer = (byte) 0xf3;

        context.setStackPointer(stackPointer);

        context.push(hi);
        context.push(lo);
        context.push(cpuFlags.value());

        final short pc = 0x6789;
        context.setPc(pc);

        this.executeAndCheck(
            CpuInstructionSharedRti.INSTANCE,
            context,
            context.a(),
            context.x(),
            context.y(),
            cpuFlags,
            stackPointer,
            (short) 0x1234
        );
    }

    @Test
    public void testDisassemble() {
        this.disassembleAndCheck(
            CpuInstructionSharedRti.INSTANCE,
            CpuContexts.fake(),
            "RTI"
        );
    }

    @Override
    public CpuInstructionSharedRti createCpuInstruction() {
        return CpuInstructionSharedRti.INSTANCE;
    }

    // class............................................................................................................

    @Override
    public Class<CpuInstructionSharedRti> type() {
        return CpuInstructionSharedRti.class;
    }
}
