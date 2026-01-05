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

abstract class CpuInstructionSharedBinaryBinaryFunctionSbc extends CpuInstructionSharedBinaryBinaryFunction {

    CpuInstructionSharedBinaryBinaryFunctionSbc() {
        super();
    }

    @Override //
    final CpuInstructionSharedOperandRegister register() {
        return CpuInstructionSharedOperand.A;
    }

    @Override //
    final byte handle(final byte left,
                      final byte right,
                      final CpuContext context) {
        final byte value;
        if (context.isDecimalMode()) {
            value = this.decimalMode(
                left,
                right,
                context
            );
        } else {
            value = CpuInstructionSharedBinaryBinaryFunctionAdc.add(
                left,
                (byte) ~right,
                context
            );
        }

        return value;
    }

    private byte decimalMode(final byte left,
                             final byte right,
                             final CpuContext context) {
        int units = units(left) - units(right) - carryToUnit(context);
        int tens = tens(left) - tens(right);

        if (units < 0) {
            units = units + 10;
            tens--;
        }

        boolean carry = true;
        if (tens < 0) {
            tens = tens + 10;
            carry = false;
        }

        final int value = (tens << 4) | (units & 0xf);

        final byte byteValue = (byte) value;

        setMinusAndZero(
            byteValue,
            context
        );

        context.setCarry(carry);

        context.setOverflow(false); // never sets overflow, always clears

        return byteValue;
    }

    private static int carryToUnit(final CpuContext context) {
        return context.isCarry() ?
            0 :
            1;
    }

    @Override //
    final String mnemonic() {
        return "SBC";
    }
}
