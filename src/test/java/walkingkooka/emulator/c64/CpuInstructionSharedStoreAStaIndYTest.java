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

public final class CpuInstructionSharedStoreAStaIndYTest extends CpuInstructionSharedStoreATestCase<CpuInstructionSharedStoreAStaIndY> {

    @Test
    public void testExecute() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(
                256 * 256
            ),
            SYMBOL_LOOKUP_UOE
        );

        context.setPc(
            (short) 0x1000
        );

        context.writeByte(
            context.pc(),
            (byte) 0x80
        );

        context.writeAddress(
            (short) 0x80,
            (short) 0x2080
        );

        final byte value = 0x23;
        context.setA(value);

        context.setY(
            (byte) 0x90
        );

        this.executeAndMemoryWriteCheck(
            context,
            (short) 0x2110,
            value
        );
    }

    @Override
    public CpuInstructionSharedStoreAStaIndY createCpuInstruction() {
        return CpuInstructionSharedStoreAStaIndY.instance();
    }

    @Override
    public Class<CpuInstructionSharedStoreAStaIndY> type() {
        return CpuInstructionSharedStoreAStaIndY.class;
    }
}
