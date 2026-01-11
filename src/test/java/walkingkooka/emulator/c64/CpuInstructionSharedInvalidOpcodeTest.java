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

import static org.junit.jupiter.api.Assertions.assertSame;

public final class CpuInstructionSharedInvalidOpcodeTest extends CpuInstructionSharedTestCase<CpuInstructionSharedInvalidOpcode> {

    @Test
    public void testWithCached() {
        final byte opcode = (byte) 0x12;

        assertSame(
            CpuInstructionSharedInvalidOpcode.with(opcode),
            CpuInstructionSharedInvalidOpcode.with(opcode)
        );
    }

    @Test
    public void testStep() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256)
        );

        context.setPc((short) 0x1000);

        context.setStackPointer(
            (byte) 0xff
        );

        final byte value = (byte) 0xff;

        context.addWatcher(
            new FakeCpuWatcher() {
                @Override
                public void onInvalidOpcode(CpuContext context) {
                    context.setA(value);
                }
            }
        );

        this.executeAccumulatorAndCheck(
            context,
            value,
            "--------"
        );
    }

    @Test
    public void testDisassemble() {
        this.disassembleAndCheck(
            this.createCpuInstruction(),
            CpuContexts.fake(),
            "INV $12"
        );
    }

    @Override
    public CpuInstructionSharedInvalidOpcode createCpuInstruction() {
        return CpuInstructionSharedInvalidOpcode.with(
            (byte) 0x12
        );
    }

    // class............................................................................................................

    @Override
    public Class<CpuInstructionSharedInvalidOpcode> type() {
        return CpuInstructionSharedInvalidOpcode.class;
    }
}
