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

public final class CpuInstructionSharedOperandMemoryIndexedIndirectXTest extends CpuInstructionSharedOperandMemoryTestCase<CpuInstructionSharedOperandMemoryIndexedIndirectX> {

    @Test
    public void testHandleUnaryFunction() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256)
        );

        final short pc = 0x1000;
        context.setPc(pc);

        context.writeByte(
            pc,
            (byte) 0x80
        );

        context.setX((byte) 1);

        final short address = (short) 0x2000;
        context.writeAddress(
            (short) (0x81),
            address
        );

        context.writeByte(
            address,
            (byte) 0x12
        );

        this.createCpuInstructionSharedOperand()
            .handleUnaryFunction(
                CpuInstructionSharedUnaryFunction.INC,
                context
            );

        this.checkEquals(
            (byte) 0x13,
            context.readByte(address),
            "zp"
        );

        this.pcAndCheck(
            context,
            (short) (pc + 1)
        );
    }

    @Test
    public void testHandleUnaryFunctionZeroPageWrap() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256)
        );

        final short pc = 0x1000;
        context.setPc(pc);

        context.writeByte(
            pc,
            (byte) 0x81
        );

        context.setX((byte) 0x82);

        final short address = (short) 0x2000;
        context.writeAddress(
            (short) (0x3),
            address
        );

        context.writeByte(
            address,
            (byte) 0x12
        );

        this.createCpuInstructionSharedOperand()
            .handleUnaryFunction(
                CpuInstructionSharedUnaryFunction.INC,
                context
            );

        this.checkEquals(
            (byte) 0x13,
            context.readByte(address),
            "zp"
        );

        this.pcAndCheck(
            context,
            (short) (pc + 1)
        );
    }

    @Override
    CpuInstructionSharedOperandMemoryIndexedIndirectX createCpuInstructionSharedOperand() {
        return CpuInstructionSharedOperandMemoryIndexedIndirectX.instance();
    }

    // class............................................................................................................

    @Override
    public Class<CpuInstructionSharedOperandMemoryIndexedIndirectX> type() {
        return CpuInstructionSharedOperandMemoryIndexedIndirectX.class;
    }
}
