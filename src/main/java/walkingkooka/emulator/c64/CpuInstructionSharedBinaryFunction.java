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

abstract class CpuInstructionSharedBinaryFunction {

    /**
     * {@see CpuInstructionSharedBinaryFunctionAdc}
     */
    final static CpuInstructionSharedBinaryFunction ADC = CpuInstructionSharedBinaryFunctionAdc.instance();

    /**
     * {@see CpuInstructionSharedBinaryFunctionAnd}
     */
    final static CpuInstructionSharedBinaryFunction AND = CpuInstructionSharedBinaryFunctionAnd.instance();

    /**
     * {@see CpuInstructionSharedBinaryFunctionEor}
     */
    final static CpuInstructionSharedBinaryFunction EOR = CpuInstructionSharedBinaryFunctionEor.instance();

    /**
     * {@see CpuInstructionSharedBinaryFunctionOr}
     */
    final static CpuInstructionSharedBinaryFunction OR = CpuInstructionSharedBinaryFunctionOr.instance();

    /**
     * {@see CpuInstructionSharedBinaryFunctionSbc}
     */
    final static CpuInstructionSharedBinaryFunction SBC = CpuInstructionSharedBinaryFunctionSbc.instance();

    abstract byte handle(final byte left,
                         final byte right,
                         final CpuContext context);

    final int units(final byte value) {
        return 0x0f & value;
    }

    final int tens(final byte value) {
        return ((((int) value) & 0xf0) >> 4) & 0x0f;
    }
}
