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

import walkingkooka.collect.set.SortedSets;
import walkingkooka.text.CharSequences;

import java.util.Objects;
import java.util.Set;

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

    private final CpuFlags flags = CpuFlags.create();

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
        return this.addressBus.read(
            mask(address)
        );
    }

    @Override
    public void writeByte(final short address,
                          final byte value) {
        this.addressBus.write(
            mask(address),
            value
        );
    }

    private static int mask(final short address) {
        return address & 0xFFFF;
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
    public void writeAddress(final short address,
                             final short value) {
        this.writeByte(
            address,
            lo(value)
        );
        this.writeByte(
            (short) (address + 1),
            hi(value)
        );
    }

    @Override
    public byte readZeroPageByte(final byte offset) {
        return this.addressBus.read(
            zeroPageAddress(offset)
        );
    }

    @Override
    public void writeZeroPageByte(final byte offset,
                                  final byte value) {
        this.addressBus.write(
            zeroPageAddress(offset),
            value
        );
    }

    @Override
    public short readZeroPageAddress(final byte offset) {
        return this.readAddress(
            zeroPageAddress(offset)
        );
    }

    private static short zeroPageAddress(final byte offset) {
        return (short) (offset & BYTE_MASK);
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

    private final AddressBus addressBus;

    @Override
    public void reset() {
        this.mode = this.mode | RESET;
    }

    @Override
    public void nmi() {
        this.mode = this.mode | NMI;
    }

    @Override
    public void irq() {
        this.mode = this.mode | IRQ;
    }

    @Override
    public void handleInterrupts() {
        int mode = this.mode;

        if (NONE != mode) {
            if ((mode & RESET) != 0) {
                this.handleReset();

                this.setA((byte) 0);
                this.setX((byte) 0);
                this.setY((byte) 0);

                this.setFlags((byte) 0);

                this.setStackPointer((byte) 0xff);

                this.setPc(
                    this.readAddress(RESET_VECTOR)
                );
                this.mode = NONE;
            }

            if ((mode & NMI) != 0) {
                this.handleNmi();

                final short pc = this.pc;

                // push hi(pc), lo(pc), flags with break=0
                this.push(
                    hi(pc)
                );
                this.push(
                    lo(pc)
                );
                this.setBreak(false); // clear
                this.push(
                    this.flags()
                );

                this.setPc(
                    this.readAddress(NMI_VECTOR)
                );

                this.mode = mode & ~NMI;
            }

            if ((mode & IRQ) != 0) {
                // if interrupts are enabled push hi(pc), lo(pc), flags with break=0
                if (false == this.isInterruptDisabled()) {
                    final short pc = this.pc;

                    this.push(
                        hi(pc)
                    );
                    this.push(
                        lo(pc)
                    );
                    this.setBreak(false); // clear
                    this.push(
                        this.flags()
                    );

                    this.setPc(
                        this.readAddress(IRQ_VECTOR)
                    );

                    this.setInterruptDisabled(true); // disable interrupts
                    this.mode = mode & ~IRQ;
                }
            }
        }
    }

    private final static int NONE = 0;
    private final static int RESET = 1;
    private final static int NMI = 2;
    private final static int IRQ = 4;

    private int mode = NONE;

    @Override
    public void handleBreakpoints() {
        if (this.breakpoints.contains(this.pc)) {
            this.watchers.onBreakpoint(this);
        }
    }

    @Override
    public void handleInvalidOpcode() {
        this.watchers.onInvalidOpcode(this);
    }

    @Override
    public void handleNmi() {
        this.watchers.onNmi(this);
    }

    @Override
    public void handleReset() {
        this.watchers.onReset(this);
    }

    @Override
    public Runnable addBreakpoint(final short address) {
        this.breakpoints.add(address);

        return () -> this.breakpoints.remove(address);
    }

    private final Set<Short> breakpoints = SortedSets.tree();

    @Override
    public Runnable addWatcher(final CpuWatcher watcher) {
        return this.watchers.add(watcher);
    }

    private final CpuWatchers watchers = CpuWatchers.empty();

    // helpers..........................................................................................................

    private byte hi(final short value) {
        return (byte) (value >> 8);
    }

    private byte lo(final short value) {
        return (byte) (0xff & value);
    }

    private static int fromByte(final byte value) {
        return BYTE_MASK & value;
    }

    private static byte toByte(final int value) {
        return (byte) (BYTE_MASK & value);
    }

    // Object...........................................................................................................

    @Override
    public String toString() {
        return "PC: " + hexAddress(this.pc) +
            ", A: " + hex(this.a) +
            ", X: " + hex(this.x) +
            ", Y: " + hex(this.y) +
            ", SP: " + hex(this.stackPointer) +
            ", SR: " + this.flags;
    }

    private static String hex(final byte value) {
        return "0x" +
            CharSequences.padLeft(
                Integer.toHexString(BYTE_MASK & value)
                    .toUpperCase(),
                2,
                '0'
            );
    }

    private static String hexAddress(final short value) {
        return "0x" +
            CharSequences.padLeft(
                Integer.toHexString(0xFFFF & value)
                    .toUpperCase(),
                4,
                '0'
            );
    }
}
