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

import walkingkooka.ToStringBuilder;
import walkingkooka.ToStringBuilderOption;

/**
 * Base class for both CIA chips, capturing common stuff, such as the direction ports, timers and clock and interrupts.
 * <br>
 * <a href="https://www.c64-wiki.com/wiki/CIA>CIA</a>
 */
abstract class Cia implements AddressBus, Updatable {

    Cia(final Runnable interrupt) {
        super();
        this.interrupt = interrupt;
    }

    final static int NANO_TO_TENTH = 1000_000_000 / 10;

    // AddressBus.......................................................................................................

    private final static int MASK = 0xf;

    final static int PRA = 0;
    final static int PRB = 1;
    final static int DDRA = 2;
    final static int DDRB = 3;

    final static int TA_HI = 4;
    final static int TA_LO = TA_HI + 1;

    final static int TB_HI = 6;
    final static int TB_LO = TB_HI + 1;

    final static int TOD_10TH = 8;
    final static int TOD_SEC = TOD_10TH + 1;
    final static int TOD_MIN = TOD_SEC + 1;
    final static int TOD_HR = TOD_MIN + 1;

    final static int SER = 12;

    final static int ICR = 13;
    final static int CRA = 14;
    final static int CRB = 15;

    // read.............................................................................................................

    @Override
    public byte read(final int offset) {
        byte value;

        switch (offset & MASK) {
            case PRA:
                value = this.readDataPortA();
                break;
            case PRB:
                value = this.readDataPortB();
                break;
            case DDRA:
                value = this.dataDirectionPortA;
                break;
            case DDRB:
                value = this.dataDirectionPortB;
                break;
            case TA_LO:
                value = this.readTimerALo();
                break;
            case TA_HI:
                value = this.readTimerAHi();
                break;
            case TB_LO:
                value = this.readTimerBLo();
                break;
            case TB_HI:
                value = this.readTimerBHi();
                break;
            case TOD_10TH:
                value = this.readTod10th();
                break;
            case TOD_SEC:
                value = this.readTodSec();
                break;
            case TOD_MIN:
                value = this.readTodMin();
                break;
            case TOD_HR:
                value = this.readTodHr();
                break;
            case SER:
                value = 0;
                break;
            case ICR:
                value = this.readIcr();
                break;
            case CRA:
                value = this.readCra();
                break;
            case CRB:
                value = this.readCrb();
                break;
            default:
                throw new UnsupportedOperationException();
        }

        return value;
    }

    abstract byte readDataPortA();

    abstract byte readDataPortB();

    // write............................................................................................................
    @Override
    public void write(final int offset,
                      final byte value) {
        switch (offset & MASK) {
            case PRA:
                this.writeDataPortA(value);
                break;
            case PRB:
                this.writeDataPortB(value);
                break;
            case DDRA:
                this.dataDirectionPortA = value;
                break;
            case DDRB:
                this.dataDirectionPortB = value;
                break;
            case TA_LO:
                this.writeTimerALo(value);
                break;
            case TA_HI:
                this.writeTimerAHi(value);
                break;
            case TB_LO:
                this.writeTimerBLo(value);
                break;
            case TB_HI:
                this.writeTimerBHi(value);
                break;
            case TOD_10TH:
                this.writeTod10th(value);
                break;
            case TOD_SEC:
                this.writeTodSec(value);
                break;
            case TOD_MIN:
                this.writeTodMin(value);
                break;
            case TOD_HR:
                this.writeTodHr(value);
                break;
            case SER:
                break;
            case ICR:
                this.writeIcr(value);
                break;
            case CRA:
                this.writeCra(value);
                break;
            case CRB:
                this.writeCrb(value);
                break;
            default:
                throw new UnsupportedOperationException();
        }
    }

    abstract void writeDataPortA(final byte value);

    abstract void writeDataPortB(final byte value);

    // registers/devices................................................................................................

    byte dataDirectionPortA;
    byte dataDirectionPortB;

    /**
     * <pre>
     * Read: (Bit0..4 = INT DATA, Origin of the interrupt)
     * Bit 0: 1 = Underflow Timer A
     * Bit 1: 1 = Underflow Timer B
     * Bit 2: 1 = Time of day and alarm time is equal
     * Bit 3: 1 = SDR full or empty, so full byte was transferred, depending of operating mode serial bus
     * Bit 4: 1 = IRQ Signal occured at FLAG-pin (cassette port Data input, serial bus SRQ IN)
     * Bit 5..6: always 0
     * Bit 7: 1 = IRQ An interrupt occured, so at least one bit of INT MASK and INT DATA is set in both registers.
     * Flags will be cleared after reading the register!
     * </pre>
     * SDR, IRQ bits NOT SUPPORTED.
     */
    final static Bit TIMERA_UNDERFLOW = Bit.BIT0;
    final static Bit TIMERB_UNDERFLOW = Bit.BIT1;
    final static Bit TIME_ALARM_MATCH = Bit.BIT2;
    final static Bit IRQ = Bit.BIT7;

