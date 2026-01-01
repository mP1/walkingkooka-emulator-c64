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

public interface CpuInstructionOpcodes {

    byte ASLA = (byte) 0x0a;

    byte BCC = (byte) 0x90;

    byte BCS = (byte) 0xB0;

    byte BEQ = (byte) 0xF0;

    byte BMI = (byte) 0x30;

    byte BNE = (byte) 0xD0;

    byte BPL = (byte) 0x10;

    byte BRK = (byte) 0x00;

    byte BVC = (byte) 0x50;

    byte BVS = (byte) 0x70;

    byte CLC = (byte) 0x18;

    byte CLD = (byte) 0xD8;

    byte CLI = (byte) 0x58;

    byte CLV = (byte) 0xB8;

    byte JMP_ABS = (byte) 0x4C;

    byte JMP_IND = (byte) 0x6C;

    byte JSR = (byte) 0x20;

    byte NOP = (byte) 0xEA;

    byte PHA = (byte) 0x48;

    byte PHP = (byte) 0x08;

    byte PLA = (byte) 0x68;

    byte RTI = (byte) 0x40;

    byte RTS = (byte) 0x60;

    byte SEC = (byte) 0x38;

    byte SED = (byte) 0xF8;

    byte SEI = (byte) 0x78;

    byte TAX = (byte) 0xAA;

    byte TAY = (byte) 0xA8;

    byte TSX = (byte) 0xBA;

    byte TXA = (byte) 0x8A;

    byte TXS = (byte) 0x9A;

    byte TYA = (byte) 0x98;
}
