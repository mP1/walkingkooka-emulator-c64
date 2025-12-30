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

final class CpuInstructionSharedClearOrSetSed extends CpuInstructionSharedClearOrSet {

    /**
     * Singleton
     */
    final static CpuInstructionSharedClearOrSetSed INSTANCE = new CpuInstructionSharedClearOrSetSed();

    private CpuInstructionSharedClearOrSetSed() {
        super();
    }

    @Override
    public byte opcode() {
        return (byte) 0xF8;
    }

    @Override
    public void execute(final CpuContext context) {
        context.setDecimalMode(true);
    }

    @Override
    public String disassemble(final CpuContext context) {
        Objects.requireNonNull(context, "context");
        return "SED";
    }
}