    private byte readIcr() {
        final byte value = this.icrMask;
        this.icrMask = 0;

        return ((value & this.icrEnabled) != 0) ? IRQ.set(value) : value;
    }

    private byte icrMask;

    private void writeIcr(final byte value) {
        byte icrEnabled = this.icrEnabled;

        // 1 = sets, 0 == clear)
        if(Bit.BIT7.read(value)) {
            if (TIMERA_UNDERFLOW.read(value)) {
                icrEnabled |= TIMERA_UNDERFLOW.set(icrEnabled);
            }
            if (TIMERB_UNDERFLOW.read(value)) {
                icrEnabled = TIMERB_UNDERFLOW.set(icrEnabled);
            }
            if (TIME_ALARM_MATCH.read(value)) {
                icrEnabled = TIME_ALARM_MATCH.set(icrEnabled);
            }
        } else {
            if (TIMERA_UNDERFLOW.read(value)) {
                icrEnabled |= TIMERA_UNDERFLOW.clear(icrEnabled);
            }
            if (TIMERB_UNDERFLOW.read(value)) {
                icrEnabled = TIMERB_UNDERFLOW.clear(icrEnabled);
            }
            if (TIME_ALARM_MATCH.read(value)) {
                icrEnabled = TIME_ALARM_MATCH.clear(icrEnabled);
            }
        }
        this.icrEnabled = icrEnabled;
    }

    private byte icrEnabled;

    private String icrMaskToString() {
        return icrMaskOrEnabledToString(Bit.BIT7.clear(this.icrMask));
    }

    private String icrEnabledToString() {
        return icrMaskOrEnabledToString(this.icrEnabled);
    }

    private static String icrMaskOrEnabledToString(final byte value) {
        return Bit.byteText(value,
                "IRQ",
                "",
                "",
                "",
                "",
                "TIME_ALARM_MATCH",
                "TIMERB_UNDERFLOW",
                "TIMERA_UNDERFLOW");
    }
    
    // timerA...........................................................................................................

    /**
     * <pre>
     * Bit 0: 0 = Stop timer; 1 = Start timer
     * Bit 1: 1 = Indicates a timer underflow at port B in bit 6.
     * Bit 2: 0 = Through a timer overflow, bit 6 of port B will get high for one cycle , 1 = Through a timer underflow, bit 6 of port B will be inverted
     * Bit 3: 0 = Timer-restart after underflow (latch will be reloaded), 1 = Timer stops after underflow.
     * Bit 4: 1 = Load latch into the timer once.
     * Bit 5: 0 = Timer counts system cycles, 1 = Timer counts positive slope at CNT-pin
     * Bit 6: Direction of the serial shift register, 0 = SP-pin is input (read), 1 = SP-pin is output (write)
     * Bit 7: Real Time Clock, 0 = 60 Hz, 1 = 50 Hz
     * </pre>
     */
    final static Bit TIMER_START = Bit.BIT0;
    final static Bit TIMER_LOAD_LATCH = Bit.BIT4;
    final static Bit TIMER_RESTART_AFTER_UNDERFLOW = Bit.BIT3;

    private byte readCra() {
        return this.cra;
    }

    private byte cra;

    private void writeCra(final byte value) {
        this.cra = value;

        this.timerAStart = TIMER_START.read(value);

        if (TIMER_LOAD_LATCH.read(value)) {
            this.timerA.loadLatch();
        }
    }

    private String craToString() {
        return Bit.byteText(this.cra,
                "",
                "",
                "",
                "TIMER_LOAD_LATCH",
                "TIMER_RESTART_AFTER_UNDERFLOW",
                "",
                "",
                "TIMER_START");
    }

    private byte readTimerALo() {
        return loByte(this.timerA.value());
    }

    private byte readTimerAHi() {
        return hiByte(this.timerA.value());
    }

    private void writeTimerALo(final byte value) {
        this.timerALatch = (this.timerALatch & 0xff00) + value;
        this.timerA.setLatch(this.timerALatch);
    }

