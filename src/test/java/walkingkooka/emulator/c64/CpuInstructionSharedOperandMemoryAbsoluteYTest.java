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

public final class CpuInstructionSharedOperandMemoryAbsoluteYTest extends CpuInstructionSharedOperandMemoryAbsoluteTestCase<CpuInstructionSharedOperandMemoryAbsoluteY> {

    @Test
    public void testHandleUnaryFunction() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256)
        );

        final short pc = 0x1234;
        context.setPc(pc);

        final short address = 0x4000;
        context.writeAddress(
            pc,
            address
        );

        context.setY((byte) 1);

        final byte value = (byte) 0x34;
        context.writeByte(
            (short) 0x4001,
            value
        );

        this.createCpuInstructionSharedOperand()
            .handleUnaryFunction(
                CpuInstructionSharedUnaryFunction.INC,
                context
            );

        this.checkEquals(
            (byte) (value + 1),
            context.readByte((short) 0x4001),
            "absolute,y byte"
        );

        this.pcAndCheck(
            context,
            (short) (pc + 2)
        );
    }

    @Test
    public void testHandleUnaryFunctionDoesntWrap() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256)
        );

        final short pc = 0x1234;
        context.setPc(pc);

        context.writeAddress(
            pc,
            (short) 0x4081
        );

        context.setY((byte) 0x82);

        final byte value = (byte) 0x34;
        context.writeByte(
            (short) 0x4103,
            value
        );

        this.createCpuInstructionSharedOperand()
            .handleUnaryFunction(
                CpuInstructionSharedUnaryFunction.INC,
                context
            );

        this.checkEquals(
            (byte) (value + 1),
            context.readByte((short) 0x4103),
            "absolute,y byte"
        );

        this.pcAndCheck(
            context,
            (short) (pc + 2)
        );
    }

    @Test
    public void testReadValue() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256)
        );

        final short pc = 0x1000;
        context.setPc(pc);

        context.writeAddress(
            pc,
            (short) 0x2000
        );

        context.setY((byte) 0x82);

        final byte value = (byte) 0x34;

        context.writeByte(
            (short) 0x2082,
            value
        );

        this.readValueAndCheck(
            context,
            value
        );
    }

    @Test
    public void testReadValuePageOverflow() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256)
        );

        final short pc = 0x1000;
        context.setPc(pc);

        context.writeAddress(
            pc,
            (short) 0x2081
        );

        context.setY((byte) 0x82);

        final byte value = (byte) 0x34;

        context.writeByte(
            (short) 0x2103,
            value
        );

        this.readValueAndCheck(
            context,
            value
        );
    }

    @Override
    CpuInstructionSharedOperandMemoryAbsoluteY createCpuInstructionSharedOperand() {
        return CpuInstructionSharedOperandMemoryAbsoluteY.instance();
    }

    // class............................................................................................................

    @Override
    public Class<CpuInstructionSharedOperandMemoryAbsoluteY> type() {
        return CpuInstructionSharedOperandMemoryAbsoluteY.class;
    }
}
