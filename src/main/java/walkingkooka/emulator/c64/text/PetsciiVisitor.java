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

package walkingkooka.emulator.c64.text;

import walkingkooka.text.Ascii;
import walkingkooka.visit.Visitor;

import java.util.Objects;

/**
 * All constants below are taken from the MEGA65 project.
 * <pre>
 * https://github.com/MEGA65/open-roms/blob/master/src/aliases/%2Caliases_petscii_codes.s
 * https://www.c64-wiki.com/wiki/PETSCII
 * </pre>
 */
public class PetsciiVisitor extends Visitor<Byte> {

    protected PetsciiVisitor() {
        super();
    }

    private final byte KEY_NA = (byte) 0;  // to indicate that no key is presed

    // PETSCII codes for programmable keys

    private final byte KEY_STOP = (byte) 0x03;
    private final byte KEY_RUN = (byte) 0x83;

    // PETSCII codes for cursor keys

    private final byte KEY_CRSR_UP = (byte) 0x91;
    private final byte KEY_CRSR_DOWN = (byte) 0x11;
    private final byte KEY_CRSR_LEFT = (byte) 0x9D;
    private final byte KEY_CRSR_RIGHT = (byte) 0x1D;

    // PETSCII codes for colors

    private final byte KEY_RVS_ON = (byte) 0x12;// CTRL+9;
    private final byte KEY_RVS_OFF = (byte) 0x92;// CTRL+0;
    private final byte KEY_BLACK = (byte) 0x90;// CTRL+1;
    private final byte KEY_WHITE = (byte) 0x05; // CTRL+2;
    private final byte KEY_RED = (byte) 0x1C; // CTRL+3;
    private final byte KEY_CYAN = (byte) 0x9F; // CTRL+4;
    private final byte KEY_PURPLE = (byte) 0x9C; // CTRL+5;
    private final byte KEY_GREEN = (byte) 0x1E; // CTRL+6;
    private final byte KEY_BLUE = (byte) 0x1F; // CTRL+7;
    private final byte KEY_YELLOW = (byte) 0x9E; // CTRL+8;

    private final byte KEY_ORANGE = (byte) 0x81;// VENDOR+1;
    private final byte KEY_BROWN = (byte) 0x95;// VENDOR+2;
    private final byte KEY_LT_RED = (byte) 0x96;// VENDOR+3;
    private final byte KEY_GREY_1 = (byte) 0x97;// VENDOR+4;
    private final byte KEY_GREY_2 = (byte) 0x98;// VENDOR+5;
    private final byte KEY_LT_GREEN = (byte) 0x99;// VENDOR+6;
    private final byte KEY_LT_BLUE = (byte) 0x9A;// VENDOR+7;
    private final byte KEY_GREY_3 = (byte) 0x9B;// VENDOR+8;

    // PETSCII codes for other non-printable characters
    private final byte KEY_RETURN = (byte) 0x0D;
    private final byte KEY_SHIFT_RETURN = (byte) 0x8D;
    private final byte KEY_CLR = (byte) 0x93;
    private final byte KEY_HOME = (byte) 0x13;
    private final byte KEY_INS = (byte) 0x94;
    private final byte KEY_DEL = (byte) 0x14;

    // PETSCII codes for printable characters

    private final byte KEY_SPACE = (byte) 0x20;
    private final byte KEY_EXCLAMATION = (byte) 0x21;
    private final byte KEY_QUOTE = (byte) 0x22;
    private final byte KEY_HASH = (byte) 0x23;
    private final byte KEY_DOLLAR = (byte) 0x24;
    private final byte KEY_PERCENT = (byte) 0x25;
    private final byte KEY_AMPERSAND = (byte) 0x26;
    private final byte KEY_APOSTROPHE = (byte) 0x27;
    private final byte KEY_R_BRACKET_L = (byte) 0x28;
    private final byte KEY_R_BRACKET_R = (byte) 0x29;
    private final byte KEY_ASTERISK = (byte) 0x2A;
    private final byte KEY_PLUS = (byte) 0x2B;
    private final byte KEY_COMA = (byte) 0x2C;
    private final byte KEY_MINUS = (byte) 0x2D;
    private final byte KEY_FULLSTOP = (byte) 0x2E;
    private final byte KEY_SLASH = (byte) 0x2F;

