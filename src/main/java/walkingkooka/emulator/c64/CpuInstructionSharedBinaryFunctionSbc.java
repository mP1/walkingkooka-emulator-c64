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

import static walkingkooka.emulator.c64.CpuInstructionShared.setMinusAndZero;

/**
 * <pre>
 * https://www.masswerk.at/6502/6502_instruction_set.html#SBC
 *
 * SBC
 *
 * Subtract Memory from Accumulator with Borrow
 *
 * A - M - C̅ -> A
 * N	Z	C	I	D	V
 * +	+	+	-	-	+
 *
 * ...
 *
 * FLags with ADC and SBC
 * Besides the carry flag (C), which allows us to chain multi-byte operations, the
 * CPU sets the following flags on the result of an arithmetic operation:
 *
 * zero flag (Z) ........ set if the result is zero, else unset
 * negative flag (N)  ... the N flag always reflects the sign bit of the result
 * overflow flag (V)  ... indicates overflow in signed operations
 *
 * The latter may require explanation: how is signed overflow different from
 * the carry flag? The overflow flag is about a certain ambiguity of the sign bit
 * and the negative flag in signed context: if operands are of the same sign, the
 * case may occure, where the sign bit flips (as indicated by a change of the
 * negative flag), while the result is still of the same sign. This condition is
 * indicated by the overflow flag. Notably, such an overflow can never occur, when
 * the operands are of opposite signs.
 *
 * E.g., adding positive $40 to positive $40:
 *
 *             acc.      acc.     flags
 *             hex      binary    NVDIZC
 *
 * LDA #$40    $40   %0100.0000   000000
 * ADC #$40    $80   %1000.0000   110000
 *
 * Here, the change of the sign bit is unrelated to the actual value in the
 * accumulator, it is merely a consequence of carry propagation from bit 6 to
 * bit 7, the sign bit. Since both operands are positive, the result must be
 * positive, as well.
 * The overflow flag (V) is of interest in signed context only and has no meaning
 * in unsigned context.
 *
 * Decimal Mode (BCD)
 * Besides binary arithmetic, the 6502 processor supports a second mode, binary
 * coded decimal (BCD), where each byte, rather than representing a range of 0…255,
 * represents two decimal digits packed into a single byte. For this, a byte is
 * thought divided into two sections of 4 bits, the high- and the low-nibble. Only
 * values from 0…9 are used for each nibble and a byte can represent a range of a
 * 2-digit decimal value only, as in 0…99.
 * E.g.,
 *
 * dec     binary     hex
 *
 *  14   %0001.0100   $14
 *  98   %1001.1000   $98
 *
 * Mind how this intuitively translates to hexadecimal notation, where figures
 * A…F are never used.
 *
 * Whether or not the processor is in decimal mode is determined by the decimal
 * flag (D). If it is set (using SED) the processor will use BCD arithmetic.
 * If it is cleared (using CLD), the processor is in binary mode.
 * Decimal mode only affects instructions ADC and SBC (but not INC or DEC.)
 *
 * Examples:
 *
 * SED
 * CLC
 * LDA #$12
 * ADC #$44  ;accumulator now holds $56
 *
 * SED
 * CLC
 * LDA #$28
 * ADC #$14  ;accumulator now holds $42
 *
 * Mind that BCD mode is always unsigned:
 *
 *           acc. NVDIZC
 * SED
 * SEC
 * LDA #0    $00  001011
 * SBC #1    $99  101000
 *
 * The carry flag and the zero flag work in decimal mode as expected.
 * The negative flag is set similar to binary mode (and of questionable value.)
 * The overflow flag has no meaning in decimal mode.
 *
 * Multi-byte operations are just as in decimal mode: We first prepare the carry
 * and then chain operations of the individual bytes in increasing value order,
 * starting with the lowest value pair.
 *
 * (It may be important to note that Western Design Center (WDC) version of
 * the processor, the 65C02, always clears the decimal flag when it enters an
 * interrupt, while the original NMOS version of the 6502 does not.)
 * </pre>
 */
final class CpuInstructionSharedBinaryFunctionSbc extends CpuInstructionSharedBinaryFunction {

    /**
     * Singleton
     */
    final static CpuInstructionSharedBinaryFunctionSbc INSTANCE = new CpuInstructionSharedBinaryFunctionSbc();

    private CpuInstructionSharedBinaryFunctionSbc() {
        super();
    }

    @Override
    byte handle(final byte left,
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
            value = ADC.handle(
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

        boolean carry = false;
        if (tens < 0) {
            tens = tens + 10;
            carry = true;
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
}