    private void writeTimerAHi(final byte value) {
        this.timerALatch = value << 8 + (this.timerALatch & 0xff);
        this.timerA.setLatch(this.timerALatch);
    }

    private int timerALatch = 0;

    private void updateTimerA(final int cycles) {
        if (this.timerAStart) {
            this.timerA.update(cycles);
        }
    }

    private boolean timerAStart; // BIT0

    final Timer timerA = Timer.with(this::timerAUnderflow);

    private void timerAUnderflow() {
        this.icrMask = TIMERA_UNDERFLOW.set(this.icrMask);

        // Bit 3: 0 = Timer-restart after underflow (latch will be reloaded), 1 = Timer stops after underflow..
        if (TIMER_RESTART_AFTER_UNDERFLOW.read(this.cra)) {
            this.timerA.loadLatch();
        }

        if (TIMERA_UNDERFLOW.read(this.icrEnabled)) {
            this.interrupt.run();
        }
    }

    // timerB...........................................................................................................

    /**
     * <pre>
     * Bit 0: 0 = Stop timer; 1 = Start timer
     * Bit 1: 1 = Indicates a timer underflow at port B in bit 7.
     * Bit 2: 0 = Through a timer overflow, bit 7 of port B will get high for one cycle , 1 = Through a timer underflow, bit 7 of port B will be inverted
     * Bit 3: 0 = Timer-restart after underflow (latch will be reloaded), 1 = Timer stops after underflow.
     * Bit 4: 1 = Load latch into the timer once.
     * Bit 5..6:
     *  %00 = Timer counts System cycle
     *  %01 = Timer counts positive slope on CNT-pin
     *  %10 = Timer counts underflow of timer A
     *  %11 = Timer counts underflow of timer A if the CNT-pin is high
     * Bit 7: 0 = Writing into the TOD register sets the clock time, 1 = Writing into the TOD register sets the alarm time.
     * </pre>
     * TODO BIT1, BIT2, BIT5, BIT6
     */
    final static Bit ALARM_WRITE = Bit.BIT7;

    private byte readCrb() {
        return this.crb;
    }

    // Bit 7: 0 = Writing into the TOD register sets the clock time, 1 = Writing into the TOD register sets the alarm time.
    private boolean clockAlarmWrite() {
        return ALARM_WRITE.read(this.crb);
    }

    private byte crb;

    private void writeCrb(final byte value) {
        this.crb = value;

        this.timerBStart = TIMER_START.read(value);

        if (TIMER_LOAD_LATCH.read(value)) {
            this.timerB.loadLatch();
        }
    }

    private String crbToString() {
        return Bit.byteText(this.crb,
                "ALARM_WRITE",
                "",
                "",
                "TIMER_LOAD_LATCH",
                "TIMER_RESTART_AFTER_UNDERFLOW",
                "",
                "",
                "TIMER_START");
    }

    private byte readTimerBLo() {
        return loByte(this.timerB.value());
    }

    private byte readTimerBHi() {
        return hiByte(this.timerB.value());
    }

    private void writeTimerBLo(final byte value) {
        this.timerBLatch = (this.timerBLatch & 0xff00) + value;
        this.timerB.setLatch(this.timerBLatch);
    }

    private void writeTimerBHi(final byte value) {
        this.timerBLatch = value << 8 + (this.timerBLatch & 0xff);
        this.timerB.setLatch(this.timerBLatch);
    }

    private int timerBLatch = 0;

    private void updateTimerB(final int cycles) {
        if (this.timerBStart) {
            this.timerB.update(cycles);
        }
    }

    private boolean timerBStart; // BIT0

    final Timer timerB = Timer.with(this::timerBUnderflow);

    private void timerBUnderflow() {
        this.icrMask = TIMERB_UNDERFLOW.set(this.icrMask);

        // Bit 3: 0 = Timer-restart after underflow (latch will be reloaded), 1 = Timer stops after underflow..
        if (TIMER_RESTART_AFTER_UNDERFLOW.read(this.crb)) {
            this.timerB.loadLatch();
        }

        if (TIMERB_UNDERFLOW.read(this.icrEnabled)) {
            this.interrupt.run();
        }
    }

    // clock............................................................................................................

    private byte readTod10th() {
        this.todReadIntoBufferIfNecessary();
        this.todReadBuffered = false;
        return this.readTod10th;
    }

    private byte readTod10th;

    private byte readTodSec() {
        this.todReadIntoBufferIfNecessary();
        return this.readTodSec;
    }

