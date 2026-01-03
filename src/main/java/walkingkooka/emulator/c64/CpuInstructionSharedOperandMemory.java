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

/**
 * Base class for all operands that read/write memory.
 */
abstract class CpuInstructionSharedOperandMemory extends CpuInstructionSharedOperand {

    CpuInstructionSharedOperandMemory() {
        super();
    }

    @Override //
    final void handleBinaryConsumer(final CpuInstructionSharedBinaryConsumer consumer,
                                    final CpuContext context) {
        throw new UnsupportedOperationException();
    }

    @Override
    void handleBinaryFunction(final CpuInstructionSharedBinaryFunction function,
                              final CpuContext context) {
        throw new UnsupportedOperationException();
    }

    @Override //
    final void handleUnaryFunction(final CpuInstructionSharedUnaryFunction function,
                                   final CpuContext context) {
        final short address = this.operandAddress(context);

        context.writeByte(
            address,
            function.handle(
                context.readByte(address),
                context
            )
        );
    }

    /**
     * Reads the operand address and advances the PC.
     */
    abstract short operandAddress(final CpuContext context);

    static short zeroPageOffsetAddress(final byte offset) {
        return (short) (0xff & offset);
    }

    final byte zeroPageOffset(final CpuContext context) {
        final short pc = context.pc();

        context.setPc(
            (short) (pc + 1)
        );
        return context.readByte(pc);
    }

    static short zeroPageOffsetAddress(final byte offset,
                                       final byte offset2) {
        return zeroPageOffsetAddress(
            (byte) (
                0xff & (offset + offset2)
            )
        );
    }

    static short address(final short address,
                         final byte offset) {
        return (short) (
            (address + ((0xff) & offset))
        );
    }
}
