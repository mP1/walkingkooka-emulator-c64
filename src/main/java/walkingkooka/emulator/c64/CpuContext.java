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

import walkingkooka.Context;

public interface CpuContext extends Context {

    byte a();

    void setA(final byte a);

    byte x();

    void setX(final byte x);

    byte y();

    void setY(final byte y);

    byte flags();

    void setFlags(final byte y);

    boolean isBreak();

    void setBreak(final boolean breakFlag);

    boolean isCarry();

    void setCarry(final boolean carry);

    boolean isDecimalMode();

    void setDecimalMode(final boolean decimalMode);

    boolean isInterruptDisabled();

    void setInterruptDisabled(final boolean interruptDisabled);

    boolean isMinus();

    void setMinus(final boolean minus);

    boolean isOverflow();

    void setOverflow(final boolean overflow);

    boolean isZero();

    void setZero(final boolean zero);

    byte stackPointer();

    void setStackPointer(final byte stackPointer);

    short pc();

    void setPc(final short pc);

    byte readByte(final short address);

    void writeByte(final short address,
                   final byte value);

    short readAddress(final short address);

    void writeAddress(final short address,
                      final short value);

    byte readZeroPageByte(final byte offset);

    void writeZeroPageByte(final byte offset,
                           final byte value);

    short readZeroPageAddress(final byte offset);

    void push(final byte value);

    byte pop();

    short NMI_VECTOR = (short) 0xfffa;

    short RESET_VECTOR = (short) 0xfffc;

    short IRQ_VECTOR = (short) 0xfffe;

    void reset();

    void nmi();

    void irq();

    void handleInterrupts();

    void handleInvalidOpcode();

    void handleNmi();

    void handleBreakpoints();

    Runnable addBreakpoint(final short address);

    Runnable addWatcher(final CpuWatcher watcher);
}
