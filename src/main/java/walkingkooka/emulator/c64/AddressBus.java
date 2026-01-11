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

/**
 * Interface for all devices including memory that support reading and writing bytes.
 * <br>
 * https://www.c64-wiki.com/wiki/Memory_Map
 * <pre>
 * I/O Table
 * Hex Address	Dec Address	Page	Contents
 * $0000-$0001	0-1	-	CPU I/O port - see Zeropage
 * $D000-$D3FF	53248-54271	Page 208-211	VIC-II registers
 * $D400-$D7FF	54272-55295	Page 212-215	SID registers
 * $D800-$DBFF	55296-56319	Page 216-219	Color Memory
 * $DC00-$DCFF	56320-56575	Page 220	CIA 1
 * $DD00-$DDFF	56576-56831	Page 221	CIA 2
 * $DE00-$DEFF	56832-57087	Page 222	I/O 1
 * $DF00-$DFFF	57088-57343	Page 223	I/O 2
 *
 * ROM Table
 * Cartridge ROM only becomes resident if attached to the expansion port on power-up. It is included for completeness as a record of the addresses it occupies as a ROM bank.
 *
 * Hex Address	Dec Address	Page	Contents
 * $8000-$9FFF	32768-40959	Page 128-159	Cartridge ROM (low)
 * $A000-$BFFF	40960-49151	Page 160-191	BASIC interpretor ROM or cartridge ROM (high)
 * $D000-$DFFF	53248-57343	Page 208-223	Character generator ROM
 * $E000-$FFFF	57344-65535	Page 224-255	KERNAL ROM or cartridge ROM (high)
 * </pre>
 */
public interface AddressBus {

    /**
     * <pre>
     * $0000	0	Register	6510 CPU's data direction I/O port register; 0 = input, 1 = output
     * Bit 0 - Direction of I/O on port at bit 0 of address $0001; default = 1
     * Bit 1 - Direction of I/O on port at bit 1 of address $0001; default = 1
     * Bit 2 - Direction of I/O on port at bit 2 of address $0001; default = 1
     * Bit 3 - Direction of I/O on port at bit 3 of address $0001; default = 1
     * Bit 4 - Direction of I/O on port at bit 4 of address $0001; default = 0
     * Bit 5 - Direction of I/O on port at bit 5 of address $0001; default = 1
     * Bit 6 - Direction of I/O on port at bit 6 of address $0001; unused, as bit 6 of address $0001 is undefined
     * Bit 7 - Direction of I/O on port at bit 7 of address $0001; unused, as bit 7 of address $0001 is undefined
     * </pre>
     */
    byte DEFAULT_CPU_DATA_DIRECTION = (byte) 0b0101111;

    /**
     * <pre>
     * $0001	1	Register	6510 CPU's on-chip port register
     * Bit 0 - LORAM: Configures RAM or ROM at $A000-$BFFF (see bankswitching)
     * Bit 1 - HIRAM: Configures RAM or ROM at $E000-$FFFF (see bankswitching)
     * Bit 2 - CHAREN: Configures I/O or ROM at $D000-$DFFF (see bankswitching)
     * Bit 3 - Cassette Data Output Line (Datasette)
     * Bit 4 - Cassette Switch Sense; 1 = Switch Closed
     * Bit 5 - Cassette Motor Control; 0 = On, 1 = Off
     * Bit 6 - Undefined
     * Bit 7 - Undefined
     * </pre>
     */
    byte DEFAULT_CPU_PORT = (byte) 0b100111;

    short BASIC_BASE = (short) 0xA000;

    short CARTRIDGE_BASE = (short) 0x8000;

    short CIAA_BASE = (short) 0xDC00;

    short CIAB_BASE = (short) 0xDD00;

    short CHARACTER_GENERATOR_BASE = (short) 0xD000;

    short COLOR_MEMORY_BASE = (short) 0xD800;

    short IO1_BASE = (short) 0xDE00;

    short IO2_BASE = (short) 0xDF00;

    short KERNAL_BASE = (short) 0xE000;

    short SID_BASE = (short) 0xD400;

    short VICII_BASE = (short) 0xD000;

    /**
     * Reads a single byte at the offset.
     */
    byte read(final int offset);

    /**
     * Writes a single byte to the given offset.
     */
    void write(final int offset, final byte value);

    /**
     * The number of values
     */
    int size();

    /**
     * Creates a view of this {@link AddressBus} where reads/writes have the given base offset added.
     * This is useful to break up a large memory into smaller chunks.
     */
    default AddressBus setBaseOffset(final int offset) {
        return 0 == offset ?
            this :
            AddressBuses.baseOffset(offset, this);
    }
}
