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

abstract class CpuInstructionSharedTransfer extends CpuInstructionShared {

    CpuInstructionSharedTransfer() {
        super();

        // CpuInstructionSharedTransferPla -> Pla
        this.mnemonic = this.getClass()
            .getSimpleName()
            .substring(CpuInstructionSharedTransfer.class.getSimpleName().length())
            .toUpperCase();
    }

    @Override
    public final int length() {
        return 1;
    }

    @Override
    public final void execute(final CpuContext context) {
        Objects.requireNonNull(context, "context");

        this.write(
            this.read(context),
            context
        );
    }

    abstract byte read(final CpuContext context);

    abstract void write(final byte value,
                        final CpuContext context);

    @Override
    public final String disassemble(final CpuContext context) {
        Objects.requireNonNull(context, "context");
        return this.mnemonic;
    }

    private final String mnemonic;
}
