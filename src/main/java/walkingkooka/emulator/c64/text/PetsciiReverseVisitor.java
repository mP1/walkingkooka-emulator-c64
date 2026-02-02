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
 * A {@link Visitor} that may be used to translate unicode back to PETSCII.
 */
public class PetsciiReverseVisitor extends Visitor<Character> implements PetsciiConstants {

    public PetsciiReverseVisitor() {
        super();
    }

    @Override
    public void accept(final Character value) {
        Objects.requireNonNull(value, "value");

        final char c = value.charValue();

        if (Ascii.isLetter(c)) {
            this.visitCharacter(c);
        } else {
            switch (c) {
                case KEY_NA:
                    this.visitNull();
                    break;
                case '\r':
                case '\n':
                    this.visitCharacter(KEY_RETURN);
                    break;
                case KEY_SPACE:
                case KEY_EXCLAMATION:
                case KEY_QUOTE:
                case KEY_HASH:
                case KEY_DOLLAR:
                case KEY_PERCENT:
                case KEY_AMPERSAND:
                case KEY_APOSTROPHE:
                case KEY_R_BRACKET_L:
                case KEY_R_BRACKET_R:
                case KEY_ASTERISK:
                case KEY_PLUS:
                case KEY_COMA:
                case KEY_MINUS:
                case KEY_FULLSTOP:
                case KEY_SLASH:
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
                case KEY_COLON:
                case KEY_SEMICOLON:
                case KEY_LT:
                case KEY_EQ:
                case KEY_GT:
                case KEY_QUESTION:
                    this.visitCharacter(c);
                    break;
                default:
                    this.visitInvalidCharacter(c);
                    break;
            }
        }
    }

    protected void visitNull() {

    }

    private void visitCharacter(final char value) {
        this.visitCharacter(
            (byte) value
        );
    }

    protected void visitCharacter(final byte value) {
        // NOP
    }

    protected void visitInvalidCharacter(final char value) {

    }
}