    private byte readTodSec;

    private byte readTodMin() {
        this.todReadIntoBufferIfNecessary();
        return this.readTodMin;
    }

    private byte readTodMin;

    private byte readTodHr() {
        this.todReadIntoBufferIfNecessary();
        return this.readTodHr;
    }

    private byte readTodHr;

    /**
     * Reads the tod clock/alarm into the buffer clearing when the tenth of a second register is read
     */
    private void todReadIntoBufferIfNecessary() {
        if (false == this.todReadBuffered) {
            final TimeOfDayClock clock = this.clock;
            this.readTod10th = toByte(clock.timeNano() / NANO_TO_TENTH);
            this.readTodSec = toBcd(clock.timeSeconds());
            this.readTodMin = toBcd(clock.timeMinutes());

            int hours = clock.timeHours();
            if (hours >= 12) {
                hours = CLOCK_PM.set() - 12;
            }
            this.readTodHr = toByte(hours);

            this.todReadBuffered = true;
        }
    }

    private boolean todReadBuffered;

    private void writeTod10th(final byte value) {
        final int nano = (0xf & value) * NANO_TO_TENTH;
        final int seconds = this.writeSeconds;
        final int minutes = this.writeMinutes;
        final int hours = this.writeHours;

        final TimeOfDayClock clock = this.clock;

        if (this.clockAlarmWrite()) {
            clock.setAlarm(hours, minutes, seconds, nano);
        } else {
            clock.setTime(hours, minutes, seconds, nano);
        }
    }

    private void writeTodSec(final byte value) {
        this.writeSeconds = fromBcd(value);
    }

    private void writeTodMin(final byte value) {
        this.writeMinutes = fromBcd(value);
    }

    private void writeTodHr(final byte value) {
        int hours = value;
        if (CLOCK_PM.read(value)) {
            hours = CLOCK_PM.clear(value) + 12;
        }
        this.writeHours = fromBcd(hours);
        this.writeMinutes = 0;
        this.writeSeconds = 0;
    }

    /**
     * Latched TOD write registers. The clock/alarm will be written when the TOD_TENTH register is written.
     */
    private byte writeHours = 0;
    private byte writeMinutes = 0;
    private byte writeSeconds = 0;

    private final static Bit CLOCK_PM = Bit.BIT7;

    private boolean clockRunning = true;

    private void updateClock(final int cycles) {
        if (this.clockRunning) {
            this.clock.update(cycles);
        }
    }

    private final TimeOfDayClock clock = TimeOfDayClock.with(this::clockAlarm);

    private void clockAlarm() {
        this.icrMask = TIME_ALARM_MATCH.set(this.icrMask);

        if (TIME_ALARM_MATCH.read(this.icrEnabled)) {
            this.interrupt.run();
        }
    }

    // Interrupts.......................................................................................................

    /**
     * Fires an interrupt on the CPU.
     */
    private final Runnable interrupt;

    // helpers..........................................................................................................

    private static byte fromBcd(final int value) {
        final int hi = value / 16;
        final int lo = value  & 0xf;

        return toByte(hi * 10 + lo);
    }

    private static byte toBcd(final int value) {
        final int lo = value % 10;
        final int hi = value  - lo;

        return toByte((hi / 10  * 16) + lo);
    }

    private static byte toByte(final int value) {
        return (byte) value;
    }

    private static byte toByte(final long value) {
        return (byte) value;
    }

    private static byte loByte(final int value) {
        return (byte) (value & 0xff);
    }

    private static byte hiByte(final int value) {
        return (byte) (value >> 8);
    }

    // Updatable........................................................................................................

    @Override
    public void update(final int cycles) {
        this.updateTimerA(cycles);
        this.updateTimerB(cycles);
        this.updateClock(cycles);
    }

    // ToString.........................................................................................................

    @Override
    public String toString() {
        return ToStringBuilder.empty()
                .disable(ToStringBuilderOption.QUOTE)
                //.label("portA").value(this.portA)
                //.label("portB").value(this.portB)
                .label("dataDirectionA").value(this.dataDirectionPortA)
                .label("dataDirectionB").value(this.dataDirectionPortB)
                .label("timerA").value(this.timerA)
                .label("timerB").value(this.timerB)
                .label("clock").value(this.clock)
                .label("icr mask").value(this.icrMaskToString()).label("icr enabled").value(this.icrEnabledToString())
                .label("cra").value(this.craToString())
                .label("crb").value(this.crbToString())
                .build();
    }
}
