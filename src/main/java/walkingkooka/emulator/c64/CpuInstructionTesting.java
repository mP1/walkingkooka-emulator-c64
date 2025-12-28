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

public interface CpuInstructionTesting<I extends CpuInstruction> extends CpuContextTesting {

    default void executeAccumulatorAndCheck(final I instruction,
                                            final CpuContext context,
                                            final byte a,
                                            final CpuFlags flags) {
        this.executeAndCheck(
            instruction,
            context,
            a,
            context.x(),
            context.y(),
            flags
        );
    }

    default void executeXAndCheck(final I instruction,
                                  final CpuContext context,
                                  final byte x,
                                  final CpuFlags flags) {
        this.executeAndCheck(
            instruction,
            context,
            context.a(),
            x,
            context.y(),
            flags
        );
    }

    default void executeYAndCheck(final I instruction,
                                  final CpuContext context,
                                  final byte y,
                                  final CpuFlags flags) {
        this.executeAndCheck(
            instruction,
            context,
            context.a(),
            context.x(),
            y,
            flags
        );
    }

    default void executeAndCheck(final I instruction,
                                 final CpuContext context,
                                 final byte a,
                                 final byte x,
                                 final byte y,
                                 final CpuFlags flags) {
        this.executeAndCheck(
            instruction,
            context,
            a,
            x,
            y,
            flags,
            context.stackPointer(),
            (short) (context.pc() + instruction.length())
        );
    }

    default void executeAndCheck(final I instruction,
                                 final CpuContext context,
                                 final byte a,
                                 final byte x,
                                 final byte y,
                                 final CpuFlags flags,
                                 final byte stackPointer,
                                 final short pc) {
        instruction.execute(context);

        this.aAndCheck(
            context,
            a
        );
        this.xAndCheck(
            context,
            x
        );
        this.yAndCheck(
            context,
            y
        );
        this.flagsAndCheck(
            context,
            flags.value()
        );
        this.stackPointerAndCheck(
            context,
            stackPointer
        );
        this.pcAndCheck(
            context,
            pc
        );
    }
}
