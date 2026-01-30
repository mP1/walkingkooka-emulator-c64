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
 * Translates petscii character codes which contain many ascii character codes and other codes for C64 specific functionality,
 * like changing the color.
 * <br>
 * All constants below are taken from the MEGA65 project.
 * <pre>
 * https://github.com/MEGA65/open-roms/blob/master/src/aliases/%2Caliases_petscii_codes.s
 * https://www.c64-wiki.com/wiki/PETSCII
 * </pre>
 */
public class PetsciiVisitor extends Visitor<Byte> implements PetsciiConstants {

    protected PetsciiVisitor() {
        super();
    }

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
