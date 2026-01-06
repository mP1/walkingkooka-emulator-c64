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

public final class CpuInstructionSharedLoadALdaImmTest extends CpuInstructionSharedLoadATestCase<CpuInstructionSharedLoadALdaImm> {

    @Test
    public void testExecuteZero() {
        this.executeImmediateAndCheck(
            (byte) 0x00,
            (byte) 0x00,
            "--------",
            "-Z------"
        );
    }

    @Test
    public void testExecuteZero2() {
        this.executeImmediateAndCheck(
            (byte) 0x00,
            (byte) 0x00,
            "CZ----VN",
            "CZ----V-"
        );
    }

    @Test
    public void testExecutePositive() {
        this.executeImmediateAndCheck(
            (byte) 0x00,
            (byte) 0x01,
            "C-----V-",
            "C-----V-"
        );
    }

    @Test
    public void testExecuteNegative() {
        this.executeImmediateAndCheck(
            (byte) 0x00,
            (byte) 0xFF,
            "C-----V-",
            "C-----VN"
        );
    }

    private void executeImmediateAndCheck(final byte a,
                                          final byte immediateValue,
                                          final String flags,
                                          final String expectedFlags) {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256)
        );

        final short pc = 0x1000;
        context.setPc(pc);

        context.setA(a);
        context.setFlags(
            CpuFlags.parse(flags)
                .value()
        );

        context.writeByte(
            pc,
            immediateValue
        );

        this.executeAccumulatorAndCheck(
            context,
            immediateValue,
            expectedFlags
        );
    }

    @Override
    public CpuInstructionSharedLoadALdaImm createCpuInstruction() {
        return CpuInstructionSharedLoadALdaImm.instance();
    }

    @Override
    public Class<CpuInstructionSharedLoadALdaImm> type() {
        return CpuInstructionSharedLoadALdaImm.class;
    }
}
