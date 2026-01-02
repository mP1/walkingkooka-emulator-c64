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

public class FakeCpuContext implements CpuContext {
    
    public FakeCpuContext() {
        super();
    }

    @Override 
    public byte a() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setA(final byte a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte x() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setX(final byte x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte y() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setY(final byte y) {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte flags() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setFlags(final byte y) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isBreak() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setBreak(final boolean breakFlag) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isCarry() {
        throw new UnsupportedOperationException();
    }

    @Override 
    public void setCarry(final boolean carry) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isDecimalMode() {
        throw new UnsupportedOperationException();
    }

    @Override 
    public void setDecimalMode(final boolean decimalMode) {
        throw new UnsupportedOperationException();
    }

    @Override 
    public boolean isInterruptDisabled() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setInterruptDisabled(final boolean interruptDisabled) {
        throw new UnsupportedOperationException();
    }

    @Override 
    public boolean isMinus() {
        throw new UnsupportedOperationException();
    }

    @Override 
    public void setMinus(final boolean minus) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isOverflow() {
        throw new UnsupportedOperationException();
    }

    @Override 
    public void setOverflow(final boolean overflow) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isZero() {
        throw new UnsupportedOperationException();
    }

    @Override 
    public void setZero(final boolean zero) {
        throw new UnsupportedOperationException();
    }

    @Override 
    public byte stackPointer() {
        throw new UnsupportedOperationException();
    }

    @Override 
    public void setStackPointer(final byte stackPointer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public short pc() {
        throw new UnsupportedOperationException();
    }

    @Override 
    public void setPc(final short pc) {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte readByte(final short address) {
        throw new UnsupportedOperationException();
    }

    @Override 
    public void writeByte(final short address, 
                          final byte value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public short readAddress(final short address) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void writeAddress(final short address,
                             final short value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte readZeroPageByte(final byte offset) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void writeZeroPageByte(final byte offset,
                                  final byte value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public short readZeroPageAddress(final byte offset) {
        throw new UnsupportedOperationException();
    }

    @Override 
    public void push(final byte value) {
        throw new UnsupportedOperationException();
    }

    @Override 
    public byte pop() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void handleInterrupts() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void irq() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void nmi() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException();
    }
}
