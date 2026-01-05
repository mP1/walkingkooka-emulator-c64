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

public final class CpuInstructionSharedUnaryRolATest extends CpuInstructionSharedUnaryRolTestCase<CpuInstructionSharedUnaryRolA> {

    @Test
    public void testExecute() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.fake()
        );

        context.setPc((short) 0x1000);
        context.setCarry(true);
        context.setA((byte) 0xC1);

        this.executeAccumulatorAndCheck(
            this.createCpuInstruction(),
            context,
            (byte) 0x83,
            "C----1-N"
        );
    }

    @Test
    public void testDisassemble() {
        this.disassembleAndCheck(
            this.createCpuInstruction(),
            CpuContexts.basic(
                AddressBuses.fake()
            ),
            "ROL A"
        );
    }


    @Override
    public CpuInstructionSharedUnaryRolA createCpuInstruction() {
        return CpuInstructionSharedUnaryRolA.INSTANCE;
    }

    // class............................................................................................................

    @Override
    public Class<CpuInstructionSharedUnaryRolA> type() {
        return CpuInstructionSharedUnaryRolA.class;
    }
}
