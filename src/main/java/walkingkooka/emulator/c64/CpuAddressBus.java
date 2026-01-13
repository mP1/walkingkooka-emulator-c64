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

import java.util.Objects;

/**
 * <a href="https://www.c64-wiki.com/wiki/Zeropage"></a>
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
 * <br>
 * <a href="https://www.c64-wiki.com/wiki/Bank_Switching"></a>
 * <pre>
 * CPU Control Lines
 * The three least significant bits in the port register (address 1) correspond to the three control lines in the port used for bank switching. To use these control lines, they must be configured as outputs, i.e. the same three least significant bits in the port's directional data register (at address 0) must be set to 1. This is the default upon power-up, but a programmer may want to make certain of this before bank switching. The control lines, in general, perform the function given in their descriptions. However, a combination of control lines are occasionally used to set a particular memory configuration.
 *
 * LORAM (bit 0, weight 1) is a control line which banks the 8 kByte BASIC ROM in or out of the CPU address space. Normally, this line is logically high (set to 1) for BASIC operation. If this line is logically low (cleared to 0), the BASIC ROM will typically disappear from the memory map and be replaced by 8 kBytes of RAM from $A000-$BFFF. Some exceptions to this rule exist; see the table below for a full overview.
 * HIRAM (bit 1, weight 2) is a control line which banks the 8 kByte KERNAL ROM in or out of the CPU address space. Normally, this line is logically high (set to 1) for KERNAL ROM operation. If this line is logically low (cleared to 0), the KERNAL ROM will typically disappear from the memory map and be replaced by 8 kBytes of RAM from $E000-$FFFF. Some exceptions to this rule exist; see the table below for a full overview.
 * CHAREN (bit 2, weight 4) is a control line which banks the 4 kByte character generator ROM in or out of the CPU address space. From the CPU point of view, the character generator ROM occupies the same address space as the I/O devices ($D000-$DFFF). When the CHAREN line is set to 1 (as is normal), the I/O devices appear in the CPU address space, and the character generator ROM is not accessible. When the CHAREN bit is cleared to 0, the character generator ROM appears in the CPU address space, and the I/O devices are not accessible. The CPU only needs to access the character generator ROM when downloading the character set from ROM to RAM. CHAREN can be overridden by other control lines in certain memory configurations. CHAREN will have no effect on any memory configuration without I/O devices. RAM will appear from $D000-$DFFF instead.
 * </pre>
 */
final class CpuAddressBus implements AddressBus {

    static CpuAddressBus with(final AddressBus memory,
                              final AddressBus basic,
                              final AddressBus chargen,
                              final AddressBus ioDevices,
                              final AddressBus kernal) {
        Objects.requireNonNull(memory, "memory");
        Objects.requireNonNull(basic, "basic");
        Objects.requireNonNull(chargen, "chargen");
        Objects.requireNonNull(ioDevices, "ioDevices");
        Objects.requireNonNull(kernal, "kernal");

        return new CpuAddressBus(memory, basic, chargen, ioDevices, kernal);
    }

    private CpuAddressBus(final AddressBus memory,
                          final AddressBus basic,
                          final AddressBus chargen,
                          final AddressBus ioDevices,
                          final AddressBus kernal) {
        super();
        this.memory = memory;
        this.basic = basic;
        this.chargen = chargen;
        this.ioDevices = ioDevices;
        this.kernal = kernal;
    }

    final static int BASIC_ROM_BEGIN = 0xFFFF & BASIC_BASE;
    final static int BASIC_ROM_END = 0xBFFF;

    final static int IO_DEVICES_BEGIN = 0xD000;
    final static int IO_DEVICES_END = 0xDFFF;

    final static int KERNAL_ROM_BEGIN = 0xFFFF & KERNAL_BASE;
    final static int KERNAL_ROM_END = 0xffff;

    @Override
    public byte read(final int offset) {
        final byte value;

        if (this.basicRomMapped && offset >= BASIC_ROM_BEGIN && offset <= BASIC_ROM_END) {
            value = this.basic.read(offset);
        } else {
            if (offset >= IO_DEVICES_BEGIN && offset <= IO_DEVICES_END) {
                value = this.ioDevicesMapped ?
                    this.ioDevices.read(offset) :
                    this.chargen.read(offset);
            } else {
                if (this.kernalRomMapped && offset >= KERNAL_ROM_BEGIN && offset <= KERNAL_ROM_END) {
                    value = this.kernal.read(offset);
                } else {
                    // default read from memory...
                    value = this.memory.read(offset);
                }
            }
        }

        return value;
    }

