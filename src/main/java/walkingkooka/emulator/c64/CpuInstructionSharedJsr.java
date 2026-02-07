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

import java.util.Objects;

final class CpuInstructionSharedJsr extends CpuInstructionShared {

    static CpuInstructionSharedJsr instance() {
        if (null == INSTANCE) {
            INSTANCE = new CpuInstructionSharedJsr();
        }
        return INSTANCE;
    }

    private static CpuInstructionSharedJsr INSTANCE;

    private CpuInstructionSharedJsr() {
        super();
    }

    @Override
    public byte opcode() {
        return JSR;
    }

    @Override
    public int length() {
        return 3;
    }

    // https://c64os.com/post/6502instructions#JSR
    //
    // Jump Saving Return
    // JSR pushes the address-1 of the next operation to the stack before transferring the value of the argument to the program counter.
    // JSR behaves just like a JMP, but saves the return address to the stack first, thus creating a subroutine.
    //
    // Subroutines are normally terminated by an RTS instruction.
    @Override
    public void execute(final CpuContext context) {
        Objects.requireNonNull(context, "context");

        final short pc = context.pc();

        final byte lo = context.readByte(pc);
        final byte hi = context.readByte(
            add(pc, 1)
        );

        final short save = add(pc, 1);

        context.push(
            hi(save)
        );
        context.push(
            lo(save)
        );

        context.setPc(
            address(hi, lo)
        );
    }

    @Override
    public String disassemble(final CpuContext context) {
        Objects.requireNonNull(context, "context");

        final short pc = context.pc();

        final byte lo = context.readByte(pc);
        final byte hi = context.readByte(
            add(pc, 1)
        );

        return "JSR " +
            context.addressSymbol(
                address(
                    hi,
                    lo
                )
            );
    }
}
