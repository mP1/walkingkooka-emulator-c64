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

    @Override
    public byte flags() {
        return this.flags.value();
    }

    @Override
    public void setFlags(final byte flags) {
        this.flags.setValue(flags);
    }

    @Override
    public boolean isBreak() {
        return this.flags.isBreak();
    }

    @Override
    public void setBreak(final boolean breakFlag) {
        this.flags.setBreak(breakFlag);
    }

    @Override
    public boolean isCarry() {
        return this.flags.isCarry();
    }

    @Override
    public void setCarry(final boolean carry) {
        this.flags.setCarry(carry);
    }

    @Override
    public boolean isDecimalMode() {
        return this.flags.isDecimalMode();
    }

    @Override
    public void setDecimalMode(final boolean decimalMode) {
        this.flags.setDecimalMode(decimalMode);
    }
    @Override
    public boolean isInterruptDisabled() {
        return this.flags.isInterruptDisabled();
    }

    @Override
    public void setInterruptDisabled(final boolean interruptDisabled) {
        this.flags.setInterruptDisabled(interruptDisabled);
    }
    @Override
    public boolean isMinus() {
        return this.flags.isMinus();
    }

    @Override
    public void setMinus(final boolean minus) {
        this.flags.setMinus(minus);
    }

    @Override
    public boolean isOverflow() {
        return this.flags.isOverflow();
    }

    @Override
    public void setOverflow(final boolean overflow) {
        this.flags.setOverflow(overflow);
    }

    @Override
    public boolean isZero() {
        return this.flags.isZero();
    }

    @Override
    public void setZero(final boolean zero) {
        this.flags.setZero(zero);
    }

    private CpuFlags flags = CpuFlags.create();

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
            ", CpuFlags: " + this.flags;
    }

    private static String hex(final byte value) {
        return "0x" + Integer.toHexString(BYTE_MASK & value);
    }

    private static String hexAddress(final short value) {
        return "0x" + Integer.toHexString(0xFFFF & value);
    }
}