    /**
     * If offset is within IODEVICE range and iodevices are mapped in write to the devices, otherwise
     * write to memory with a special case for the PORT1 (BANK control).
     */
    @Override
    public void write(final int offset,
                      final byte value) {
        final int masked = offset & MASK;
        if (masked >= IO_DEVICES_BEGIN & masked <= IO_DEVICES_END) {
            if (this.ioDevicesMapped) {
                this.ioDevices.write(masked, value);
            } else {
                this.chargen.write(masked, value); // assumes writing to CHARGEN eventually writes to memory
            }
        } else {
            switch (masked) {
                case DATA_DIRECTION:
                    this.writeDataDirection(value);
                    break;
                case PORT:
                    this.writePort(value);
                    break;
                default:
                    break;
            }

            this.memory.write(masked, value);
        }
    }

    final static int MASK = 0xffff;

    final static int DATA_DIRECTION = 0;
    final static int PORT = 1;

    private void writeDataDirection(final byte value) {
        this.basicRomOutput = LORAM.read(value);
        this.ioDevicesOutput = CHAREN.read(value);
        this.kernalRomOutput = HIRAM.read(value);
    }

    private void writePort(final byte value) {
        this.basicRomMapped = this.basicRomOutput &&
            LORAM.read(value);
        this.ioDevicesMapped = this.ioDevicesOutput &&
            CHAREN.read(value);
        this.kernalRomMapped = this.kernalRomOutput &&
            HIRAM.read(value);
    }

    boolean basicRomOutput;
    boolean ioDevicesOutput;
    boolean kernalRomOutput;

    boolean basicRomMapped;
    boolean ioDevicesMapped;
    boolean kernalRomMapped;

    /**
     * <pre>
     * LORAM (bit 0, weight 1) is a control line which banks the 8 kByte BASIC ROM in or out of the CPU address space.
     * Normally, this line is logically high (set to 1) for BASIC operation. If this line is logically low (cleared to 0),
     * the BASIC ROM will typically disappear from the memory map and be replaced by 8 kBytes of RAM from $A000-$BFFF.
     * Some exceptions to this rule exist; see the table below for a full overview.
     * </pre>
     */
    final static Bit LORAM = Bit.BIT0;

    /**
     * <pre>
     * HIRAM (bit 1, weight 2) is a control line which banks the 8 kByte KERNAL ROM in or out of the CPU address space.
     * Normally, this line is logically high (set to 1) for KERNAL ROM operation. If this line is logically low (cleared to 0),
     * the KERNAL ROM will typically disappear from the memory map and be replaced by 8 kBytes of RAM from $E000-$FFFF.
     * Some exceptions to this rule exist; see the table below for a full overview.
     * </pre>
     */
    final static Bit HIRAM = Bit.BIT1;

    /**
     * <pre>
     * CHAREN (bit 2, weight 4) is a control line which banks the 4 kByte character generator ROM in or out of the CPU address space.
     * From the CPU point of view, the character generator ROM occupies the same address space as the I/O devices ($D000-$DFFF).
     * When the CHAREN line is set to 1 (as is normal), the I/O devices appear in the CPU address space, and the character generator ROM
     * is not accessible. When the CHAREN bit is cleared to 0, the character generator ROM appears in the CPU address space,
     * and the I/O devices are not accessible. The CPU only needs to access the character generator ROM when downloading
     * the character set from ROM to RAM. CHAREN can be overridden by other control lines in certain memory configurations.
     * CHAREN will have no effect on any memory configuration without I/O devices. RAM will appear from $D000-$DFFF instead.
     * </pre>
     */
    final static Bit CHAREN = Bit.BIT2;

    @Override
    public int size() {
        return 256 * 256;
    }

    private final AddressBus memory;
    private final AddressBus chargen;
    private final AddressBus basic;
    private final AddressBus ioDevices;
    private final AddressBus kernal;

    @Override
    public String toString() {
        final StringBuilder toString = new StringBuilder();

        if (this.basicRomMapped) {
            toString.append("BASIC");
        }

        if (toString.length() > 0) {
            toString.append(" ");
        }
        if (this.ioDevicesMapped) {
            toString.append("IO");
        } else {
            toString.append("CHARGEN");
        }

        if (this.kernalRomMapped) {
            if (toString.length() > 0) {
                toString.append(" ");
            }
            toString.append("KERNAL");
        }

        return toString.toString();
    }
}
