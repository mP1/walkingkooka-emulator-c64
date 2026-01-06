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

    byte ADCABS = (byte) 0x6D;

    byte ADCABSX = (byte) 0x7D;

    byte ADCABSY = (byte) 0x79;

    byte ADCIMM = (byte) 0x69;

    byte ADCINDX = (byte) 0x61;

    byte ADCINDY = (byte) 0x71;

    byte ADCZP = (byte) 0x65;

    byte ADCZPX = (byte) 0x75;

    byte ANDABS = (byte) 0x2D;

    byte ANDABSX = (byte) 0x3D;

    byte ANDABSY = (byte) 0x39;

    byte ANDIMM = (byte) 0x29;

    byte ANDINDX = (byte) 0x21;

    byte ANDINDY = (byte) 0x31;

    byte ANDZP = (byte) 0x25;

    byte ANDZPX = (byte) 0x35;

    byte ASLA = (byte) 0x0a;

    byte ASLZP = (byte) 0x06;

    byte ASLZPX = (byte) 0x16;

    byte ASLABS = (byte) 0x0E;

    byte ASLABSX = (byte) 0x1E;

    byte BCC = (byte) 0x90;

    byte BCS = (byte) 0xB0;

    byte BEQ = (byte) 0xF0;

    byte BITABS = (byte) 0x2C;

    byte BITZP = (byte) 0x24;

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

    byte CMPIMM = (byte) 0xC9;

    byte CMPABS = (byte) 0xCD;

    byte CMPABSX = (byte) 0xDD;

    byte CMPABSY = (byte) 0xD9;

    byte CMPINDX = (byte) 0xC1;

    byte CMPINDY = (byte) 0xD1;

    byte CMPZP = (byte) 0xC5;

    byte CMPZPX = (byte) 0xD5;

    byte CPXABS = (byte) 0xEC;

    byte CPXIMM = (byte) 0xE0;

    byte CPXZP = (byte) 0xE4;

    byte CPYABS = (byte) 0xCC;

    byte CPYIMM = (byte) 0xC0;

    byte CPYZP = (byte) 0xC4;

    byte DECABS = (byte) 0xCE;

    byte DECABSX = (byte) 0xDE;

    byte DECZP = (byte) 0xC6;

    byte DECZPX = (byte) 0xD6;

    byte DEX = (byte) 0xCA;

    byte DEY = (byte) 0x88;

    byte EORABS = (byte) 0x4D;

    byte EORABSX = (byte) 0x5D;

    byte EORABSY = (byte) 0x59;

    byte EORIMM = (byte) 0x49;

    byte EORINDX = (byte) 0x41;

    byte EORINDY = (byte) 0x51;

    byte EORZP = (byte) 0x45;

    byte EORZPX = (byte) 0x55;

    byte INCABS = (byte) 0xEE;

    byte INCABSX = (byte) 0xFE;

    byte INCZP = (byte) 0xE6;

    byte INCZPX = (byte) 0xF6;

    byte INX = (byte) 0xE8;

    byte INY = (byte) 0xC8;

    byte JMP_ABS = (byte) 0x4C;

    byte JMP_IND = (byte) 0x6C;

    byte JSR = (byte) 0x20;

    byte LDAABS = (byte) 0xAD;

    byte LDAABSX = (byte) 0xBD;

    byte LDAABSY = (byte) 0xB9;

    byte LDAIMM = (byte) 0xA9;

    byte LDAINDX = (byte) 0xA1;

    byte LDAINDY = (byte) 0xB1;

    byte LDAZP = (byte) 0xA5;

    byte LDAZPX = (byte) 0xB5;

    byte LDXABS = (byte) 0xAE;

    byte LDXABSY = (byte) 0xBE;

    byte LDXIMM = (byte) 0xA2;

    byte LDXZP = (byte) 0xA6;

    byte LDXZPY = (byte) 0xB6;

    byte LDYIMM = (byte) 0xA0;

    byte LDYZP = (byte) 0xA4;

    byte LSRA = (byte) 0x4A;

    byte LSRZP = (byte) 0x46;

    byte LSRZPX = (byte) 0x56;

    byte LSRABS = (byte) 0x4E;

    byte LSRABSX = (byte) 0x5E;

    byte NOP = (byte) 0xEA;

    byte ORABS = (byte) 0x0D;

    byte ORABSX = (byte) 0x1D;

    byte ORABSY = (byte) 0x19;

    byte ORIMM = (byte) 0x09;

    byte ORINDX = (byte) 0x01;

    byte ORINDY = (byte) 0x11;

    byte ORZP = (byte) 0x05;

    byte ORZPX = (byte) 0x15;

    byte PHA = (byte) 0x48;

    byte PHP = (byte) 0x08;

    byte PLA = (byte) 0x68;

    byte ROLA = (byte) 0x2A;

    byte ROLZP = (byte) 0x26;

    byte ROLZPX = (byte) 0x36;

    byte ROLABS = (byte) 0x2E;

    byte ROLABSX = (byte) 0x3E;

    byte RORA = (byte) 0x6A;

    byte RORABS = (byte) 0x6E;

    byte RORABSX = (byte) 0x7E;

    byte RORZP = (byte) 0x66;

    byte RORZPX = (byte) 0x76;

    byte RTI = (byte) 0x40;

    byte RTS = (byte) 0x60;

    byte SBCABS = (byte) 0xED;

    byte SBCABSX = (byte) 0xFD;

    byte SBCABSY = (byte) 0xF9;

    byte SBCIMM = (byte) 0xE9;

    byte SBCINDX = (byte) 0xE1;

    byte SBCINDY = (byte) 0xF1;

    byte SBCZP = (byte) 0xE5;

    byte SBCZPX = (byte) 0xF5;

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
