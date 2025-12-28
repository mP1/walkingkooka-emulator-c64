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

import walkingkooka.text.printer.TreePrintableTesting;

public interface CpuContextTesting extends TreePrintableTesting {

    default void aAndCheck(final CpuContext context,
                           final byte expected) {
        this.checkEquals(
            expected,
            context.a(),
            context::toString
        );
    }

    default void setAAndCheck(final CpuContext context,
                              final byte expected) {
        context.setA(expected);
        this.aAndCheck(
            context,
            expected
        );
    }

    default void xAndCheck(final CpuContext context,
                           final byte expected) {
        this.checkEquals(
            expected,
            context.x(),
            context::toString
        );
    }

    default void setXAndCheck(final CpuContext context,
                              final byte expected) {
        context.setX(expected);
        this.xAndCheck(
            context,
            expected
        );
    }

    default void yAndCheck(final CpuContext context,
                           final byte expected) {
        this.checkEquals(
            expected,
            context.y(),
            context::toString
        );
    }

    default void setYAndCheck(final CpuContext context,
                              final byte expected) {
        context.setY(expected);
        this.yAndCheck(
            context,
            expected
        );
    }

    default void flagsAndCheck(final CpuContext context,
                               final byte expected) {
        this.checkEquals(
            expected,
            context.flags(),
            context::toString
        );
    }

    default void setFlagsAndCheck(final CpuContext context,
                                  final byte expected) {
        context.setFlags(expected);
        this.flagsAndCheck(
            context,
            expected
        );
    }

    default void stackPointerAndCheck(final CpuContext context,
                                      final byte expected) {
        this.checkEquals(
            expected,
            context.stackPointer(),
            context::toString
        );
    }

    default void setStackPointerAndCheck(final CpuContext context,
                                         final byte expected) {
        context.setStackPointer(expected);
        this.stackPointerAndCheck(
            context,
            expected
        );
    }

    default void pcAndCheck(final CpuContext context,
                            final short expected) {
        this.checkEquals(
            expected,
            context.pc(),
            context::toString
        );
    }

    default void setPCAndCheck(final CpuContext context,
                               final short expected) {
        context.setPc(expected);
        this.pcAndCheck(
            context,
            expected
        );
    }

    default void readByteAndCheck(final CpuContext context,
                                  final short address,
                                  final byte expected) {
        this.checkEquals(
            expected,
            context.readByte(address),
            () -> "readByte " + address
        );
    }

    default void readAddressAndCheck(final CpuContext context,
                                     final short address,
                                     final short expected) {
        this.checkEquals(
            expected,
            context.readAddress(address),
            () -> "readAddress " + address
        );
    }

    default void writeByteAndCheck(final CpuContext context,
                                   final short address,
                                   final byte value) {
        context.writeByte(
            address,
            value
        );
        this.readByteAndCheck(
            context,
            address,
            value
        );
    }

    default void readZeroPageByteAndCheck(final CpuContext context,
                                          final byte offset,
                                          final byte expected) {
        this.checkEquals(
            expected,
            context.readZeroPageByte(offset),
            () -> "readZeroPageByte " + offset
        );
    }

    default void writeZeroPageByteAndCheck(final CpuContext context,
                                           final byte offset,
                                           final byte value) {
        context.writeZeroPageByte(
            offset,
            value
        );
        this.readZeroPageByteAndCheck(
            context,
            offset,
            value
        );
    }

    default void readZeroPageAddressAndCheck(final CpuContext context,
                                             final byte offset,
                                             final short address) {
        this.checkEquals(
            address,
            context.readZeroPageAddress(offset),
            () -> "readZeroPageAddress " + offset
        );
    }

    default void popAndCheck(final CpuContext context,
                             final byte expected) {
        final byte stackPointer = context.stackPointer();

        this.readByteAndCheck(
            context,
            (short) (0x101 + (0xff & stackPointer)),
            expected
        );

        this.checkEquals(
            expected,
            context.pop(),
            () -> "pop"
        );

        this.stackPointerAndCheck(
            context,
            (byte) (stackPointer + 1)
        );
    }

    default void pushAndCheck(final CpuContext context,
                              final byte value) {
        final byte stackPointer = context.stackPointer();

        context.push(value);

        this.readByteAndCheck(
            context,
            (short) (0x100 + (0xff & stackPointer)),
            value
        );
        this.stackPointerAndCheck(
            context,
            (byte) (stackPointer - 1)
        );
    }
}
