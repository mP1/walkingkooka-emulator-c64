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

public final class CpuInstructionSharedBinaryFunctionSbcZpTest extends CpuInstructionSharedBinaryFunctionSbcTestCase<CpuInstructionSharedBinaryFunctionSbcZp> {

    @Test
    public void testExecute() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256),
            SYMBOL_LOOKUP_UOE
        );

        final short pc = 0x1000;
        context.setPc(pc);

        final byte offset = 0x40;

        context.writeByte(
            pc,
            offset
        );

        context.writeZeroPageByte(
            offset,
            (byte) 0x12
        );

        context.setA(
            (byte) 0x34
        );
        context.setCarry(true);

        this.executeAccumulatorAndCheck(
            context,
            (byte) 0x22,
            "C----1--"
        );
    }

    @Test
    public void testExecuteWithDecimalMode() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256),
            SYMBOL_LOOKUP_UOE
        );

        final short pc = 0x1000;
        context.setPc(pc);

        final byte offset = 0x40;

        context.writeByte(
            pc,
            offset
        );

        context.writeZeroPageByte(
            offset,
            (byte) 0x77
        );

        context.setA(
            (byte) 0x88
        );
        context.setCarry(true);
        context.setDecimalMode(true);

        this.executeAccumulatorAndCheck(
            context,
            (byte) 0x11,
            "C--D-1--"
        );
    }

    @Override
    public CpuInstructionSharedBinaryFunctionSbcZp createCpuInstruction() {
        return CpuInstructionSharedBinaryFunctionSbcZp.instance();
    }

    // class............................................................................................................

    @Override
    public Class<CpuInstructionSharedBinaryFunctionSbcZp> type() {
        return CpuInstructionSharedBinaryFunctionSbcZp.class;
    }
}
