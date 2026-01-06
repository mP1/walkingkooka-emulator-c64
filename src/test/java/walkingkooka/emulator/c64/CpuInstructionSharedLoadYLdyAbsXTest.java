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

public final class CpuInstructionSharedLoadYLdyAbsXTest extends CpuInstructionSharedLoadYTestCase<CpuInstructionSharedLoadYLdyAbsX> {

    @Test
    public void testExecute() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256)
        );

        final short pc = 0x1000;
        context.setPc(pc);

        context.writeAddress(
            pc,
            (short) 0x2000
        );

        context.writeByte(
            (short) 0x2005,
            (byte) 0x99
        );

        context.setY(
            (byte) 0xFF
        );
        context.setX(
            (byte) 0x5
        );
        context.setCarry(true);

        this.executeYAndCheck(
            context,
            (byte) 0x99,
            "C----1-N"
        );
    }

    @Override
    public CpuInstructionSharedLoadYLdyAbsX createCpuInstruction() {
        return CpuInstructionSharedLoadYLdyAbsX.instance();
    }

    @Override
    public Class<CpuInstructionSharedLoadYLdyAbsX> type() {
        return CpuInstructionSharedLoadYLdyAbsX.class;
    }
}
