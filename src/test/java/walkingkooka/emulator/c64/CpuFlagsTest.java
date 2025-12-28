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

import org.junit.jupiter.api.Test;
import walkingkooka.HashCodeEqualsDefinedTesting2;
import walkingkooka.ToStringTesting;
import walkingkooka.test.ParseStringTesting;

public final class CpuFlagsTest implements ParseStringTesting<CpuFlags>,
    HashCodeEqualsDefinedTesting2<CpuFlags>,
    ToStringTesting<CpuFlags> {

    // parseString......................................................................................................

    @Test
    public void testParseInvalidLetterFails() {
        this.parseStringInvalidCharacterFails(
            "CZ!DB10N",
            '!'
        );
    }

    @Test
    public void testParseAllDashes() {
        this.parseStringAndCheck(
            "--------",
            CpuFlags.create()
        );
    }

    @Test
    public void testParseCarry() {
        final CpuFlags expected = CpuFlags.create();
        expected.setCarry(true);

        this.parseStringAndCheck(
            "C-------",
            expected
        );
    }

    @Test
    public void testParseCarryAndZero() {
        final CpuFlags expected = CpuFlags.create();
        expected.setCarry(true);
        expected.setZero(true);

        this.parseStringAndCheck(
            "CZ------",
            expected
        );
    }

    @Test
    public void testParseAllLetters() {
        final CpuFlags expected = CpuFlags.create();
        expected.setValue((byte) 0xff);

        this.parseStringAndCheck(
            "CZIDB10N",
            expected
        );
    }

    @Override
    public CpuFlags parseString(final String text) {
        return CpuFlags.parse(text);
    }

    @Override
    public Class<? extends RuntimeException> parseStringFailedExpected(final Class<? extends RuntimeException> expected) {
        return expected;
    }

    @Override
    public RuntimeException parseStringFailedExpected(final RuntimeException expected) {
        return expected;
    }

    // hashCode/equals..................................................................................................

    @Test
    public void testEqualsDifferentBreak() {
        final CpuFlags flags = CpuFlags.create();
        flags.setBreak(true);

        this.checkNotEquals(flags);
    }

    @Test
    public void testEqualsDifferentCarry() {
        final CpuFlags flags = CpuFlags.create();
        flags.setCarry(true);

        this.checkNotEquals(flags);
    }

    @Test
    public void testEqualsDifferentDecimalMode() {
        final CpuFlags flags = CpuFlags.create();
        flags.setDecimalMode(true);

        this.checkNotEquals(flags);
    }

    @Test
    public void testEqualsDifferentInterruptDisabled() {
        final CpuFlags flags = CpuFlags.create();
        flags.setInterruptDisabled(true);

        this.checkNotEquals(flags);
    }

    @Test
    public void testEqualsDifferentMinus() {
        final CpuFlags flags = CpuFlags.create();
        flags.setMinus(true);

        this.checkNotEquals(flags);
    }

    @Test
    public void testEqualsDifferentOverflow() {
        final CpuFlags flags = CpuFlags.create();
        flags.setOverflow(true);

        this.checkNotEquals(flags);
    }

    @Test
    public void testEqualsDifferentZero() {
        final CpuFlags flags = CpuFlags.create();
        flags.setZero(true);

        this.checkNotEquals(flags);
    }

    @Override
    public CpuFlags createObject() {
        return CpuFlags.create();
    }

    // toString.........................................................................................................

    @Test
    public void testToStringCarry() {
        final CpuFlags context = CpuFlags.create();

        context.setValue((byte)0x1);

        this.toStringAndCheck(
            context,
            "C----1--"
        );
    }

    @Test
    public void testToStringZero() {
        final CpuFlags context = CpuFlags.create();

        context.setValue((byte)0x2);

        this.toStringAndCheck(
            context,
            "-Z---1--"
        );
    }

    @Test
    public void testToStringInterruptDisabled() {
        final CpuFlags context = CpuFlags.create();

        context.setValue((byte)0x4);

        this.toStringAndCheck(
            context,
            "--I--1--"
        );
    }

    @Test
    public void testToStringDecimalMode() {
        final CpuFlags context = CpuFlags.create();

        context.setValue((byte)0x8);

        this.toStringAndCheck(
            context,
            "---D-1--"
        );
    }

    @Test
    public void testToStringBreak() {
        final CpuFlags context = CpuFlags.create();

        context.setValue((byte)0x10);

        this.toStringAndCheck(
            context,
            "----B1--"
        );
    }

    @Test
    public void testToStringUnused() {
        final CpuFlags context = CpuFlags.create();

        context.setValue((byte)0x20);

        this.toStringAndCheck(
            context,
            "-----1--"
        );
    }

    @Test
    public void testToStringOverflow() {
        final CpuFlags context = CpuFlags.create();

        context.setValue((byte)0x40);

        this.toStringAndCheck(
            context,
            "-----1O-"
        );
    }

    @Test
    public void testToStringMinus() {
        final CpuFlags context = CpuFlags.create();

        context.setValue((byte)0x80);

        this.toStringAndCheck(
            context,
            "-----1-N"
        );
    }

    @Test
    public void testToString() {
        final CpuFlags context = CpuFlags.create();

        context.setBreak(true);
        context.setCarry(true);
        context.setDecimalMode(true);
        context.setInterruptDisabled(true);
        context.setMinus(true);
        context.setZero(true);


        this.toStringAndCheck(
            context,
            "CZIDB1-N"
        );
    }

    // class............................................................................................................

    @Override
    public Class<CpuFlags> type() {
        return CpuFlags.class;
    }
}
