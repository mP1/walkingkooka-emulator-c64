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

public final class CpuInstructionSharedOperandMemoryIndirectIndexedYTest extends CpuInstructionSharedOperandMemoryTestCase<CpuInstructionSharedOperandMemoryIndirectIndexedY> {

    @Test
    public void testHandleUnaryFunction() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256)
        );

        final short pc = 0x1000;
        context.setPc(pc);

        final byte offset = (byte) 0x80;
        context.writeByte(
            pc,
            offset
        );

        context.setY((byte) 1);

        context.writeAddress(
            (short) (0xff & offset),
            (short) 0x2000
        );

        final short address = 0x2001;
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
    public void testHandleUnaryFunctionPageOverflow() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256)
        );

        final short pc = 0x1000;
        context.setPc(pc);

        final byte offset = (byte) 0x80;
        context.writeByte(
            pc,
            offset
        );

        context.setY((byte) 0x82);

        context.writeAddress(
            (short) (0xff & offset),
            (short) 0x2081
        );

        final short address = 0x2103;
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
    public void testReadValue() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256)
        );

        final short pc = 0x1000;
        context.setPc(pc);

        final byte offset = (byte) 0x81;

        context.writeByte(
            pc,
            offset
        );

        context.writeAddress(
            (short) (0xff & offset),
            (short) 0x2000
        );

        final byte value = (byte) 0x34;
        context.writeByte(
            (short) 0x2012,
            value
        );

        context.setY((byte) 0x12);

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

        final byte offset = (byte) 0x81;

        context.writeByte(
            pc,
            offset
        );

        context.writeAddress(
            (short) (0xff & offset),
            (short) 0x2081
        );

        final byte value = (byte) 0x56;
        context.writeByte(
            (short) 0x2103,
            value
        );

        context.setY((byte) 0x82);

        this.readValueAndCheck(
            context,
            value
        );
    }

    @Test
    public void testDisassemble() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256)
        );

        final short pc = 0x1000;
        context.setPc(pc);

        final byte offset = (byte) 0x81;

        context.writeByte(
            pc,
            offset
        );

        context.writeAddress(
            (short) (0xff & offset),
            (short) 0x2000
        );

        final byte value = (byte) 0x34;
        context.writeByte(
            (short) 0x2012,
            value
        );

        context.setY((byte) 0x12);

        this.disassembleAndCheck(
            this.createCpuInstructionSharedOperand(),
            context,
            "($81),Y $2012"
        );
    }

    @Test
    public void testDisassembleYPageOverflow() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256)
        );

        final short pc = 0x1000;
        context.setPc(pc);

        final byte offset = (byte) 0x81;

        context.writeByte(
            pc,
            offset
        );

        context.writeAddress(
            (short) (0xff & offset),
            (short) 0x2080
        );

        final byte value = (byte) 0x34;
        context.writeByte(
            (short) 0x2111,
            value
        );

        context.setY((byte) 0x91);

        this.disassembleAndCheck(
            this.createCpuInstructionSharedOperand(),
            context,
            "($81),Y $2111"
        );
    }

    @Override
    CpuInstructionSharedOperandMemoryIndirectIndexedY createCpuInstructionSharedOperand() {
        return CpuInstructionSharedOperandMemoryIndirectIndexedY.instance();
    }

    // class............................................................................................................

    @Override
    public Class<CpuInstructionSharedOperandMemoryIndirectIndexedY> type() {
        return CpuInstructionSharedOperandMemoryIndirectIndexedY.class;
    }
}
