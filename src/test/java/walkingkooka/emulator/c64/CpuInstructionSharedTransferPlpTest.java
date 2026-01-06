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

public final class CpuInstructionSharedTransferPlpTest extends CpuInstructionSharedTransferTestCase<CpuInstructionSharedTransferPlp> {

    @Test
    public void testExecuteIncludesBreakFlag() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256)
        );

        context.setPc(
            (short) 0x1000
        );

        context.setFlags(
            (byte) 0xff
        );

        final CpuFlags flags = CpuFlags.create();
        flags.setOverflow(true);
        flags.setCarry(true);
        flags.setBreak(true);

        context.push(
            flags.value()
        );

        this.executeAndCheck(
            CpuInstructionSharedTransferPlp.instance(),
            context,
            context.a(),
            context.x(),
            context.y(),
            flags,
            (byte) (context.stackPointer() + 1)
        );
    }

    @Test
    public void testExecuteMissingBreakFlag() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256)
        );

        context.setPc(
            (short) 0x1000
        );

        context.setFlags(
            (byte) 0x00
        );

        final CpuFlags flags = CpuFlags.create();
        flags.setOverflow(true);
        flags.setCarry(true);

        context.push(
            flags.value()
        );

        this.executeAndCheck(
            CpuInstructionSharedTransferPlp.instance(),
            context,
            context.a(),
            context.x(),
            context.y(),
            flags,
            (byte) (context.stackPointer() + 1)
        );
    }

    @Override
    public CpuInstructionSharedTransferPlp createCpuInstruction() {
        return CpuInstructionSharedTransferPlp.instance();
    }

    // class............................................................................................................

    @Override
    public Class<CpuInstructionSharedTransferPlp> type() {
        return CpuInstructionSharedTransferPlp.class;
    }
}
