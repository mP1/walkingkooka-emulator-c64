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

public final class CpuInstructionSharedBinaryConsumerCompareCmpImmTest extends CpuInstructionSharedBinaryConsumerCompareCmpTestCase<CpuInstructionSharedBinaryConsumerCompareCmpImm> {

    @Test
    public void testExecuteGreater() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256),
            SYMBOL_LOOKUP_UOE
        );

        final short pc = 0x1000;
        context.setPc(pc);

        context.writeAddress(
            pc,
            (byte) 0xc0
        );

        context.setA(
            (byte) 0xff
        );

        this.executeAndCheck(
            context,
            "C----1--"
        );
    }

    @Test
    public void testExecuteEquals() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256),
            SYMBOL_LOOKUP_UOE
        );

        final short pc = 0x1000;
        context.setPc(pc);

        context.writeAddress(
            pc,
            (byte) 0xc0
        );

        context.setA(
            (byte) 0xc0
        );

        this.executeAndCheck(
            context,
            "CZ---1--"
        );
    }

    @Test
    public void testExecuteLess() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256),
            SYMBOL_LOOKUP_UOE
        );

        final short pc = 0x1000;
        context.setPc(pc);

        context.writeAddress(
            pc,
            (byte) 0xc0
        );

        context.setA(
            (byte) 0x1
        );

        this.executeAndCheck(
            context,
            "-----1--"
        );
    }

    @Override
    public CpuInstructionSharedBinaryConsumerCompareCmpImm createCpuInstruction() {
        return CpuInstructionSharedBinaryConsumerCompareCmpImm.instance();
    }

    // class............................................................................................................

    @Override
    public Class<CpuInstructionSharedBinaryConsumerCompareCmpImm> type() {
        return CpuInstructionSharedBinaryConsumerCompareCmpImm.class;
    }
}
