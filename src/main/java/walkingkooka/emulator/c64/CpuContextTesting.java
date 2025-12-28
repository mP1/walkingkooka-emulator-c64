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

import walkingkooka.test.Testing;

public interface CpuContextTesting extends Testing {

    default void aAndCheck(final CpuContext context,
                           final byte value) {
        this.checkEquals(
            context.a(),
            value,
            context::toString
        );
    }

    default void setAAndCheck(final CpuContext context,
                              final byte value) {
        context.setA(value);
        this.aAndCheck(
            context,
            value
        );
    }

    default void xAndCheck(final CpuContext context,
                           final byte value) {
        this.checkEquals(
            context.x(),
            value,
            context::toString
        );
    }

    default void setXAndCheck(final CpuContext context,
                              final byte value) {
        context.setX(value);
        this.xAndCheck(
            context,
            value
        );
    }

    default void yAndCheck(final CpuContext context,
                           final byte value) {
        this.checkEquals(
            context.y(),
            value,
            context::toString
        );
    }

    default void setYAndCheck(final CpuContext context,
                              final byte value) {
        context.setY(value);
        this.yAndCheck(
            context,
            value
        );
    }

    default void flagsAndCheck(final CpuContext context,
                               final byte value) {
        this.checkEquals(
            context.flags(),
            value,
            context::toString
        );
    }

    default void setFlagsAndCheck(final CpuContext context,
                                  final byte value) {
        context.setFlags(value);
        this.flagsAndCheck(
            context,
            value
        );
    }

    default void stackPointerAndCheck(final CpuContext context,
                                      final byte value) {
        this.checkEquals(
            context.stackPointer(),
            value,
            context::toString
        );
    }

    default void setStackPointerAndCheck(final CpuContext context,
                                         final byte value) {
        context.setStackPointer(value);
        this.stackPointerAndCheck(
            context,
            value
        );
    }

    default void pcAndCheck(final CpuContext context,
                            final short value) {
        this.checkEquals(
            context.pc(),
            value,
            context::toString
        );
    }

    default void setPCAndCheck(final CpuContext context,
                               final short value) {
        context.setPc(value);
        this.pcAndCheck(
            context,
            value
        );
    }

    default void readByteAndCheck(final CpuContext context,
                                  final short address,
                                  final byte value) {
        this.checkEquals(
            value,
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
                                          final byte value) {
        this.checkEquals(
            value,
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
                             final byte value) {
        this.checkEquals(
            value,
            context.pop(),
            () -> "pop"
        );
    }

    default void pushAndCheck(final CpuContext context,
                              final byte value) {
        context.push(
            value
        );
    }
}