    private final byte KEY_0 = (byte) 0x30;
    private final byte KEY_1 = (byte) 0x31;
    private final byte KEY_2 = (byte) 0x32;
    private final byte KEY_3 = (byte) 0x33;
    private final byte KEY_4 = (byte) 0x34;
    private final byte KEY_5 = (byte) 0x35;
    private final byte KEY_6 = (byte) 0x36;
    private final byte KEY_7 = (byte) 0x37;
    private final byte KEY_8 = (byte) 0x38;
    private final byte KEY_9 = (byte) 0x39;
    private final byte KEY_COLON = (byte) 0x3A;
    private final byte KEY_SEMICOLON = (byte) 0x3B;
    private final byte KEY_LT = (byte) 0x3C;
    private final byte KEY_EQ = (byte) 0x3D;
    private final byte KEY_GT = (byte) 0x3E;
    private final byte KEY_QUESTION = (byte) 0x3F;

    // XXX finish this: $4x, $5x, $6x, $7x, $Ax, $Bx, $Cx, $Dx, $Ex, $Fx

    @Override
    public void accept(final Byte value) {
        Objects.requireNonNull(value, "value");

        final char c = (char) (0xff & value);

        if (Ascii.isLetter(c)) {
            // petscii lower case is actually mapped to ascii upper and vice versa
            if (Character.isLowerCase(c)) {
                this.visitCharacter(
                    Character.toUpperCase(c)
                );
            } else {
                this.visitCharacter(
                    Character.toLowerCase(c)
                );
            }
        } else {
            switch (value) {
                case KEY_NA:
                    this.visitNoKey();
                    break;
                case KEY_STOP:
                    this.visitStop();
                    break;
                case KEY_RUN:
                    this.visitRun();
                    break;
                case KEY_CRSR_UP:
                    this.visitCursorUp();
                    break;
                case KEY_CRSR_DOWN:
                    this.visitCursorDown();
                    break;
                case KEY_CRSR_LEFT:
                    this.visitCursorLeft();
                    break;
                case KEY_CRSR_RIGHT:
                    this.visitCursorRight();
                    break;
                case KEY_RVS_ON:
                    this.visitReverseOn();
                    break;
                case KEY_RVS_OFF:
                    this.visitReverseOff();
                    break;
                case KEY_BLACK:
                    this.visitBlack();
                    break;
                case KEY_WHITE:
                    this.visitWhite();
                    break;
                case KEY_RED:
                    this.visitRed();
                    break;
                case KEY_CYAN:
                    this.visitCyan();
                    break;
                case KEY_PURPLE:
                    this.visitPurple();
                    break;
                case KEY_GREEN:
                    this.visitGreen();
                    break;
                case KEY_BLUE:
                    this.visitBlue();
                    break;
                case KEY_YELLOW:
                    this.visitYellow();
                    break;
                case KEY_ORANGE:
                    this.visitOrange();
                    break;
                case KEY_BROWN:
                    this.visitBrown();
                    break;
                case KEY_LT_RED:
                    this.visitLightRed();
                    break;
                case KEY_GREY_1:
                    this.visitDarkGrey();
                    break;
                case KEY_GREY_2:
                    this.visitMediumGrey();
                    break;
                case KEY_LT_GREEN:
                    this.visitLightGreen();
                    break;
                case KEY_LT_BLUE:
                    this.visitLightBlue();
                    break;
                case KEY_GREY_3:
                    this.visitLightGrey();
                    break;
                case KEY_RETURN:
                    this.visitReturn();
                    break;
                case KEY_SHIFT_RETURN:
                    this.visitShiftReturn();
                    break;
                case KEY_CLR:
                    this.visitClear();
                    break;
                case KEY_HOME:
                    this.visitHome();
                    break;
                case KEY_INS:
                    this.visitInsert();
                    break;
                case KEY_DEL:
                    this.visitDelete();
                    break;
                case KEY_SPACE:
                    this.visitCharacter(' ');
                    break;
                case KEY_EXCLAMATION:
                    this.visitCharacter('!');
                    break;
                case KEY_QUOTE:
                    this.visitCharacter('"');
                    break;
                case KEY_HASH:
                    this.visitCharacter('#');
                    break;
                case KEY_DOLLAR:
                    this.visitCharacter('$');
                    break;
                case KEY_PERCENT:
                    this.visitCharacter('%');
                    break;
                case KEY_AMPERSAND:
                    this.visitCharacter('&');
                    break;
                case KEY_APOSTROPHE:
                    this.visitCharacter('\'');
                    break;
                case KEY_R_BRACKET_L:
                    this.visitCharacter('(');
                    break;
                case KEY_R_BRACKET_R:
                    this.visitCharacter(')');
                    break;
                case KEY_ASTERISK:
                    this.visitCharacter('*');
                    break;
                case KEY_PLUS:
                    this.visitCharacter('+');
                    break;
                case KEY_COMA:
                    this.visitCharacter(',');
                    break;
                case KEY_MINUS:
                    this.visitCharacter('-');
                    break;
                case KEY_FULLSTOP:
                    this.visitCharacter('.');
                    break;
                case KEY_SLASH:
                    this.visitCharacter('/');
                    break;
                case KEY_0:
                case KEY_1:
                case KEY_2:
                case KEY_3:
                case KEY_4:
                case KEY_5:
                case KEY_6:
                case KEY_7:
                case KEY_8:
                case KEY_9:
                    this.visitCharacter(
                        (char) ('0' + value - KEY_0)
                    );
                    break;
                case KEY_COLON:
                    this.visitCharacter(':');
                    break;
                case KEY_SEMICOLON:
                    this.visitCharacter(';');
                    break;
                case KEY_LT:
                    this.visitCharacter('<');
                    break;
                case KEY_EQ:
                    this.visitCharacter('=');
                    break;
                case KEY_GT:
                    this.visitCharacter('>');
                    break;
                case KEY_QUESTION:
                    this.visitCharacter('?');
                    break;
                default:
                    this.visitUnknown(value);
                    break;
            }
        }
    }

