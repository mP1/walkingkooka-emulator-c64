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

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class CpuInstructionSharedOperandMemoryImmediateTest extends CpuInstructionSharedOperandMemoryTestCase<CpuInstructionSharedOperandMemoryImmediate> {

    @Test
    public void testHandleUnaryFunction() {
        assertThrows(
            UnsupportedOperationException.class,
            () -> this.createCpuInstructionSharedOperand()
                .handleUnaryFunction(
                    CpuInstructionSharedUnaryFunction.INC,
                    CpuContexts.fake()
                )
        );
    }

    @Test
    public void testReadValue() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256),
            SYMBOL_LOOKUP_UOE
        );

        final short pc = 0x1000;
        context.setPc(pc);

        final byte value = 0x34;
        context.writeByte(
            pc,
            value
        );

        this.readValueAndCheck(
            context,
            value
        );
    }

    @Test
    public void testDisassemble() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.memory(256 * 256),
            SYMBOL_LOOKUP_UOE
        );

        final short pc = 0x1000;
        context.setPc(pc);

        context.writeByte(
            pc,
            (byte) 0x55
        );

        this.disassembleAndCheck(
            this.createCpuInstructionSharedOperand(),
            context,
            "#$55"
        );
    }

    @Override
    CpuInstructionSharedOperandMemoryImmediate createCpuInstructionSharedOperand() {
        return CpuInstructionSharedOperandMemoryImmediate.instance();
    }

    // class............................................................................................................

    @Override
    public Class<CpuInstructionSharedOperandMemoryImmediate> type() {
        return CpuInstructionSharedOperandMemoryImmediate.class;
    }
}
