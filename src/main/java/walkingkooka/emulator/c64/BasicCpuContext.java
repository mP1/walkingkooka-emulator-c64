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

final class BasicCpuContext implements CpuContext {

    static BasicCpuContext with(final AddressBus addressBus) {
        return new BasicCpuContext(
            Objects.requireNonNull(addressBus, "addressBus")
        );
    }

    private final static int BYTE_MASK = 0xff;

    private final static int PAGE_SIZE = 256;

    private BasicCpuContext(final AddressBus addressBus) {
        this.addressBus = addressBus;
    }

    @Override
    public byte a() {
        return this.a;
    }

    @Override
    public void setA(final byte a) {
        this.a = a;
    }

    private byte a;

    @Override
    public byte x() {
        return this.x;
    }

    @Override
    public void setX(final byte x) {
        this.x = x;
    }

    private byte x;

    @Override
    public byte y() {
        return this.y;
    }

    @Override
    public void setY(final byte y) {
        this.y = y;
    }

    private byte y;

    // https://www.c64-wiki.com/wiki/Processor_Status_Register

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
    @Override
    public byte flags() {

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

    @Override
    public void setFlags(final byte flags) {
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

    @Override
    public byte stackPointer() {
        return this.stackPointer;
    }

    @Override
    public void setStackPointer(final byte stackPointer) {
        this.stackPointer = stackPointer;
    }

    private byte stackPointer;

    @Override
    public short pc() {
        return this.pc;
    }

    @Override
    public void setPc(final short pc) {
        this.pc = pc;
    }

    private short pc;

    @Override
    public byte readByte(final short address) {
        return this.addressBus.read(address);
    }

    @Override
    public void writeByte(final short address,
                          final byte value) {
        this.addressBus.write(
            address,
            value
        );
    }

    @Override
    public short readAddress(final short address) {
        final byte lo = this.readByte(address);
        final byte hi = this.readByte((short) (1 + address));

        return (short) (
            fromByte(lo) +
            fromByte(hi) * PAGE_SIZE
        );
    }

    @Override
    public byte readZeroPageByte(final byte offset) {
        return this.addressBus.read(offset);
    }

    @Override
    public void writeZeroPageByte(final byte offset,
                                  final byte value) {
        this.addressBus.write(
            offset,
            value
        );
    }

    @Override
    public short readZeroPageAddress(final byte offset) {
        return this.readAddress(offset);
    }

    @Override
    public void push(final byte value) {
        final byte stackPointer = this.stackPointer;

        this.addressBus.write(
            STACK_PAGE_OFFSET | fromByte(stackPointer),
            value
        );

        this.stackPointer = toByte(stackPointer - 1);
    }

    @Override
    public byte pop() {
        final byte stackPointer = toByte(this.stackPointer + 1);

        final byte value = this.addressBus.read(STACK_PAGE_OFFSET | fromByte(stackPointer));
        this.stackPointer = stackPointer;
        return value;
    }

    private final static short STACK_PAGE_OFFSET = 0x100;

    private AddressBus addressBus;

    private static int fromByte(final byte value) {
        return BYTE_MASK & value;
    }

    private static byte toByte(final int value) {
        return (byte)(BYTE_MASK & value);
    }

    // Object...........................................................................................................

    @Override
    public String toString() {
        return "A: " + hex(this.a) +
            ", X: " + hex(this.x) +
            ", Y: " + hex(this.y) +
            ", SP: " + hex(this.stackPointer) +
            ", PC: " + hexAddress(this.pc) +
            ", Flags: " +
            (this.carry ? "C" : "-") +
            (this.zero ? "Z" : "-") +
            (this.interruptDisabled ? "I" : "-") +
            (this.decimalMode ? "D" : "-") +
            (this.breakFlag ? "B" : "-") +
            "1" +
            (this.overflow ? "O" : "-") +
            (this.minus ? "N" : "-");
    }

    private static String hex(final byte value) {
        return "0x" + Integer.toHexString(BYTE_MASK & value);
    }

    private static String hexAddress(final short value) {
        return "0x" + Integer.toHexString(0xFFFF & value);
    }
}
