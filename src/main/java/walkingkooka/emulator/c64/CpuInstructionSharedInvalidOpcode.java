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

import walkingkooka.collect.map.Maps;

import java.util.Map;
import java.util.Objects;

/**
 * A {@link CpuInstruction} that when executed invokes {@link CpuContext#handleInvalidOpcode()}.
 */
final class CpuInstructionSharedInvalidOpcode extends CpuInstructionShared {

    static CpuInstructionSharedInvalidOpcode with(final byte opcode) {
        CpuInstructionSharedInvalidOpcode instruction = CACHE.get(opcode);
        if (null == instruction) {
            instruction = new CpuInstructionSharedInvalidOpcode(opcode);
            CACHE.put(
                opcode,
                instruction
            );
        }
        return instruction;
    }

    private final static Map<Byte, CpuInstructionSharedInvalidOpcode> CACHE = Maps.sorted();

    private CpuInstructionSharedInvalidOpcode(final byte opcode) {
        super();
        this.opcode = opcode;
    }

    @Override
    public byte opcode() {
        return this.opcode;
    }

    private final byte opcode;

    @Override
    public int length() {
        return 1;
    }

    @Override
    public void execute(final CpuContext context) {
        Objects.requireNonNull(context, "context");

        context.handleInvalidOpcode();
    }

    @Override
    public String disassemble(final CpuContext context) {
        Objects.requireNonNull(context, "context");
        return "INV " + hexByte(this.opcode);
    }
}
