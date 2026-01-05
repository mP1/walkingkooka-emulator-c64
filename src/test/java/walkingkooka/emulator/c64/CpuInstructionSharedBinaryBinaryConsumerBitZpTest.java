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

public final class CpuInstructionSharedBinaryBinaryConsumerBitZpTest extends CpuInstructionSharedBinaryBinaryConsumerBitTestCase<CpuInstructionSharedBinaryBinaryConsumerBitZp> {

    @Test
    public void testExecuteZero() {
        this.executeZeroPageAndCheck(
            (byte) 0x0,
            (byte) 0x0,
            "CZ---1VN",
            "CZ---1--"
        );
    }

    @Test
    public void testExecuteZero2() {
        this.executeZeroPageAndCheck(
            (byte) 0x0,
            (byte) 0x1,
            "CZ---1VN",
            "CZ---1--"
        );
    }

    @Test
    public void testExecuteNonZero() {
        this.executeZeroPageAndCheck(
            (byte) 0xF0,
            (byte) 0x55,
            "CZ---1VN",
            "C----1V-"
        );
    }

    @Test
    public void testExecuteBit67() {
        this.executeZeroPageAndCheck(
            (byte) 0x0,
            (byte) 0xC0,
            "CZ---1VN",
            "CZ---1VN"
        );
    }

    @Test
    public void testExecuteBit672() {
        this.executeZeroPageAndCheck(
            (byte) 0xFF,
            (byte) 0xC0,
            "CZ---1VN",
            "C----1VN"
        );
    }

    private void executeZeroPageAndCheck(final byte a,
                                         final byte value,
                                         final String flags,
                                         final String expected) {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256)
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
            value
        );

        context.setA(a);
        context.setFlags(
            CpuFlags.parse(flags)
                .value()
        );

        this.executeAndCheck(
            context,
            expected
        );
    }

    @Override
    public CpuInstructionSharedBinaryBinaryConsumerBitZp createCpuInstruction() {
        return CpuInstructionSharedBinaryBinaryConsumerBitZp.instance();
    }

    // class............................................................................................................

    @Override
    public Class<CpuInstructionSharedBinaryBinaryConsumerBitZp> type() {
        return CpuInstructionSharedBinaryBinaryConsumerBitZp.class;
    }
}
