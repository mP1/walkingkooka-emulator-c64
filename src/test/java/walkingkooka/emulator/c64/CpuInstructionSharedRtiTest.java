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

    @Test
    public void testNmiHandleInterruptRti() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256)
        );

        final short pc = 0x5005;
        final byte stackPointer = (byte) 0xf0;

        context.setPc(pc);
        context.setStackPointer(stackPointer);

        // IRQ vector = $1234
        context.writeByte(
            CpuContext.IRQ_VECTOR,
            (byte) 0x34
        );
        context.writeByte(
            (short) (CpuContext.IRQ_VECTOR + 1),
            (byte) 0x12
        );

        // RTI at IRQ vector
        final short handler = 0x1234;
        context.writeByte(
            handler,
            RTI
        );

        final CpuFlags flags = CpuFlags.parse("CZ------");
        context.setFlags(flags.value());

        context.irq();

        this.pcAndCheck(
            context,
            pc // irq() doesnt update pc
        );

        context.handleInterrupts();
        this.pcAndCheck(
            context,
            handler
        );

        final Cpu cpu = Cpus.basic(
            Lists.of(
                CpuInstructionSharedJsr.INSTANCE,
                CpuInstructions.rti()
            )
        );

        cpu.step(context); // RTI

        this.pcAndCheck(
            context,
            pc
        );

        this.stackPointerAndCheck(
            context,
            stackPointer
        );
        this.flagsAndCheck(
            context,
            flags
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
