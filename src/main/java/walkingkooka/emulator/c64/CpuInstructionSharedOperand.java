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
 * https://www.c64-wiki.com/wiki/Addressing_mode
 * <pre>
 * An addressing mode one of a set of pre-set methods in which the "target address" for many machine language instructions is specified. Some instructions supports only a single addressing mode, whereas others may support up to eight of the 13 available addressing modes, which are:
 *
 * Implied addressing, which is rather a "no addressing mode at all"-option: Instructions which do not address an arbitrary memory location only supports this mode.
 * Accumulator addressing, supported by bit-shifting instructions, turns the "action" of the operation towards the accumulator.
 * Immediate addressing, which refers to the byte immediately following the opcode for the instruction.
 * Absolute addressing, which refers to a given 16-bit address
 * Indexed absolute addressing, indexed by either the X and Y index registers: These adds the index register to a base address, forming the final "destination" for the operation.
 * Zeropage addressing, which is similar to absolute addressing, but only works on addresses within the zeropage.
 * Indexed zeropage addressing: Effective address is zero page address plus the contents of the given register (X, or Y).
 * Relative addressing, which uses a single byte to specify the destination of conditional branches ("jumps") within 128 bytes of where the branching instruction resides.
 * Absolute-indirect addressing, which takes the content of a vector as its destination address.
 * Indexed-indirect addressing, which uses the X index register to select one of a range of vectors in zeropage and takes the address from that pointer. Extremely rarely used!
 * Indirect-indexed addressing, which adds the Y index register to the contents of a pointer to obtain the address. Very flexible instruction found in anything but the most trivial machine language routines!
 * In some assemblers and machine language monitors, implied and accumulator addressing are taken to be the same, so that e.g. an ASL performed on the accumulator is written as merely "ASL", whereas others distinguish between the two, requiring an ASL on the accumulator to be stated as "ASL A".
 * </pre>
 */
abstract class CpuInstructionSharedOperand {

    /**
     * {@see CpuInstructionSharedOperand}
     */
    final static CpuInstructionSharedOperand A = CpuInstructionSharedOperandRegisterA.instance();

    CpuInstructionSharedOperand() {
        super();
    }

    /**
     * The length of bytes for this operand.
     */
    abstract int length();

    abstract void handleBinaryConsumer(final CpuInstructionSharedBinaryConsumer consumer,
                                       final CpuContext context);

    abstract void handleBinaryFunction(final CpuInstructionSharedBinaryFunction function,
                                       final CpuContext context);

    abstract void handleUnaryFunction(final CpuInstructionSharedUnaryFunction function,
                                      final CpuContext context);

    abstract String disassemble(final CpuContext context);
}
