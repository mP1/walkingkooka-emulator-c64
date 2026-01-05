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

abstract class CpuInstructionSharedBinaryBinaryFunctionAdc extends CpuInstructionSharedBinaryBinaryFunction {

    CpuInstructionSharedBinaryBinaryFunctionAdc() {
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
        return add(
            left,
            right,
            context
        );
    }

    static byte add(final byte left,
                    final byte right,
                    final CpuContext context) {
        final byte value;
        if (context.isDecimalMode()) {
            value = decimalMode(
                left,
                right,
                context
            );
        } else {
            value = binaryMode(
                left,
                right,
                context
            );
        }

        return value;
    }

    private static byte binaryMode(final byte left,
                                   final byte right,
                                   final CpuContext context) {
        final int value = (0xff & left) +
            (0xff & right) +
            carryToUnit(context);

        final byte byteValue = (byte) value;

        setMinusAndZero(
            byteValue,
            context
        );

        context.setCarry(
            (value & 0x100) != 0
        );

        // http://6502.org/tutorials/vflag.html
        context.setOverflow(
            (
                (left ^ value) &
                    (right ^ value) &
                    0x80
            ) != 0
        );

        return byteValue;
    }

    private static byte decimalMode(final byte left,
                                    final byte right,
                                    final CpuContext context) {
        int units = units(left) + units(right) + carryToUnit(context);
        if (units > 9) {
            units = units + 6;
        }

        int tens = tens(left) + tens(right);
        if (units > 9) {
            tens++;
        }

        boolean carry = false;
        if (tens > 9) {
            tens = tens + 6;
            carry = true;
        }

        final int value = (tens << 4) + (units & 0xf);

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
            1 :
            0;
    }

    @Override //
    final String mnemonic() {
        return "ADC";
    }
}
