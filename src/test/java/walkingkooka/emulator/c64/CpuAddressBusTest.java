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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public final class CpuAddressBusTest extends AddressBusTestCase<CpuAddressBus> {

    private final static Byte MEMORY = 1;
    private final static Byte BASIC = 2;
    private final static Byte CHARGEN = 3;
    private final static Byte IO = 4;
    private final static Byte KERNAL = 5;

    @Test
    public void testWithNullMemoryFails() {
        assertThrows(
            NullPointerException.class,
            () -> CpuAddressBus.with(
                null,
                this.basic(),
                this.chargen(),
                this.ioDevices(),
                this.kernal()
            )
        );
    }

    @Test
    public void testWithNullBasicFails() {
        assertThrows(
            NullPointerException.class,
            () -> CpuAddressBus.with(
                this.memory(),
                null,
                chargen(),
                this.ioDevices(),
                this.kernal()
            )
        );
    }

    @Test
    public void testWithNullChargenFails() {
        assertThrows(
            NullPointerException.class,
            () -> CpuAddressBus.with(
                this.memory(),
                this.basic(),
                null,
                this.ioDevices(),
                this.kernal()
            )
        );
    }

    @Test
    public void testWithNullIoDevicesFails() {
        assertThrows(
            NullPointerException.class,
            () -> CpuAddressBus.with(
                this.memory(),
                this.basic(),
                this.chargen(),
                null,
                this.kernal()
            )
        );
    }

    @Test
    public void testWithNullKernalFails() {
        assertThrows(
            NullPointerException.class,
            () -> CpuAddressBus.with(
                this.memory(),
                this.basic(),
                this.chargen(),
                this.ioDevices(),
                null
            )
        );
    }

    // read.............................................................................................................

    @Test
    public void testReadMemory() {
        final CpuAddressBus bus = this.createAddressBus();

        this.readBelowBasicAndCheck(bus, MEMORY);
        this.readBasicAndCheck(bus, MEMORY);
        this.readIoAndCheck(bus, CHARGEN);
        this.readKernalAndCheck(bus, MEMORY);
    }

    @Test
    public void testReadBasicRom() {
        final CpuAddressBus bus = this.createAddressBus();
        bus.basicRomMapped = true;

        this.readBelowBasicAndCheck(bus, MEMORY);
        this.readBasicAndCheck(bus, BASIC);
        this.readIoAndCheck(bus, CHARGEN);
        this.readKernalAndCheck(bus, MEMORY);
    }

    @Test
    public void testReadChargen() {
        final CpuAddressBus bus = this.createAddressBus();
        bus.ioDevicesMapped = false;

        this.readBelowBasicAndCheck(bus, MEMORY);
        this.readBasicAndCheck(bus, MEMORY);
        this.readIoAndCheck(bus, CHARGEN);
        this.readKernalAndCheck(bus, MEMORY);
    }

    @Test
    public void testReadIoDevices() {
        final CpuAddressBus bus = this.createAddressBus();
        bus.ioDevicesMapped = true;

        this.readBelowBasicAndCheck(bus, MEMORY);
        this.readBasicAndCheck(bus, MEMORY);
        this.readIoAndCheck(bus, IO);
        this.readKernalAndCheck(bus, MEMORY);
    }

    @Test
    public void testReadKernalRom() {
        final CpuAddressBus bus = this.createAddressBus();
        bus.kernalRomMapped = true;

        this.readBelowBasicAndCheck(bus, MEMORY);
        this.readBasicAndCheck(bus, MEMORY);
        this.readIoAndCheck(bus, CHARGEN);
        this.readKernalAndCheck(bus, KERNAL);
    }

    // write............................................................................................................

    private final boolean BASIC_TRUE = true;
    private final boolean BASIC_FALSE = false;

    private final boolean IO_TRUE = true;
    private final boolean IO_FALSE = false;

    private final boolean KERNAL_TRUE = true;
    private final boolean KERNAL_FALSE = false;

    @Test
    public void testWriteDataDirectionZeroPortZero() {
        this.writeBankAndCheck(0x7,
            0,
            BASIC_FALSE,
            IO_FALSE,
            KERNAL_FALSE);
    }

    @Test
    public void testWriteDataDirectionBasicPortBasicTrue() {
        final byte value = CpuAddressBus.LORAM.set();
        this.writeBankAndCheck(value,
            value,
            BASIC_TRUE,
            IO_FALSE,
            KERNAL_FALSE);
    }

    @Test
    public void testWriteDataDirectionIoPortIoTrue() {
        final byte value = CpuAddressBus.CHAREN.set();
        this.writeBankAndCheck(value,
            value,
            BASIC_FALSE,
            IO_TRUE,
            KERNAL_FALSE);
    }

    @Test
    public void testWriteDataDirectionKernalPortKernalTrue() {
        final byte value = CpuAddressBus.HIRAM.set();
        this.writeBankAndCheck(value,
            value,
            BASIC_FALSE,
            IO_FALSE,
            KERNAL_TRUE);
    }

    @Test
    public void testWriteDataDirectionFalseAndBasicIoKernalTrueAndIgnored() {
        this.writeBankAndCheck(0,
            CpuAddressBus.LORAM.or(CpuAddressBus.CHAREN, CpuAddressBus.HIRAM),
            BASIC_FALSE,
            IO_FALSE,
            KERNAL_FALSE);
    }

    @Test
    public void testWriteDataDirectionAndBasicIoKernalAllTrue() {
        final byte value = CpuAddressBus.LORAM.or(CpuAddressBus.CHAREN, CpuAddressBus.HIRAM);
        this.writeBankAndCheck(value,
            value,
            BASIC_TRUE,
            IO_TRUE,
            KERNAL_TRUE);
    }

    private void writeBankAndCheck(final int dataDirection,
                                   final int port,
                                   final boolean basic,
                                   final boolean ioDevices,
                                   final boolean kernal) {
        final CpuAddressBus bus = CpuAddressBus.with(AddressBuses.memory(0x10000),
            this.basic(),
            this.chargen(),
            this.ioDevices(),
            this.kernal());

        bus.write(CpuAddressBus.DATA_DIRECTION, (byte) dataDirection);
        bus.write(CpuAddressBus.PORT, (byte) port);

        assertEquals(basic, bus.basicRomMapped, () -> "basicRomMapped " + bus);
        assertEquals(ioDevices, bus.ioDevicesMapped, () -> "ioDevicesMapped " + bus);
        assertEquals(kernal, bus.kernalRomMapped, () -> "kernalRomMapped " + bus);
    }

    @Test
    public void testWriteMemory() {
        final AddressBus memory = AddressBuses.memory(0x10000);
        final AddressBus basic = this.basic();
        final AddressBus chargen = this.chargen();
        final AddressBus ioDevices = this.ioDevices();
        final AddressBus kernal = this.kernal();

        final CpuAddressBus bus = CpuAddressBus.with(memory, basic, chargen, ioDevices, kernal);

        this.write2(bus,
            0,
            CpuAddressBus.IO_DEVICES_BEGIN - 1,
            MEMORY);
        this.write2(bus,
            CpuAddressBus.IO_DEVICES_BEGIN,
            CpuAddressBus.IO_DEVICES_END,
            CHARGEN);
        this.write2(bus,
            CpuAddressBus.KERNAL_ROM_BEGIN,
            CpuAddressBus.KERNAL_ROM_END,
            MEMORY);

        this.readBelowBasicAndCheck(bus, MEMORY);
        this.readIoAndCheck(bus, CHARGEN);
        this.readKernalAndCheck(bus, MEMORY);
    }

    @Test
    public void testWriteBasicRom() {
        final AddressBus memory = this.memory(CpuAddressBus.BASIC_ROM_BEGIN,
            CpuAddressBus.BASIC_ROM_END,
            BASIC);
        final AddressBus basic = this.basic();
        final AddressBus chargen = this.chargen();
        final AddressBus ioDevices = this.ioDevices();
        final AddressBus kernal = this.kernal();

        final CpuAddressBus bus = CpuAddressBus.with(memory, basic, chargen, ioDevices, kernal);
        bus.basicRomMapped = true;

        this.write2(bus,
            CpuAddressBus.BASIC_ROM_BEGIN,
            CpuAddressBus.BASIC_ROM_END,
            BASIC);

        bus.basicRomMapped = false;

        this.readBasicAndCheck(bus, BASIC);
    }

    @Test
    public void testWriteChargen() {
        final AddressBus memory = AddressBuses.fake();
        final AddressBus basic = this.basic();
        final AddressBus chargen = this.chargen();
        final AddressBus ioDevices = this.ioDevices();
        final AddressBus kernal = this.kernal();

        final CpuAddressBus bus = CpuAddressBus.with(memory, basic, chargen, ioDevices, kernal);

        this.write2(
            bus,
            CpuAddressBus.IO_DEVICES_BEGIN,
            CpuAddressBus.IO_DEVICES_END,
            CHARGEN
        );

        this.readIoAndCheck(bus, CHARGEN);
    }

    @Test
    public void testWriteIoDevices() {
        final AddressBus memory = AddressBuses.fake();
        final AddressBus basic = this.basic();
        final AddressBus chargen = this.chargen();
        final AddressBus ioDevices = this.ioDevices();
        final AddressBus kernal = this.kernal();

        final CpuAddressBus bus = CpuAddressBus.with(memory, basic, chargen, ioDevices, kernal);
        bus.ioDevicesMapped = true;

        this.write2(
            bus,
            CpuAddressBus.IO_DEVICES_BEGIN,
            CpuAddressBus.IO_DEVICES_END,
            IO
        );

        this.readIoAndCheck(bus, IO);

        bus.ioDevicesMapped = false;
        this.readIoAndCheck(bus, CHARGEN);
    }

    @Test
    public void testWriteKernalRom() {
        final AddressBus memory = this.memory(
            CpuAddressBus.KERNAL_ROM_BEGIN,
            CpuAddressBus.KERNAL_ROM_END,
            KERNAL
        );
        final AddressBus basic = this.basic();
        final AddressBus chargen = this.chargen();
        final AddressBus ioDevices = this.ioDevices();
        final AddressBus kernal = this.kernal();

        final CpuAddressBus bus = CpuAddressBus.with(memory, basic, chargen, ioDevices, kernal);
        bus.kernalRomMapped = true;

        this.write2(
            bus,
            CpuAddressBus.KERNAL_ROM_BEGIN,
            CpuAddressBus.KERNAL_ROM_END,
            KERNAL
        );

        bus.kernalRomMapped = false;

        this.readBasicAndCheck(bus, KERNAL);
    }

    @Test
    public void testWriteManyBankings() {
        final AddressBus memory = this.memory();
        final AddressBus basic = this.basic();
        final AddressBus chargen = this.chargen();
        final AddressBus ioDevices = this.ioDevices();
        final AddressBus kernal = this.kernal();

        final CpuAddressBus bus = CpuAddressBus.with(memory, basic, chargen, ioDevices, kernal);

        this.write2(
            bus,
            0,
            CpuAddressBus.BASIC_ROM_BEGIN - 1,
            MEMORY
        );
        this.write2(
            bus,
            CpuAddressBus.KERNAL_ROM_BEGIN,
            CpuAddressBus.KERNAL_ROM_END,
            MEMORY
        );

        bus.basicRomMapped = true;
        bus.ioDevicesMapped = true;
        bus.kernalRomMapped = true;

        this.readBasicAndCheck(
            bus,
            BASIC
        );

        this.write2(
            bus,
            CpuAddressBus.BASIC_ROM_BEGIN,
            CpuAddressBus.BASIC_ROM_END,
            MEMORY
        );

        this.readIoAndCheck(
            bus,
            IO
        );

        this.write2(
            bus,
            CpuAddressBus.IO_DEVICES_BEGIN,
            CpuAddressBus.IO_DEVICES_END,
            IO
        );

        this.readKernalAndCheck(
            bus,
            KERNAL
        );

        this.write2(
            bus,
            CpuAddressBus.KERNAL_ROM_BEGIN,
            CpuAddressBus.KERNAL_ROM_END,
            MEMORY
        );

        this.readAndCheck2(
            bus,
            0,
            CpuAddressBus.BASIC_ROM_BEGIN - 1,
            MEMORY
        );

        this.readAndCheck2(
            bus,
            CpuAddressBus.BASIC_ROM_BEGIN,
            CpuAddressBus.BASIC_ROM_END,
            BASIC
        );

        this.readAndCheck2(
            bus,
            CpuAddressBus.BASIC_ROM_END + 1,
            CpuAddressBus.IO_DEVICES_BEGIN - 1,
            MEMORY
        );

        this.readAndCheck2(
            bus,
            CpuAddressBus.IO_DEVICES_BEGIN,
            CpuAddressBus.IO_DEVICES_END,
            IO
        );

        this.readAndCheck2(
            bus,
            CpuAddressBus.KERNAL_ROM_BEGIN,
            CpuAddressBus.KERNAL_ROM_END,
            KERNAL
        );
    }

    private void write2(final CpuAddressBus bus,
                        final int lo,
                        final int hi,
                        final byte value) {
        for (int i = lo; i <= hi; i++) {
            bus.write(i, value);
        }
    }

    private void readBelowBasicAndCheck(final CpuAddressBus bus,
                                        final byte value) {
        this.readAndCheck2(
            bus,
            0,
            CpuAddressBus.BASIC_ROM_BEGIN - 1,
            value
        );
    }

    private void readBasicAndCheck(final CpuAddressBus bus,
                                   final byte value) {
        this.readAndCheck2(
            bus,
            CpuAddressBus.BASIC_ROM_BEGIN,
            CpuAddressBus.BASIC_ROM_END,
            value
        );
    }

    private void readIoAndCheck(final CpuAddressBus bus,
                                final byte value) {
        this.readAndCheck2(
            bus,
            CpuAddressBus.IO_DEVICES_BEGIN,
            CpuAddressBus.IO_DEVICES_END,
            value
        );
    }

    private void readKernalAndCheck(final CpuAddressBus bus,
                                    final byte value) {
        this.readAndCheck2(
            bus,
            CpuAddressBus.KERNAL_ROM_BEGIN,
            CpuAddressBus.KERNAL_ROM_END,
            value
        );
    }

    private void readAndCheck2(final CpuAddressBus bus,
                               final int lo,
                               final int hi,
                               final byte value) {
        for (int i = lo; i <= hi; i++) {
            this.readAndCheck(bus, i, value);
        }
    }

    // helpers..........................................................................................................

    @Override
    public CpuAddressBus createAddressBus() {
        return CpuAddressBus.with(this.memory(),
            this.basic(),
            this.chargen(),
            this.ioDevices(),
            this.kernal());
    }

    private AddressBus memory() {
        return this.memory(0,
            0xffff,
            MEMORY);
    }

    private AddressBus basic() {
        return this.memory(CpuAddressBus.BASIC_ROM_BEGIN,
            CpuAddressBus.BASIC_ROM_END,
            BASIC);
    }

    private AddressBus chargen() {
        return this.memory(CpuAddressBus.IO_DEVICES_BEGIN,
            CpuAddressBus.IO_DEVICES_END,
            CHARGEN);
    }

    private AddressBus ioDevices() {
        return this.memory(CpuAddressBus.IO_DEVICES_BEGIN,
            CpuAddressBus.IO_DEVICES_END,
            IO);
    }

    private AddressBus kernal() {
        return this.memory(CpuAddressBus.KERNAL_ROM_BEGIN,
            CpuAddressBus.KERNAL_ROM_END,
            KERNAL);
    }

    private AddressBus memory(final int lo,
                              final int hi,
                              final byte value) {
        return new AddressBus() {
            @Override
            public byte read(final int offset) {
                checkOffset(offset);
                return value;
            }

            @Override
            public void write(final int offset, final byte write) {
                checkOffset(offset);
                assertEquals(value, write, () -> "write to " + Integer.toHexString(offset));
            }

            private void checkOffset(final int offset) {
                if (offset < lo && offset > hi) {
                    fail("Invalid offset " + offset + " not between " + Integer.toHexString(lo) + ".." + Integer.toHexString(hi));
                }
            }

            @Override
            public int size() {
                return 1;
            }

            @Override
            public String toString() {
                return Integer.toHexString(lo) + ".." + Integer.toHexString(hi) + " value=" + value;
            }
        };
    }

    // toString.........................................................................................................

    @Test
    public void testToStringBasicIOKernal() {
        final CpuAddressBus bus = this.createAddressBus();
        bus.basicRomMapped = true;
        bus.ioDevicesMapped = true;
        bus.kernalRomMapped = true;

        this.toStringAndCheck(bus, "BASIC IO KERNAL");
    }

    @Test
    public void testToStringBasicChargen() {
        final CpuAddressBus bus = this.createAddressBus();
        bus.basicRomMapped = true;
        bus.ioDevicesMapped = false;
        bus.kernalRomMapped = false;

        this.toStringAndCheck(
            bus,
            "BASIC CHARGEN"
        );
    }

    @Test
    public void testToStringChargen() {
        final CpuAddressBus bus = this.createAddressBus();
        bus.basicRomMapped = false;
        bus.ioDevicesMapped = false;
        bus.kernalRomMapped = false;

        this.toStringAndCheck(
            bus,
            "CHARGEN"
        );
    }

    @Test
    public void testToStringIo() {
        final CpuAddressBus bus = this.createAddressBus();
        bus.basicRomMapped = false;
        bus.ioDevicesMapped = true;
        bus.kernalRomMapped = false;

        this.toStringAndCheck(
            bus,
            "IO"
        );
    }

    // class............................................................................................................

    @Override
    public Class<CpuAddressBus> type() {
        return CpuAddressBus.class;
    }
}
