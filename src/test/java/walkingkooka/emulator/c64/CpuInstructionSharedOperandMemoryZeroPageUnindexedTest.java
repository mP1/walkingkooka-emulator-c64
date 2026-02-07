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

public final class CpuInstructionSharedOperandMemoryZeroPageUnindexedTest extends CpuInstructionSharedOperandMemoryZeroPageTestCase<CpuInstructionSharedOperandMemoryZeroPageUnindexed> {

    @Test
    public void testHandleUnaryFunction() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256),
            SYMBOL_LOOKUP_UOE
        );

        final short pc = 0x1234;
        context.setPc(pc);

        final byte offset = (byte) 0x80;
        context.writeByte(
            pc,
            offset
        );

        final byte value = (byte) 0x34;
        context.writeZeroPageByte(
            offset,
            value
        );

        this.createCpuInstructionSharedOperand()
            .handleUnaryFunction(
                CpuInstructionSharedUnaryFunction.INC,
                context
            );

        this.checkEquals(
            (byte) (value + 1),
            context.readZeroPageByte(offset),
            "zp"
        );

        this.pcAndCheck(
            context,
            (short) (pc + 1)
        );
    }

    @Test
    public void testReadValue() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256),
            SYMBOL_LOOKUP_UOE
        );

        final short pc = 0x1000;
        context.setPc(pc);

        final byte offset = (byte) 0x80;
        context.writeByte(
            pc,
            offset
        );

        final byte value = (byte) 0x34;
        context.writeZeroPageByte(
            offset,
            value
        );

        this.readValueAndCheck(
            context,
            value
        );
    }

    @Override
    CpuInstructionSharedOperandMemoryZeroPageUnindexed createCpuInstructionSharedOperand() {
        return CpuInstructionSharedOperandMemoryZeroPageUnindexed.instance();
    }

    // class............................................................................................................

    @Override
    public Class<CpuInstructionSharedOperandMemoryZeroPageUnindexed> type() {
        return CpuInstructionSharedOperandMemoryZeroPageUnindexed.class;
    }
}
