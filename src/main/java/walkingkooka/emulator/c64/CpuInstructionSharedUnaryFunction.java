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

abstract class CpuInstructionSharedUnaryFunction {

    /**
     * {@see CpuInstructionSharedUnaryFunctionAsl}
     */
    final static CpuInstructionSharedUnaryFunction ASL = CpuInstructionSharedUnaryFunctionAsl.INSTANCE;

    /**
     * {@see CpuInstructionSharedUnaryFunctionDec}
     */
    final static CpuInstructionSharedUnaryFunction DEC = CpuInstructionSharedUnaryFunctionDec.INSTANCE;

    /**
     * {@see CpuInstructionSharedUnaryFunctionInc}
     */
    final static CpuInstructionSharedUnaryFunction INC = CpuInstructionSharedUnaryFunctionInc.INSTANCE;

    /**
     * {@see CpuInstructionSharedUnaryFunctionLsr}
     */
    final static CpuInstructionSharedUnaryFunction LSR = CpuInstructionSharedUnaryFunctionLsr.INSTANCE;

    /**
     * {@see CpuInstructionSharedUnaryFunctionRol}
     */
    final static CpuInstructionSharedUnaryFunction ROL = CpuInstructionSharedUnaryFunctionRol.INSTANCE;

    /**
     * {@see CpuInstructionSharedUnaryFunctionRor}
     */
    final static CpuInstructionSharedUnaryFunction ROR = CpuInstructionSharedUnaryFunctionRor.INSTANCE;

    /**
     * {@see CpuInstructionSharedUnaryFunctionStore}
     */
    final static CpuInstructionSharedUnaryFunction STORE = CpuInstructionSharedUnaryFunctionStore.INSTANCE;

    CpuInstructionSharedUnaryFunction() {
        super();
    }

    abstract byte handle(final byte value,
                         final CpuContext context);
}
