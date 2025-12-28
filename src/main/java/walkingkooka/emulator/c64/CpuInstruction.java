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
 * Represents a single instruction that will be dispatched when its opcode is encountered by the {@link Cpu}.
 */
public interface CpuInstruction {

    /**
     * THe opcode of this instruction.
     */
    byte opcode();

    /**
     * Length of this instruction including arguments.
     */
    int length();

    /**
     * Execute this instruction, advancing pc, updating memory or flags etc, ready for the next instruction to be executed.
     */
    void execute(final CpuContext context);
}
