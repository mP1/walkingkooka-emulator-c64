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

public final class CpuInstructionSharedUnaryDexTest extends CpuInstructionSharedUnaryDecTestCase<CpuInstructionSharedUnaryDex> {

    @Test
    public void testExecute() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.fake()
        );

        context.setPc((short) 0x1000);
        context.setCarry(true);
        context.setX((byte) 0x0f);

        this.executeXAndCheck(
            this.createCpuInstruction(),
            context,
            (byte) 0x0e,
            CpuFlags.parse("C----1--")
        );
    }

    @Test
    public void testDisassemble() {
        this.disassembleAndCheck(
            this.createCpuInstruction(),
            CpuContexts.basic(
                AddressBuses.fake()
            ),
            "DEX"
        );
    }


    @Override
    public CpuInstructionSharedUnaryDex createCpuInstruction() {
        return CpuInstructionSharedUnaryDex.INSTANCE;
    }

    // class............................................................................................................

    @Override
    public Class<CpuInstructionSharedUnaryDex> type() {
        return CpuInstructionSharedUnaryDex.class;
    }
}
