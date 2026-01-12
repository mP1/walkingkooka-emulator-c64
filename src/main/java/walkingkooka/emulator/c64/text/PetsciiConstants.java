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

interface PetsciiConstants {

    byte KEY_NA = (byte) 0;  // to indicate that no key is presed

    // PETSCII codes for programmable keys

    byte KEY_STOP = (byte) 0x03;
    byte KEY_RUN = (byte) 0x83;

    // PETSCII codes for cursor keys

    byte KEY_CRSR_UP = (byte) 0x91;
    byte KEY_CRSR_DOWN = (byte) 0x11;
    byte KEY_CRSR_LEFT = (byte) 0x9D;
    byte KEY_CRSR_RIGHT = (byte) 0x1D;

    // PETSCII codes for colors

    byte KEY_RVS_ON = (byte) 0x12;// CTRL+9;
    byte KEY_RVS_OFF = (byte) 0x92;// CTRL+0;
    byte KEY_BLACK = (byte) 0x90;// CTRL+1;
    byte KEY_WHITE = (byte) 0x05; // CTRL+2;
    byte KEY_RED = (byte) 0x1C; // CTRL+3;
    byte KEY_CYAN = (byte) 0x9F; // CTRL+4;
    byte KEY_PURPLE = (byte) 0x9C; // CTRL+5;
    byte KEY_GREEN = (byte) 0x1E; // CTRL+6;
    byte KEY_BLUE = (byte) 0x1F; // CTRL+7;
    byte KEY_YELLOW = (byte) 0x9E; // CTRL+8;

    byte KEY_ORANGE = (byte) 0x81;// VENDOR+1;
    byte KEY_BROWN = (byte) 0x95;// VENDOR+2;
    byte KEY_LT_RED = (byte) 0x96;// VENDOR+3;
    byte KEY_GREY_1 = (byte) 0x97;// VENDOR+4;
    byte KEY_GREY_2 = (byte) 0x98;// VENDOR+5;
    byte KEY_LT_GREEN = (byte) 0x99;// VENDOR+6;
    byte KEY_LT_BLUE = (byte) 0x9A;// VENDOR+7;
    byte KEY_GREY_3 = (byte) 0x9B;// VENDOR+8;

    // PETSCII codes for other non-printable characters
    byte KEY_RETURN = (byte) 0x0D;
    byte KEY_SHIFT_RETURN = (byte) 0x8D;
    byte KEY_CLR = (byte) 0x93;
    byte KEY_HOME = (byte) 0x13;
    byte KEY_INS = (byte) 0x94;
    byte KEY_DEL = (byte) 0x14;

    // PETSCII codes for printable characters

    byte KEY_SPACE = (byte) 0x20;
    byte KEY_EXCLAMATION = (byte) 0x21;
    byte KEY_QUOTE = (byte) 0x22;
    byte KEY_HASH = (byte) 0x23;
    byte KEY_DOLLAR = (byte) 0x24;
    byte KEY_PERCENT = (byte) 0x25;
    byte KEY_AMPERSAND = (byte) 0x26;
    byte KEY_APOSTROPHE = (byte) 0x27;
    byte KEY_R_BRACKET_L = (byte) 0x28;
    byte KEY_R_BRACKET_R = (byte) 0x29;
    byte KEY_ASTERISK = (byte) 0x2A;
    byte KEY_PLUS = (byte) 0x2B;
    byte KEY_COMA = (byte) 0x2C;
    byte KEY_MINUS = (byte) 0x2D;
    byte KEY_FULLSTOP = (byte) 0x2E;
    byte KEY_SLASH = (byte) 0x2F;

    byte KEY_0 = (byte) 0x30;
    byte KEY_1 = (byte) 0x31;
    byte KEY_2 = (byte) 0x32;
    byte KEY_3 = (byte) 0x33;
    byte KEY_4 = (byte) 0x34;
    byte KEY_5 = (byte) 0x35;
    byte KEY_6 = (byte) 0x36;
    byte KEY_7 = (byte) 0x37;
    byte KEY_8 = (byte) 0x38;
    byte KEY_9 = (byte) 0x39;
    byte KEY_COLON = (byte) 0x3A;
    byte KEY_SEMICOLON = (byte) 0x3B;
    byte KEY_LT = (byte) 0x3C;
    byte KEY_EQ = (byte) 0x3D;
    byte KEY_GT = (byte) 0x3E;
    byte KEY_QUESTION = (byte) 0x3F;
}
