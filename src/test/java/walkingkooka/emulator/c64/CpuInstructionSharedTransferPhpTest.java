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

public final class CpuInstructionSharedTransferPhpTest extends CpuInstructionSharedTransferTestCase<CpuInstructionSharedTransferPhp> {

    @Test
    public void testExecute() {
        final short pc = 0x1000;

        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(
                256 * 256
            ),
            SYMBOL_LOOKUP_UOE
        );
        context.setPc(pc);

        final byte flags = 0x55;
        context.setFlags(flags);

        context.setStackPointer((byte) 0x40);

        this.executeAndCheck(
            this.createCpuInstruction(),
            context,
            context.a(),
            context.x(),
            context.y(),
            "C-I-B1V-",
            (byte) 0x3f, // stackPointer
            pc
        );

        this.readByteAndCheck(
            context,
            (short) 0x140,
            (byte) (flags | CpuFlags.UNUSED.set())
        );
    }

    @Test
    public void testExecute2() {
        final short pc = 0x1000;

        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(
                256 * 256
            ),
            SYMBOL_LOOKUP_UOE
        );
        context.setPc(pc);

        final byte flags = (byte) 0xF0;
        context.setFlags(flags);

        context.setStackPointer((byte) 0x40);

        this.executeAndCheck(
            this.createCpuInstruction(),
            context,
            context.a(),
            context.x(),
            context.y(),
            "----B1VN",
            (byte) 0x3f, // stackPointer
            pc
        );

        this.readByteAndCheck(
            context,
            (short) 0x140,
            flags
        );
    }

    @Override
    public CpuInstructionSharedTransferPhp createCpuInstruction() {
        return CpuInstructionSharedTransferPhp.instance();
    }

    @Override
    public Class<CpuInstructionSharedTransferPhp> type() {
        return CpuInstructionSharedTransferPhp.class;
    }
}
