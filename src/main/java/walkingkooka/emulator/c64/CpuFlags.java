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

/**
 * A mutable value that holds CPU flags.
 */
public final class CpuFlags implements HasCpuFlags {

    /**
     * Creates a new instance with none of the flags set.
     */
    public static CpuFlags create() {
        return new CpuFlags();
    }

    private CpuFlags() {
        super();
    }

    /**
     * <pre>
     * Bit	Flag	Abbreviation	Purpose
     * 0	Carry Flag	C	Indicates when a bit of the result is to be carried to or borrowed from another byte. Also used for rotate and shift operations.
     * 1	Zero Flag	Z	A one indicates that the result of an operation is equal to zero.
     * 2	Interrupt Disable Flag	I	If set IRQ will be prevented (masked), except non-maskable interrupts (NMI).
     * 3	Decimal Mode Flag	D	If set arithmetic operations are calculated in decimal mode (otherwise usually in binary mode).
     * 4	Break Command Flag	B	Indicates that interrupt request has been triggered by an BRK opcode (not an IRQ).
     * 5	Unused	-	Cannot be changed, usually 1.
     * 6	Overflow Flag	V	Indicates that a result of an signed arithmetic operation exceeds the signed value range (-128 to 127).
     * 7	Negative Flag	N	A value of 1 indicates that the result is negative (bit 7 is set, for a two's complement representation).
     * </pre>
     */
    public byte value() {

        byte flags = Bit.BIT5.set();

        if (this.carry) {
            flags |= CARRY.set();
        }
        if (this.zero) {
            flags |= ZERO.set();
        }
        if (this.interruptDisabled) {
            flags |= INTERRUPT_DISABLED.set();
        }
        if (this.decimalMode) {
            flags |= DECIMAL_MODE.set();
        }
        if (this.breakFlag) {
            flags |= BREAK.set();
        }
        if (this.overflow) {
            flags |= OVERFLOW.set();
        }
        if (this.minus) {
            flags |= MINUS.set();
        }

        return flags;
    }

    public void setValue(final byte flags) {
        this.setCarry(
            CARRY.read(flags)
        );

        this.setZero(
            ZERO.read(flags)
        );

        this.setInterruptDisabled(
            INTERRUPT_DISABLED.read(flags)
        );

        this.setDecimalMode(
            DECIMAL_MODE.read(flags)
        );

        this.setBreak(
            BREAK.read(flags)
        );

        this.setOverflow(
            OVERFLOW.read(flags)
        );

        this.setMinus(
            MINUS.read(flags)
        );
    }

    private final Bit CARRY = Bit.BIT0;
    private final Bit ZERO = Bit.BIT1;
    private final Bit INTERRUPT_DISABLED = Bit.BIT2;
    private final Bit DECIMAL_MODE = Bit.BIT3;
    private final Bit BREAK = Bit.BIT5;
    private final Bit OVERFLOW = Bit.BIT6;
    private final Bit MINUS = Bit.BIT7;

    @Override
    public boolean isBreak() {
        return this.breakFlag;
    }

    @Override
    public void setBreak(final boolean breakFlag) {
        this.breakFlag = breakFlag;
    }

    private boolean breakFlag;

    @Override
    public boolean isCarry() {
        return this.carry;
    }

    @Override
    public void setCarry(final boolean carry) {
        this.carry = carry;
    }

    private boolean carry;

    @Override
    public boolean isDecimalMode() {
        return this.decimalMode;
    }

    @Override
    public void setDecimalMode(final boolean decimalMode) {
        this.decimalMode = decimalMode;
    }

    private boolean decimalMode;

    @Override
    public boolean isInterruptDisabled() {
        return this.interruptDisabled;
    }

    @Override
    public void setInterruptDisabled(final boolean interruptDisabled) {
        this.interruptDisabled = interruptDisabled;
    }

    private boolean interruptDisabled;

    @Override
    public boolean isMinus() {
        return this.minus;
    }

    @Override
    public void setMinus(final boolean minus) {
        this.minus = minus;
    }

    private boolean minus;

    @Override
    public boolean isOverflow() {
        return this.overflow;
    }

    @Override
    public void setOverflow(final boolean overflow) {
        this.overflow = overflow;
    }

    private boolean overflow;

    @Override
    public boolean isZero() {
        return this.zero;
    }

    @Override
    public void setZero(final boolean zero) {
        this.zero = zero;
    }

    private boolean zero;

    // Object...........................................................................................................

    @Override
    public int hashCode() {
        return Objects.hash(
            this.breakFlag,
            this.carry,
            this.decimalMode,
            this.interruptDisabled,
            this.minus,
            this.overflow,
            this.zero
        );
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
            other instanceof CpuFlags && this.equals0((CpuFlags) other);
    }

    private boolean equals0(final CpuFlags other) {
        return this.breakFlag == other.breakFlag &&
            this.carry == other.carry &&
            this.decimalMode == other.decimalMode &&
            this.interruptDisabled == other.interruptDisabled &&
            this.minus == other.minus &&
            this.overflow == other.overflow &&
            this.zero == other.zero;
    }
    
    @Override
    public String toString() {
        return (this.carry ? "C" : "-") +
            (this.zero ? "Z" : "-") +
            (this.interruptDisabled ? "I" : "-") +
            (this.decimalMode ? "D" : "-") +
            (this.breakFlag ? "B" : "-") +
            "1" +
            (this.overflow ? "O" : "-") +
            (this.minus ? "N" : "-");
    }
}
