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
import walkingkooka.test.ToStringTesting;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class CiaTestCase<C extends Cia> extends AddressBusTestCase<C> implements ToStringTesting<C> {

    CiaTestCase() {
        super();
    }

    // data direction...................................................................................................

    @Test
    public final void testDataDirectionPortAReadWrite() {
        final C cia = this.createCiaInterruptFails();

        for (int i = 0; i < 256; i++) {
            this.writeAndReadCheck(cia, Cia.DDRA, (byte) i);
        }
    }

    @Test
    public final void testDataDirectionPortBReadWrite() {
        final C cia = this.createCiaInterruptFails();

        for (int i = 0; i < 256; i++) {
            this.writeAndReadCheck(cia, Cia.DDRB, (byte) i);
        }
    }

    // timer............................................................................................................

    @Test
    public final void testSetThenReadTimerA() {
        final C cia = this.createCiaInterruptFails();

        final byte hi = 2;
        final byte lo = 3;
        cia.write(Cia.TA_HI, hi);
        cia.write(Cia.TA_LO, lo);

        this.readAndCheck(cia, Cia.TA_HI, NEGATIVE_ONE);
        this.readAndCheck(cia, Cia.TA_LO, NEGATIVE_ONE);
    }

    @Test
    public final void testSetThenReadTimerAHighbitsIgnored() {
        final C cia = this.createCiaInterruptFails();

        final byte hi = 2;
        final byte lo = 3;
        cia.write(128 + Cia.TA_HI, hi);
        cia.write(64 + Cia.TA_LO, lo);

        this.readAndCheck(cia, 32 + Cia.TA_HI, NEGATIVE_ONE);
        this.readAndCheck(cia, 96 + Cia.TA_LO, NEGATIVE_ONE);
    }

    @Test
    public final void testSetThenReadTimerB() {
        final C cia = this.createCiaInterruptFails();

        final byte hi = 2;
        final byte lo = 3;
        cia.write(Cia.TB_HI, hi);
        cia.write(Cia.TB_LO, lo);

        this.readAndCheck(cia, Cia.TB_HI, NEGATIVE_ONE);
        this.readAndCheck(cia, Cia.TB_LO, NEGATIVE_ONE);
    }

    @Test
    public final void testSetThenLoadLatchTimerA() {
        final C cia = this.createCiaInterruptFails();

        final byte hi = 2;
        final byte lo = 3;
        cia.write(Cia.TA_HI, hi);
        cia.write(Cia.TA_LO, lo);

        cia.write(Cia.CRA, Cia.TIMER_LOAD_LATCH.set());

        this.readAndCheck(cia, Cia.TA_HI, hi);
        this.readAndCheck(cia, Cia.TA_LO, lo);
    }

    @Test
    public final void testSetThenLoadLatchTimerB() {
        final C cia = this.createCiaInterruptFails();

        final byte hi = 2;
        final byte lo = 3;
        cia.write(Cia.TB_HI, hi);
        cia.write(Cia.TB_LO, lo);

        cia.write(Cia.CRB, Cia.TIMER_LOAD_LATCH.set());

        this.readAndCheck(cia, Cia.TB_HI, hi);
        this.readAndCheck(cia, Cia.TB_LO, lo);
    }

    @Test
    public final void testSetThenLoadLatchUpdateTimerA() {
        final C cia = this.createCiaInterruptFails();

        final byte hi = 2;
        final byte lo = 3;
        cia.write(Cia.TA_HI, hi);
        cia.write(Cia.TA_LO, lo);

        cia.write(Cia.CRA, Cia.TIMER_LOAD_LATCH.set());

        cia.update(100000); // timerB not started wont update

        this.readAndCheck(cia, Cia.TA_HI, hi);
        this.readAndCheck(cia, Cia.TA_LO, lo);
    }

    @Test
    public final void testSetThenLoadLatchUpdateTimerB() {
        final C cia = this.createCiaInterruptFails();

        final byte hi = 2;
        final byte lo = 3;
        cia.write(Cia.TB_HI, hi);
        cia.write(Cia.TB_LO, lo);

        cia.write(Cia.CRB, Cia.TIMER_LOAD_LATCH.set());

        cia.update(100000); // timerB not started wont update

        this.readAndCheck(cia, Cia.TB_HI, hi);
        this.readAndCheck(cia, Cia.TB_LO, lo);
    }

    @Test
    public final void testSetClockrAInterruptDisabledUnderflows() {
        final C cia = this.createCiaInterruptFails();

        cia.write(Cia.ICR, ZERO); // disable interrupts

        final byte lo = 3;
        cia.write(Cia.TA_HI, ZERO);
        cia.write(Cia.TA_LO, lo);

        cia.write(Cia.CRA, Cia.TIMER_START.set(Cia.TIMER_LOAD_LATCH.set())); // start and load timer

        cia.update(6); // timer A underflows

        this.readAndCheck(cia, Cia.ICR, Cia.TIMERA_UNDERFLOW.set());
        this.readAndCheck(cia, Cia.ICR, ZERO); // timerA underflow cleared

        this.readAndCheck(cia, Cia.TA_HI, NEGATIVE_ONE);
        this.readAndCheck(cia, Cia.TA_LO, NEGATIVE_ONE);
    }

    @Test
    public final void testSetClockrBInterruptDisabledUnderflows() {
        final C cia = this.createCiaInterruptFails();

        cia.write(Cia.ICR, ZERO); // disable interrupts

        final byte lo = 3;
        cia.write(Cia.TB_HI, ZERO);
        cia.write(Cia.TB_LO, lo);
        cia.write(Cia.CRB, Cia.TIMER_START.set(Cia.TIMER_LOAD_LATCH.set())); // start and load timer

        cia.update(6); // timer B underflows

        this.readAndCheck(cia, Cia.ICR, Cia.TIMERB_UNDERFLOW.set());
        this.readAndCheck(cia, Cia.ICR, ZERO); // timerB underflow cleared

        this.readAndCheck(cia, Cia.TB_HI, NEGATIVE_ONE);
        this.readAndCheck(cia, Cia.TB_LO, NEGATIVE_ONE);
    }

    @Test
    public final void testSetClockrAInterruptDisabledReloadUnderflows() {
        final C cia = this.createCiaInterruptFails();

        cia.write(Cia.ICR, ZERO); // disable interrupts

        final byte lo = 3;
        final byte hi = 0;
        cia.write(Cia.TA_HI, hi);
        cia.write(Cia.TA_LO, lo);

        cia.write(Cia.CRA, Cia.TIMER_START.or(Cia.TIMER_LOAD_LATCH, Cia.TIMER_RESTART_AFTER_UNDERFLOW));
        cia.update(6); // timer A underflows

        this.readAndCheck(cia, Cia.ICR, Cia.TIMERA_UNDERFLOW.set());
        this.readAndCheck(cia, Cia.ICR, ZERO); // timerA underflow cleared

        this.readAndCheck(cia, Cia.TA_HI, hi);
        this.readAndCheck(cia, Cia.TA_LO, lo);
    }

    @Test
    public final void testSetClockrBInterruptDisabledReloadUnderflows() {
        final C cia = this.createCiaInterruptFails();

        cia.write(Cia.ICR, ZERO); // disable interrupts

        final byte lo = 3;
        final byte hi = 0;
        cia.write(Cia.TB_HI, hi);
        cia.write(Cia.TB_LO, lo);

        cia.write(Cia.CRB, Cia.TIMER_START.or(Cia.TIMER_LOAD_LATCH, Cia.TIMER_RESTART_AFTER_UNDERFLOW));

        cia.update(6); // timer B underflows

        this.readAndCheck(cia, Cia.ICR, Cia.TIMERB_UNDERFLOW.set());
        this.readAndCheck(cia, Cia.ICR, ZERO); // timerB underflow cleared

        this.readAndCheck(cia, Cia.TB_HI, hi);
        this.readAndCheck(cia, Cia.TB_LO, lo);
    }

    @Test
    public final void testSetClockrAInterruptDisabledOtherInterruptsEnabledUnderflows() {
        final C cia = this.createCiaInterruptFails();

        cia.write(Cia.ICR, ZERO); // disable interrupts

        final byte lo = 3;
        cia.write(Cia.TA_HI, ZERO);
        cia.write(Cia.TA_LO, lo);
        cia.write(Cia.CRA, Cia.TIMER_START.or(Cia.TIMER_LOAD_LATCH));

        cia.write(Cia.ICR, Cia.TIMERA_UNDERFLOW.not());

        cia.update(6); // timer A underflows

        this.readAndCheck(cia, Cia.ICR, Cia.TIMERA_UNDERFLOW.set());
        this.readAndCheck(cia, Cia.ICR, ZERO); // timerA underflow cleared

        this.readAndCheck(cia, Cia.TA_HI, NEGATIVE_ONE);
        this.readAndCheck(cia, Cia.TA_LO, NEGATIVE_ONE);
    }

    @Test
    public final void testSetClockrBInterruptDisabledOtherInterruptsEnabledUnderflows() {
        final C cia = this.createCiaInterruptFails();

        cia.write(Cia.ICR, ZERO); // disable interrupts

        final byte lo = 3;
        cia.write(Cia.TB_HI, ZERO);
        cia.write(Cia.TB_LO, lo);
        cia.write(Cia.CRB, Cia.TIMER_START.set(Cia.TIMER_LOAD_LATCH.set())); // start and load timer

        cia.write(Cia.ICR, Cia.TIMERB_UNDERFLOW.not());
        cia.update(6); // timer B underflows

        this.readAndCheck(cia, Cia.ICR, Cia.TIMERB_UNDERFLOW.set());
        this.readAndCheck(cia, Cia.ICR, ZERO); // timerB underflow cleared

        this.readAndCheck(cia, Cia.TB_HI, NEGATIVE_ONE);
        this.readAndCheck(cia, Cia.TB_LO, NEGATIVE_ONE);

        this.readAndCheck(cia, Cia.TB_HI, NEGATIVE_ONE);
        this.readAndCheck(cia, Cia.TB_LO, NEGATIVE_ONE);
    }

    @Test
    public final void testSetClockrAInterruptEnabledUnderflows() {
        final C cia = this.createCiaInterruptable();

        cia.write(Cia.ICR, ZERO); // disable interrupts

        final byte lo = 3;
        cia.write(Cia.TA_HI, ZERO);
        cia.write(Cia.TA_LO, lo);

        this.writeAndReadCheck(cia, Cia.CRA, Cia.TIMER_START.or(Cia.TIMER_LOAD_LATCH));

        cia.write(Cia.ICR, Cia.IRQ.or(Cia.TIMERA_UNDERFLOW)); // enable interrupt for timerA
        cia.update(6); // timer A underflows

        this.readAndCheck(cia, Cia.ICR, Cia.IRQ.set(Cia.TIMERA_UNDERFLOW.set()));
        this.readAndCheck(cia, Cia.ICR, ZERO); // timerA underflow cleared

        this.readAndCheck(cia, Cia.TA_HI, NEGATIVE_ONE);
        this.readAndCheck(cia, Cia.TA_LO, NEGATIVE_ONE);

        this.interruptCheck(cia, true);
    }

    @Test
    public final void testSetClockrBInterruptEnabledUnderflows() {
        final C cia = this.createCiaInterruptable();

        cia.write(Cia.ICR, ZERO); // disable interrupts

        final byte lo = 3;
        cia.write(Cia.TB_HI, ZERO);
        cia.write(Cia.TB_LO, lo);

        this.writeAndReadCheck(cia, Cia.CRB, Cia.TIMER_START.or(Cia.TIMER_LOAD_LATCH));

        cia.write(Cia.ICR, Cia.IRQ.or(Cia.TIMERB_UNDERFLOW)); // enable interrupt for timerB
        cia.update(6); // timer B underflows

        this.readAndCheck(cia, Cia.ICR, Cia.IRQ.set(Cia.TIMERB_UNDERFLOW.set()));
        this.readAndCheck(cia, Cia.ICR, ZERO); // timerB underflow cleared

        this.readAndCheck(cia, Cia.TB_HI, NEGATIVE_ONE);
        this.readAndCheck(cia, Cia.TB_LO, NEGATIVE_ONE);

        this.interruptCheck(cia, true);
    }

    // tod..............................................................................................................

    @Test
    public final void testSetClockHour() {
        final C cia = this.createCiaInterruptFails();

        cia.write(Cia.TOD_HR, ONE);

        this.readAndCheck(cia, Cia.TOD_HR, ZERO);
        this.readAndCheck(cia, Cia.TOD_MIN, ZERO);
        this.readAndCheck(cia, Cia.TOD_SEC, ZERO);
        this.readAndCheck(cia, Cia.TOD_10TH, ZERO);
    }

    @Test
    public final void testSetClockMinute() {
        final C cia = this.createCiaInterruptFails();

        cia.write(Cia.TOD_MIN, ONE);

        this.readAndCheck(cia, Cia.TOD_HR, ZERO);
        this.readAndCheck(cia, Cia.TOD_MIN, ZERO);
        this.readAndCheck(cia, Cia.TOD_SEC, ZERO);
        this.readAndCheck(cia, Cia.TOD_10TH, ZERO);
    }

    @Test
    public final void testSetClockSecond() {
        final C cia = this.createCiaInterruptFails();

        cia.write(Cia.TOD_SEC, ONE);

        this.readAndCheck(cia, Cia.TOD_HR, ZERO);
        this.readAndCheck(cia, Cia.TOD_MIN, ZERO);
        this.readAndCheck(cia, Cia.TOD_SEC, ZERO);
        this.readAndCheck(cia, Cia.TOD_10TH, ZERO);
    }

    @Test
    public final void testSetClockTenth() {
        final C cia = this.createCiaInterruptFails();

        cia.write(Cia.TOD_10TH, ONE);

        this.readAndCheck(cia, Cia.TOD_HR, ZERO);
        this.readAndCheck(cia, Cia.TOD_MIN, ZERO);
        this.readAndCheck(cia, Cia.TOD_SEC, ZERO);
        this.readAndCheck(cia, Cia.TOD_10TH, ONE);
    }

    @Test
    public final void testSetClockHourMinuteSecThenTenth() {
        final C cia = this.createCiaInterruptFails();

        cia.write(Cia.TOD_HR, ONE);
        cia.write(Cia.TOD_MIN, TWO);
        cia.write(Cia.TOD_SEC, THREE);
        cia.write(Cia.TOD_10TH, FOUR);

        this.readAndCheck(cia, Cia.TOD_HR, ONE);
        this.readAndCheck(cia, Cia.TOD_MIN, TWO);
        this.readAndCheck(cia, Cia.TOD_SEC, THREE);
        this.readAndCheck(cia, Cia.TOD_10TH, FOUR);
    }

    @Test
    public final void testSetClockHourMinuteSecThenTenth2() {
        final C cia = this.createCiaInterruptFails();

        cia.write(Cia.TOD_HR, ONE);
        cia.write(Cia.TOD_MIN, FIFTY_EIGHT_BCD);
        cia.write(Cia.TOD_SEC, FIFTY_NINE_BCD);
        cia.write(Cia.TOD_10TH, FOUR);

        this.readAndCheck(cia, Cia.TOD_HR, ONE);
        this.readAndCheck(cia, Cia.TOD_MIN, FIFTY_EIGHT_BCD);
        this.readAndCheck(cia, Cia.TOD_SEC, FIFTY_NINE_BCD);
        this.readAndCheck(cia, Cia.TOD_10TH, FOUR);
    }

    @Test
    public final void testSetClockThenUpdate() {
        final C cia = this.createCiaInterruptFails();

        cia.write(Cia.TOD_HR, ONE);
        cia.write(Cia.TOD_MIN, FIFTY_EIGHT_BCD);
        cia.write(Cia.TOD_SEC, FIFTY_NINE_BCD);
        cia.write(Cia.TOD_10TH, FOUR);

        cia.update(1);

        this.readAndCheck(cia, Cia.TOD_HR, ONE);
        this.readAndCheck(cia, Cia.TOD_MIN, FIFTY_EIGHT_BCD);
        this.readAndCheck(cia, Cia.TOD_SEC, FIFTY_NINE_BCD);
        this.readAndCheck(cia, Cia.TOD_10TH, FOUR);

        this.readAndCheck(cia, Cia.ICR, ZERO); // no interrupt
    }

    @Test
    public final void testSetClockThenUpdate2() {
        final C cia = this.createCiaInterruptFails();

        cia.write(Cia.TOD_HR, ONE);
        cia.write(Cia.TOD_MIN, FIFTY_EIGHT_BCD);
        cia.write(Cia.TOD_SEC, FIFTY_NINE_BCD);
        cia.write(Cia.TOD_10TH, FOUR);

        cia.update(10);

        this.readAndCheck(cia, Cia.TOD_HR, ONE);
        this.readAndCheck(cia, Cia.TOD_MIN, FIFTY_EIGHT_BCD);
        this.readAndCheck(cia, Cia.TOD_SEC, FIFTY_NINE_BCD);
        this.readAndCheck(cia, Cia.TOD_10TH, FOUR);

        this.readAndCheck(cia, Cia.ICR, ZERO); // no interrupt
    }

    @Test
    public final void testSetClockThenUpdateTenth() {
        final C cia = this.createCiaInterruptFails();

        cia.write(Cia.TOD_HR, ONE);
        cia.write(Cia.TOD_MIN, FIFTY_EIGHT_BCD);
        cia.write(Cia.TOD_SEC, FIFTY_NINE_BCD);
        cia.write(Cia.TOD_10TH, FOUR);

        cia.update(Cia.NANO_TO_TENTH);

        this.readAndCheck(cia, Cia.TOD_HR, ONE);
        this.readAndCheck(cia, Cia.TOD_MIN, FIFTY_EIGHT_BCD);
        this.readAndCheck(cia, Cia.TOD_SEC, FIFTY_NINE_BCD);
        this.readAndCheck(cia, Cia.TOD_10TH, FIVE);

        this.readAndCheck(cia, Cia.ICR, ZERO); // no interrupt
    }

    @Test
    public final void testSetClockThenUpdateTenth2() {
        final C cia = this.createCiaInterruptFails();

        cia.write(Cia.TOD_HR, ONE);
        cia.write(Cia.TOD_MIN, FIFTY_EIGHT_BCD);
        cia.write(Cia.TOD_SEC, FIFTY_NINE_BCD);
        cia.write(Cia.TOD_10TH, TWO);

        cia.update(Cia.NANO_TO_TENTH); // 3
        cia.update(Cia.NANO_TO_TENTH); // 4
        cia.update(Cia.NANO_TO_TENTH); // 5

        this.readAndCheck(cia, Cia.TOD_HR, ONE);
        this.readAndCheck(cia, Cia.TOD_MIN, FIFTY_EIGHT_BCD);
        this.readAndCheck(cia, Cia.TOD_SEC, FIFTY_NINE_BCD);
        this.readAndCheck(cia, Cia.TOD_10TH, FIVE);

        this.readAndCheck(cia, Cia.ICR, ZERO); // no interrupt
    }

    @Test
    public final void testSetClockThenUpdateSecond() {
        final C cia = this.createCiaInterruptFails();

        cia.write(Cia.TOD_HR, ONE);
        cia.write(Cia.TOD_MIN, TWO);
        cia.write(Cia.TOD_SEC, THREE);
        cia.write(Cia.TOD_10TH, FIVE);

        cia.update(Cia.NANO_TO_TENTH); // 6
        cia.update(Cia.NANO_TO_TENTH); // 7
        cia.update(Cia.NANO_TO_TENTH); // 8
        cia.update(Cia.NANO_TO_TENTH); // 9
        cia.update(Cia.NANO_TO_TENTH); // 0
        cia.update(Cia.NANO_TO_TENTH); // 1

        this.readAndCheck(cia, Cia.TOD_HR, ONE);
        this.readAndCheck(cia, Cia.TOD_MIN, TWO);
        this.readAndCheck(cia, Cia.TOD_SEC, FOUR);
        this.readAndCheck(cia, Cia.TOD_10TH, ONE);

        this.readAndCheck(cia, Cia.ICR, ZERO); // no interrupt
    }

    @Test
    public final void testSetClockThenUpdateMany() {
        final C cia = this.createCiaInterruptFails();

        cia.write(Cia.TOD_HR, ONE);
        cia.write(Cia.TOD_MIN, TWO);
        cia.write(Cia.TOD_SEC, THREE);
        cia.write(Cia.TOD_10TH, FIVE);

        for (int s = 3; s < 58; s++) {
            for (int m = 0; m < 10; m++) {
                cia.update(Cia.NANO_TO_TENTH);
            }
        }

        this.readAndCheck(cia, Cia.TOD_HR, ONE);
        this.readAndCheck(cia, Cia.TOD_MIN, TWO);
        this.readAndCheck(cia, Cia.TOD_SEC, FIFTY_EIGHT_BCD);
        this.readAndCheck(cia, Cia.TOD_10TH, FIVE);

        this.readAndCheck(cia, Cia.ICR, ZERO); // no interrupt
    }

    @Test
    public final void testSetAlarm() {
        final C cia = this.createCiaInterruptFails();

        cia.write(Cia.CRB, Cia.ALARM_WRITE.set());

        cia.write(Cia.TOD_HR, ONE);
        cia.write(Cia.TOD_MIN, TWO);
        cia.write(Cia.TOD_SEC, THREE);
        cia.write(Cia.TOD_10TH, FIVE);

        // reads are from clock
        this.readAndCheck(cia, Cia.TOD_HR, ZERO);
        this.readAndCheck(cia, Cia.TOD_MIN, ZERO);
        this.readAndCheck(cia, Cia.TOD_SEC, ZERO);
        this.readAndCheck(cia, Cia.TOD_10TH, ZERO);

        this.readAndCheck(cia, Cia.ICR, ZERO); // no interrupt
    }

    @Test
    public final void testSetTimeThenSetAlarm() {
        final C cia = this.createCiaInterruptFails();

        // clock write
        cia.write(Cia.CRB, ZERO);

        cia.write(Cia.TOD_HR, FIVE);
        cia.write(Cia.TOD_MIN, SIX);
        cia.write(Cia.TOD_SEC, SEVEN);
        cia.write(Cia.TOD_10TH, EIGHT);

        // alarm write
        cia.write(Cia.CRB, Cia.ALARM_WRITE.set());

        cia.write(Cia.TOD_HR, ONE);
        cia.write(Cia.TOD_MIN, TWO);
        cia.write(Cia.TOD_SEC, THREE);
        cia.write(Cia.TOD_10TH, FIVE);

        // reads are from clock
        this.readAndCheck(cia, Cia.TOD_HR, FIVE);
        this.readAndCheck(cia, Cia.TOD_MIN, SIX);
        this.readAndCheck(cia, Cia.TOD_SEC, SEVEN);
        this.readAndCheck(cia, Cia.TOD_10TH, EIGHT);
    }

    @Test
    public final void testSetTimeThenSetAlarmThenUpdateClock() {
        final C cia = this.createCiaInterruptFails();

        // clock write
        cia.write(Cia.CRB, ZERO);

        cia.write(Cia.TOD_HR, ONE);
        cia.write(Cia.TOD_MIN, TWO);
        cia.write(Cia.TOD_SEC, THREE);
        cia.write(Cia.TOD_10TH, FOUR);

        // alarm write
        cia.write(Cia.CRB, Cia.ALARM_WRITE.set());

        cia.write(Cia.TOD_HR, ONE);
        cia.write(Cia.TOD_MIN, TWO);
        cia.write(Cia.TOD_SEC, THREE);
        cia.write(Cia.TOD_10TH, FIVE);

        cia.update(1);

        // reads are from clock
        this.readAndCheck(cia, Cia.TOD_HR, ONE);
        this.readAndCheck(cia, Cia.TOD_MIN, TWO);
        this.readAndCheck(cia, Cia.TOD_SEC, THREE);
        this.readAndCheck(cia, Cia.TOD_10TH, FOUR);

        this.readAndCheck(cia, Cia.ICR, ZERO); // alarm not matched
    }

    @Test
    public final void testSetTimeThenSetAlarmThenUpdateTenthClockAlarmMatchInterruptDisabled() {
        final C cia = this.createCiaInterruptFails();

        // clock write
        cia.write(Cia.CRB, ZERO);

        cia.write(Cia.TOD_HR, ONE);
        cia.write(Cia.TOD_MIN, TWO);
        cia.write(Cia.TOD_SEC, THREE);
        cia.write(Cia.TOD_10TH, FOUR);

        // alarm write
        cia.write(Cia.CRB, Cia.ALARM_WRITE.set());

        cia.write(Cia.TOD_HR, ONE);
        cia.write(Cia.TOD_MIN, TWO);
        cia.write(Cia.TOD_SEC, THREE);
        cia.write(Cia.TOD_10TH, FIVE);

        cia.update(Cia.NANO_TO_TENTH);

        // reads are from clock
        this.readAndCheck(cia, Cia.TOD_HR, ONE);
        this.readAndCheck(cia, Cia.TOD_MIN, TWO);
        this.readAndCheck(cia, Cia.TOD_SEC, THREE);
        this.readAndCheck(cia, Cia.TOD_10TH, FIVE);

        this.readAndCheck(cia, Cia.ICR, Cia.TIME_ALARM_MATCH.set()); // alarm matched

        this.interruptCheck(cia, false);
    }

    @Test
    public final void testSetTimeThenSetAlarmThenUpdateTenthClockAlarmMatchInterruptEnabled() {
        final C cia = this.createCiaInterruptable();

        // clock write
        cia.write(Cia.CRB, ZERO);

        cia.write(Cia.TOD_HR, ONE);
        cia.write(Cia.TOD_MIN, TWO);
        cia.write(Cia.TOD_SEC, THREE);
        cia.write(Cia.TOD_10TH, FOUR);

        // alarm write
        cia.write(Cia.CRB, Cia.ALARM_WRITE.set());

        cia.write(Cia.TOD_HR, ONE);
        cia.write(Cia.TOD_MIN, TWO);
        cia.write(Cia.TOD_SEC, THREE);
        cia.write(Cia.TOD_10TH, FIVE);

        cia.write(Cia.ICR, Cia.IRQ.or(Cia.TIME_ALARM_MATCH)); // enable time alarm interrupt

        cia.update(Cia.NANO_TO_TENTH);

        // reads are from clock
        this.readAndCheck(cia, Cia.TOD_HR, ONE);
        this.readAndCheck(cia, Cia.TOD_MIN, TWO);
        this.readAndCheck(cia, Cia.TOD_SEC, THREE);
        this.readAndCheck(cia, Cia.TOD_10TH, FIVE);

        this.readAndCheck(cia, Cia.ICR, Cia.IRQ.or(Cia.TIME_ALARM_MATCH)); // alarm matched
        this.readAndCheck(cia, Cia.ICR, ZERO); // alarm interrupt cleared

        this.interruptCheck(cia, true);
    }

    @Test
    public final void testSetTimeThenSetAlarmThenUpdateTenthClockAlarmMatchInterruptEnabled2() {
        final C cia = this.createCiaInterruptable();

        // clock write
        cia.write(Cia.CRB, ZERO);

        cia.write(Cia.TOD_HR, ONE);
        cia.write(Cia.TOD_MIN, TWO);
        cia.write(Cia.TOD_SEC, THREE);
        cia.write(Cia.TOD_10TH, FOUR);

        // alarm write
        cia.write(Cia.CRB, Cia.ALARM_WRITE.set());

        cia.write(Cia.TOD_HR, ONE);
        cia.write(Cia.TOD_MIN, TWO);
        cia.write(Cia.TOD_SEC, THREE);
        cia.write(Cia.TOD_10TH, FIVE);

        cia.write(Cia.ICR, Cia.IRQ.or(Cia.TIME_ALARM_MATCH)); // enable time alarm interrupt

        cia.update(Cia.NANO_TO_TENTH);

        // reads are from clock
        this.readAndCheck(cia, Cia.TOD_HR, ONE);
        this.readAndCheck(cia, Cia.TOD_MIN, TWO);
        this.readAndCheck(cia, Cia.TOD_SEC, THREE);
        this.readAndCheck(cia, Cia.TOD_10TH, FIVE);

        this.readAndCheck(cia, Cia.ICR, Cia.IRQ.or(Cia.TIME_ALARM_MATCH)); // alarm matched
        this.readAndCheck(cia, Cia.ICR, ZERO); // alarm interrupt cleared

        this.interruptCheck(cia, true);

        cia.update(Cia.NANO_TO_TENTH);

        // reads are from clock
        this.readAndCheck(cia, Cia.TOD_HR, ONE);
        this.readAndCheck(cia, Cia.TOD_MIN, TWO);
        this.readAndCheck(cia, Cia.TOD_SEC, THREE);
        this.readAndCheck(cia, Cia.TOD_10TH, SIX);

        this.readAndCheck(cia, Cia.ICR, ZERO);

        this.interruptCheck(cia, false);
    }

    // toString.........................................................................................................

    @Test
    public final void testToString() {
        final C cia = this.createCiaInterruptFails();
        cia.write(Cia.ICR, ZERO);
        this.toStringAndCheck(cia, "timerA=-1/0 timerB=-1/0 clock=time: 0:00:00.0");
    }

    @Test
    public final void testToStringIcrTimerAUnderflow() {
        final C cia = this.createCiaInterruptFails();
        cia.write(Cia.ICR, Cia.IRQ.or(Cia.TIMERA_UNDERFLOW));
        this.toStringAndCheck(cia, "timerA=-1/0 timerB=-1/0 clock=time: 0:00:00.0 icr enabled=TIMERA_UNDERFLOW");
    }

    @Test
    public final void testToStringIcrTimerAUnderflowTimerBUnderflowTimeAlarm() {
        final C cia = this.createCiaInterruptFails();
        cia.write(Cia.ICR, Cia.IRQ.or(Cia.TIMERA_UNDERFLOW, Cia.TIMERB_UNDERFLOW, Cia.TIME_ALARM_MATCH));
        this.toStringAndCheck(cia, "timerA=-1/0 timerB=-1/0 clock=time: 0:00:00.0 icr enabled=TIME_ALARM_MATCH TIMERB_UNDERFLOW TIMERA_UNDERFLOW");
    }

    @Test
    public final void testToStringTimeClock() {
        final C cia = this.createCiaInterruptFails();
        cia.write(Cia.TOD_HR, ONE);
        cia.write(Cia.TOD_MIN, FIFTY_EIGHT_BCD);
        cia.write(Cia.TOD_SEC, FIFTY_NINE_BCD);
        cia.write(Cia.TOD_10TH, FOUR);

        this.toStringAndCheck(cia, "timerA=-1/0 timerB=-1/0 clock=time: 1:58:59.4");
    }

    // helpers..........................................................................................................

    final C createCiaInterruptFails() {
        return this.createCia(() -> {
            throw new UnsupportedOperationException();
        });
    }

    final C createCiaInterruptable() {
        this.interrupt = false;
        return this.createCia(this::interrupt);
    }

    final void interrupt() {
        this.interrupt = true;
    }

    final void interruptCheck(final Cia cia, final boolean interrupted) {
        assertEquals(interrupted, this.interrupt, () -> cia.toString());
        this.interrupt = false;
    }

    boolean interrupt;

    abstract C createCia(final Runnable interrupts);
}
