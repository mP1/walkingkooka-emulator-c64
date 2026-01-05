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

public final class CpuInstructionSharedBinaryBinaryFunctionOrIndXTest extends CpuInstructionSharedBinaryBinaryFunctionOrTestCase<CpuInstructionSharedBinaryBinaryFunctionOrIndX> {

    @Test
    public void testExecute() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256)
        );

        final short pc = 0x1000;
        context.setPc(pc);

        context.writeByte(
            pc,
            (byte) 0x40
        );

        final short address = 0x2000;
        context.writeAddress(
            (short) 0x45,
            address
        );

        context.writeByte(
            address,
            (byte) 0xF1
        );

        context.setA(
            (byte) 0x1F
        );
        context.setX(
            (byte) 5
        );
        context.setCarry(true);

        this.executeAccumulatorAndCheck(
            context,
            (byte) 0xFF,
            "C----1-N"
        );
    }

    @Override
    public CpuInstructionSharedBinaryBinaryFunctionOrIndX createCpuInstruction() {
        return CpuInstructionSharedBinaryBinaryFunctionOrIndX.instance();
    }

    // class............................................................................................................

    @Override
    public Class<CpuInstructionSharedBinaryBinaryFunctionOrIndX> type() {
        return CpuInstructionSharedBinaryBinaryFunctionOrIndX.class;
    }
}