    protected void visitNoKey() {

    }

    protected void visitStop() {

    }

    protected void visitRun() {

    }

    protected void visitCursorUp() {

    }

    protected void visitCursorDown() {

    }

    protected void visitCursorLeft() {

    }

    protected void visitCursorRight() {

    }

    protected void visitReverseOn() {

    }

    protected void visitReverseOff() {

    }

    protected void visitBlack() {

    }

    protected void visitWhite() {

    }

    protected void visitRed() {

    }

    protected void visitCyan() {

    }

    protected void visitPurple() {

    }

    protected void visitGreen() {

    }

    protected void visitBlue() {

    }

    protected void visitYellow() {

    }

    protected void visitOrange() {

    }

    protected void visitBrown() {

    }

    protected void visitLightRed() {

    }

    protected void visitDarkGrey() {

    }

    protected void visitMediumGrey() {

    }

    protected void visitLightGreen() {

    }

    protected void visitLightBlue() {

    }

    protected void visitLightGrey() {

    }

    protected void visitReturn() {

    }

    protected void visitShiftReturn() {

    }

    protected void visitClear() {

    }

    protected void visitHome() {

    }

    protected void visitInsert() {

    }

    protected void visitDelete() {

    }

    protected void visitCharacter(final char c) {

    }

    protected void visitUnknown(final byte value) {

    }
}
