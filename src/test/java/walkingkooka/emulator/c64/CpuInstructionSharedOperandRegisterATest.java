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

public final class CpuInstructionSharedOperandRegisterATest extends CpuInstructionSharedOperandRegisterTestCase<CpuInstructionSharedOperandRegisterA> {

    @Test
    public void testHandleUnaryFunction() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.fake(),
            SYMBOL_LOOKUP_UOE
        );

        final short pc = 0x1234;
        context.setPc(pc);

        context.setA((byte) 0x1);

        this.createCpuInstructionSharedOperand()
            .handleUnaryFunction(
                CpuInstructionSharedUnaryFunction.INC,
                context
            );

        this.checkEquals(
            (byte) 0x2,
            context.a(),
            "a"
        );

        this.pcAndCheck(
            context,
            pc
        );
    }

    @Test
    public void testReadValue() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.fake(),
            SYMBOL_LOOKUP_UOE
        );

        final short pc = 0x1234;
        context.setPc(pc);

        final byte value = 0x12;
        context.setA(value);

        this.readValueAndCheck(
            context,
            value
        );
    }

    @Override
    CpuInstructionSharedOperandRegisterA createCpuInstructionSharedOperand() {
        return CpuInstructionSharedOperandRegisterA.instance();
    }

    // class............................................................................................................

    @Override
    public Class<CpuInstructionSharedOperandRegisterA> type() {
        return CpuInstructionSharedOperandRegisterA.class;
    }
}
