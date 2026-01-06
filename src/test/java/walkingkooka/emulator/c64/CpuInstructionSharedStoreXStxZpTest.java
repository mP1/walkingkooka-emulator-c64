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

public final class CpuInstructionSharedStoreXStxZpTest extends CpuInstructionSharedStoreXTestCase<CpuInstructionSharedStoreXStxZp> {

    @Test
    public void testExecute() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(
                256 * 256
            )
        );

        context.writeAddress(
            context.pc(),
            (byte) 0x80
        );

        final byte value = 0x23;
        context.setX(value);

        this.executeAndMemoryWriteCheck(
            context,
            (short) 0x0080,
            value
        );
    }

    @Override
    public CpuInstructionSharedStoreXStxZp createCpuInstruction() {
        return CpuInstructionSharedStoreXStxZp.instance();
    }

    @Override
    public Class<CpuInstructionSharedStoreXStxZp> type() {
        return CpuInstructionSharedStoreXStxZp.class;
    }
}
