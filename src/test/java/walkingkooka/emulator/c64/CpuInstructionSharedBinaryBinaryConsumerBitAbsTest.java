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

public final class CpuInstructionSharedBinaryBinaryConsumerBitAbsTest extends CpuInstructionSharedBinaryBinaryConsumerBitTestCase<CpuInstructionSharedBinaryBinaryConsumerBitAbs> {

    @Test
    public void testExecute() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256)
        );

        final short pc = 0x1000;
        context.setPc(pc);

        final short address = 0x2000;
        context.writeAddress(
            pc,
            address
        );

        context.writeByte(
            address,
            (byte) 0xc0
        );

        context.setA(
            (byte) 0xff
        );

        this.executeAndCheck(
            context,
            "-----1VN"
        );
    }

    @Test
    public void testExecuteZero() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256)
        );

        final short pc = 0x1000;
        context.setPc(pc);

        final short address = 0x2000;
        context.writeAddress(
            pc,
            address
        );

        context.writeByte(
            address,
            (byte) 0xc0
        );

        context.setA(
            (byte) 0
        );

        this.executeAndCheck(
            context,
            "-Z---1VN"
        );
    }

    @Override
    public CpuInstructionSharedBinaryBinaryConsumerBitAbs createCpuInstruction() {
        return CpuInstructionSharedBinaryBinaryConsumerBitAbs.instance();
    }

    // class............................................................................................................

    @Override
    public Class<CpuInstructionSharedBinaryBinaryConsumerBitAbs> type() {
        return CpuInstructionSharedBinaryBinaryConsumerBitAbs.class;
    }
}
