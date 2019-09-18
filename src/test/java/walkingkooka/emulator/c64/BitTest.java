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
import walkingkooka.test.ClassTesting2;
import walkingkooka.text.CharSequences;
import walkingkooka.type.JavaVisibility;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class BitTest implements ClassTesting2<Bit> {

    // read.............................................................................................................

    @Test
    public void testRead0True() {
        readAndCheck(Bit.BIT0, (byte) (1 << 0), true);
    }

    @Test
    public void testRead0False() {
        readAndCheck(Bit.BIT0, (byte) ~(1 << 0), false);
    }

    @Test
    public void testRead1True() {
        readAndCheck(Bit.BIT1, (byte) (1 << 1), true);
    }

    @Test
    public void testRead1False() {
        readAndCheck(Bit.BIT1, (byte) ~(1 << 1), false);
    }

    @Test
    public void testRead2True() {
        readAndCheck(Bit.BIT2, (byte) (1 << 2), true);
    }

    @Test
    public void testRead2False() {
        readAndCheck(Bit.BIT2, (byte) ~(1 << 2), false);
    }

    @Test
    public void testRead3True() {
        readAndCheck(Bit.BIT3, (byte) (1 << 3), true);
    }

    @Test
    public void testRead3False() {
        readAndCheck(Bit.BIT3, (byte) ~(1 << 3), false);
    }

    @Test
    public void testRead4True() {
        readAndCheck(Bit.BIT4, (byte) (1 << 4), true);
    }

    @Test
    public void testRead4False() {
        readAndCheck(Bit.BIT4, (byte) ~(1 << 4), false);
    }

    @Test
    public void testRead5True() {
        readAndCheck(Bit.BIT5, (byte) (1 << 5), true);
    }

    @Test
    public void testRead5False() {
        readAndCheck(Bit.BIT5, (byte) ~(1 << 5), false);
    }

    @Test
    public void testRead6True() {
        readAndCheck(Bit.BIT6, (byte) (1 << 6), true);
    }

    @Test
    public void testRead6False() {
        readAndCheck(Bit.BIT6, (byte) ~(1 << 6), false);
    }

    @Test
    public void testRead7True() {
        readAndCheck(Bit.BIT7, (byte) (1 << 7), true);
    }

    @Test
    public void testRead7False() {
        readAndCheck(Bit.BIT7, (byte) ~(1 << 7), false);
    }

    private void readAndCheck(final Bit bit, final byte value, final boolean expected) {
        assertEquals(expected,
                bit.read(value),
                () -> bit + " read " + Integer.toHexString(value));
    }

    // not.............................................................................................................

    @Test
    public void testNot0True() {
        notAndCheck(Bit.BIT0, (byte) ~(1 << 0));
    }

    @Test
    public void testNot1True() {
        notAndCheck(Bit.BIT1, (byte) ~(1 << 1));
    }

    @Test
    public void testNot2True() {
        notAndCheck(Bit.BIT2, (byte) ~(1 << 2));
    }
    
    @Test
    public void testNot3True() {
        notAndCheck(Bit.BIT3, (byte) ~(1 << 3));
    }

    @Test
    public void testNot4True() {
        notAndCheck(Bit.BIT4, (byte) ~(1 << 4));
    }

    @Test
    public void testNot5True() {
        notAndCheck(Bit.BIT5, (byte) ~(1 << 5));
    }

    @Test
    public void testNot6True() {
        notAndCheck(Bit.BIT6, (byte) ~(1 << 6));
    }

    @Test
    public void testNot7True() {
        notAndCheck(Bit.BIT7, (byte) ~(1 << 7));
    }

    private void notAndCheck(final Bit bit, final byte expected) {
        assertEquals(expected,
                bit.not(),
                () -> bit + " not " + Integer.toHexString(expected));
    }

    // set.............................................................................................................

    @Test
    public void testSet0() {
        setAndCheck(Bit.BIT0, 1 << 0);
    }

    @Test
    public void testSet1() {
        setAndCheck(Bit.BIT1, 1 << 1);
    }

    @Test
    public void testSet2() {
        setAndCheck(Bit.BIT2, 1 << 2);
    }

    @Test
    public void testSet3() {
        setAndCheck(Bit.BIT3, 1 << 3);
    }

    @Test
    public void testSet4() {
        setAndCheck(Bit.BIT4, 1 << 4);
    }

    @Test
    public void testSet5() {
        setAndCheck(Bit.BIT5, 1 << 5);
    }

    @Test
    public void testSet6() {
        setAndCheck(Bit.BIT6, 1 << 6);
    }

    @Test
    public void testSet7() {
        setAndCheck(Bit.BIT7, 1 << 7);
    }

    private void setAndCheck(final Bit bit,
                             final int expected) {
        assertEquals((byte) expected,
                bit.set(),
                () -> bit + " set");
    }

    // set.............................................................................................................

    @Test
    public void testSetByte0True() {
        setByteAndCheck(Bit.BIT0, 1 << 0);
    }

    @Test
    public void testSetByte0False() {
        setByteAndCheck(Bit.BIT0, ~(1 << 0), 0xff);
    }

    @Test
    public void testSetByte1True() {
        setByteAndCheck(Bit.BIT1, 1 << 1);
    }

    @Test
    public void testSetByte1False() {
        setByteAndCheck(Bit.BIT1, ~(1 << 1), 0xff);
    }

    @Test
    public void testSetByte2True() {
        setByteAndCheck(Bit.BIT2, 1 << 2);
    }

    @Test
    public void testSetByte2False() {
        setByteAndCheck(Bit.BIT2, ~(1 << 2), 0xff);
    }

    @Test
    public void testSetByte3True() {
        setByteAndCheck(Bit.BIT3, 1 << 3);
    }

    @Test
    public void testSetByte3False() {
        setByteAndCheck(Bit.BIT3, ~(1 << 3), 0xff);
    }

    @Test
    public void testSetByte4True() {
        setByteAndCheck(Bit.BIT4, 1 << 4);
    }

    @Test
    public void testSetByte4False() {
        setByteAndCheck(Bit.BIT4, ~(1 << 4), 0xff);
    }

    @Test
    public void testSetByte5True() {
        setByteAndCheck(Bit.BIT5, 1 << 5);
    }

    @Test
    public void testSetByte5False() {
        setByteAndCheck(Bit.BIT5, ~(1 << 5), 0xff);
    }

    @Test
    public void testSetByte6True() {
        setByteAndCheck(Bit.BIT6, 1 << 6);
    }

    @Test
    public void testSetByte6False() {
        setByteAndCheck(Bit.BIT6, ~(1 << 6), 0xff);
    }

    @Test
    public void testSetByte7True() {
        setByteAndCheck(Bit.BIT7, 1 << 7);
    }

    @Test
    public void testSetByte7False() {
        setByteAndCheck(Bit.BIT7, ~(1 << 7), 0xff);
    }

    private void setByteAndCheck(final Bit bit, final int value) {
        this.setByteAndCheck(bit, value, value);
    }

    private void setByteAndCheck(final Bit bit, final int value, final int expected) {
        assertEquals((byte) expected,
                bit.set((byte) value),
                () -> bit + " set " + Integer.toHexString(value));
    }

    // clear.............................................................................................................

    @Test
    public void testClear0True() {
        clearAndCheck(Bit.BIT0, 0xff, ~(1 << 0));
    }

    @Test
    public void testClear0False() {
        clearAndCheck(Bit.BIT0, 0);
    }

    @Test
    public void testClear1True() {
        clearAndCheck(Bit.BIT1, 0xff, ~(1 << 1));
    }

    @Test
    public void testClear1False() {
        clearAndCheck(Bit.BIT1, ~(1 << 1));
    }

    @Test
    public void testClear2True() {
        clearAndCheck(Bit.BIT2, 0xff, ~(1 << 2));
    }

    @Test
    public void testClear2False() {
        clearAndCheck(Bit.BIT2, 0, 0);
    }

    @Test
    public void testClear3True() {
        clearAndCheck(Bit.BIT3, 0xff, ~(1 << 3));
    }

    @Test
    public void testClear3False() {
        clearAndCheck(Bit.BIT3, 0);
    }

    @Test
    public void testClear4True() {
        clearAndCheck(Bit.BIT4, 0xff, ~(1 << 4));
    }

    @Test
    public void testClear4False() {
        clearAndCheck(Bit.BIT4, 0);
    }

    @Test
    public void testClear5True() {
        clearAndCheck(Bit.BIT5, 0xff, ~(1 << 5));
    }

    @Test
    public void testClear5False() {
        clearAndCheck(Bit.BIT5, 0);
    }

    @Test
    public void testClear6True() {
        clearAndCheck(Bit.BIT6, 0xff, ~(1 << 6));
    }

    @Test
    public void testClear6False() {
        clearAndCheck(Bit.BIT6, 0);
    }

    @Test
    public void testClear7True() {
        clearAndCheck(Bit.BIT7, 0xff, ~(1 << 7));
    }

    @Test
    public void testClear7False() {
        clearAndCheck(Bit.BIT7, 0);
    }

    private void clearAndCheck(final Bit bit, final int value) {
        clearAndCheck(bit, value, value);
    }

    private void clearAndCheck(final Bit bit, final int value, final int expected) {
        assertEquals((byte) expected,
                bit.clear((byte) value),
                () -> bit + " clear " + Integer.toHexString(value));
    }

    // or...............................................................................................................

    @Test
    public void testOr() {
        orAndCheck(Bit.BIT0,
                Bit.BIT1,
                0x3);
    }

    @Test
    public void testOr2() {
        orAndCheck(Bit.BIT0,
                Bit.BIT1,
                new Bit[]{Bit.BIT2},
                0x7);
    }

    @Test
    public void testOr3() {
        orAndCheck(Bit.BIT0,
                Bit.BIT1,
                new Bit[]{Bit.BIT2, Bit.BIT3},
                0xf);
    }

    @Test
    public void testOr4() {
        orAndCheck(Bit.BIT0,
                Bit.BIT1,
                new Bit[]{Bit.BIT2, Bit.BIT3, Bit.BIT4},
                0x1f);
    }

    private void orAndCheck(final Bit bit,
                            final Bit param0,
                            final int expected) {
        this.orAndCheck(bit, param0, new Bit[0], expected);
    }

    private void orAndCheck(final Bit bit,
                            final Bit param0,
                            final Bit[] params,
                            final int expected) {
        assertEquals((byte) expected,
                bit.or(param0, params),
                () -> bit + " or " + bit + ", " + Arrays.toString(params));
    }

    // or...............................................................................................................

    @Test
    public void testAndNot() {
        andNotAndCheck(Bit.BIT0,
                Bit.BIT0,
                0xfe);
    }

    @Test
    public void testAndNot2() {
        andNotAndCheck(Bit.BIT0,
                Bit.BIT1,
                0xfc);
    }

    @Test
    public void testAndNot3() {
        andNotAndCheck(Bit.BIT0,
                Bit.BIT1,
                new Bit[]{Bit.BIT2},
                0xf8);
    }

    @Test
    public void testAndNot4() {
        andNotAndCheck(Bit.BIT0,
                Bit.BIT1,
                new Bit[]{Bit.BIT2, Bit.BIT3},
                0xf0);
    }

    @Test
    public void testAndNot5() {
        andNotAndCheck(Bit.BIT0,
                Bit.BIT1,
                new Bit[]{Bit.BIT2, Bit.BIT3, Bit.BIT4},
                0xe0);
    }

    @Test
    public void testAndNot6() {
        andNotAndCheck(Bit.BIT7,
                Bit.BIT6,
                0x3f);
    }

    private void andNotAndCheck(final Bit bit,
                                final Bit param0,
                                final int expected) {
        this.andNotAndCheck(bit, param0, new Bit[0], expected);
    }

    private void andNotAndCheck(final Bit bit,
                                final Bit param0,
                                final Bit[] params,
                                final int expected) {
        assertEquals((byte) expected,
                bit.andNot(param0, params),
                () -> bit + " andNot " + bit + ", " + Arrays.toString(params));
    }

    // text.........................................................................................................

    @Test
    public void testTextTrue() {
        this.textAndCheck2(Bit.BIT0,
                Bit.BIT0.set(),
                "MAGIC",
                "MAGIC");
    }

    @Test
    public void testTextTrue2() {
        this.textAndCheck2(Bit.BIT0,
                Bit.BIT0.or(Bit.BIT1),
                "MAGIC",
                "MAGIC");
    }

    @Test
    public void testTextFalse() {
        this.textAndCheck2(Bit.BIT0,
                Bit.BIT1.set(),
                "MAGIC",
                "");
    }

    @Test
    public void testTextFalse2() {
        this.textAndCheck2(Bit.BIT7,
                (byte) 0X7F,
                "MAGIC",
                "");
    }

    private void textAndCheck2(final Bit bit,
                                   final byte value,
                                   final String text,
                                   final String expected) {
        assertEquals(expected,
                bit.text(value, text),
                () -> bit + " text " + Integer.toHexString(value) + " " + CharSequences.quoteAndEscape(text));
    }

    // byteText..........................................................................................................

    @Test
    public void testByteTextZero() {
        this.byteTextAndCheck2((byte) 0,
                "");
    }

    @Test
    public void testByteText0xf0() {
        this.byteTextAndCheck2((byte) 0xf0,
                "A B C D");
    }

    @Test
    public void testByteText0x09() {
        this.byteTextAndCheck2((byte) 0x09,
                "E H");
    }

    @Test
    public void testByteText255() {
        this.byteTextAndCheck2((byte) 255,
                "A B C D E F G H");
    }

    @Test
    public void testByteText3() {
        this.byteTextAndCheck2((byte) 0x83,
                "7",
                "6",
                "5",
                "4",
                "3",
                "2",
                "1",
                "0",
                "7 1 0");
    }

    private void byteTextAndCheck2(final byte value,
                                   final String expected) {
        byteTextAndCheck2(value,
                "A",
                "B",
                "C",
                "D",
                "E",
                "F",
                "G",
                "H",
                expected);
    }

    private void byteTextAndCheck2(final byte value,
                                   final String text7,
                                   final String text6,
                                   final String text5,
                                   final String text4,
                                   final String text3,
                                   final String text2,
                                   final String text1,
                                   final String text0,
                                   final String expected) {
        assertEquals(expected,
                Bit.byteText(value,
                        text7,
                        text6,
                        text5,
                        text4,
                        text3,
                        text2,
                        text1,
                        text0),
                () -> "Bit.byteText " + Integer.toHexString(value) + " " +
                        CharSequences.quoteAndEscape(text7) + ", " +
                        CharSequences.quoteAndEscape(text6) + ", " +
                        CharSequences.quoteAndEscape(text5) + ", " +
                        CharSequences.quoteAndEscape(text4) + ", " +
                        CharSequences.quoteAndEscape(text3) + ", " +
                        CharSequences.quoteAndEscape(text2) + ", " +
                        CharSequences.quoteAndEscape(text1) + ", " +
                        CharSequences.quoteAndEscape(text0));
    }

    // of...............................................................................................................

    @Test
    public void testOf() {
        Arrays.stream(Bit.values())
                .forEach((b) -> {
                    final int number = b.number();
                    assertEquals(b, Bit.of(number), () -> "bit number " + number);
                });
    }

    // ClassTesting.....................................................................................................

    @Override
    public Class<Bit> type() {
        return Bit.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }
}
