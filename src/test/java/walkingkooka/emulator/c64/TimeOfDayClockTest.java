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
import walkingkooka.test.ClassTesting2;
import walkingkooka.test.ToStringTesting;
import walkingkooka.type.JavaVisibility;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class TimeOfDayClockTest implements ClassTesting2<TimeOfDayClock>, ToStringTesting<TimeOfDayClock> {

    // definition copied from LocalTime
    private final static int SECOND = 1000_000_000;

    @Test
    public void testSetTimeAndUpdateOneNano() {
        final TimeOfDayClock clock = this.timeOfDayAlarmFails();
        clock.setTime(0, 0, 0, 0);
        clock.update(1);

        this.checkTime(clock, 0, 0, 0, 1);
        this.checkAlarmDisabled(clock);
    }

    @Test
    public void testSetTimeAndUpdateOneSecond() {
        final TimeOfDayClock clock = this.timeOfDayAlarmFails();
        clock.setTime(0, 0, 0, 0);
        clock.update(SECOND);

        this.checkTime(clock, 0, 0, 1, 0);
        this.checkAlarmDisabled(clock);
    }

    @Test
    public void testSetTimeAndMultipleUpdates() {
        final TimeOfDayClock clock = this.timeOfDay();
        clock.setTime(12, 34, 56, 7);

        for (int s = 0; s < 61; s++) {
            clock.update(SECOND); // 1 second
        }

        this.checkTime(clock, 12, 34 + 1, 56 + 1, 7);
        this.checkAlarmDisabled(clock);
    }

    @Test
    public void testUpdateMidnightWrap() {
        final TimeOfDayClock clock = this.timeOfDay();
        clock.setTime(23, 59, 59, 0);
        clock.update(2 * SECOND); // 2 seconds

        this.checkTime(clock, 0, 0, 1, 0);
        this.checkAlarmDisabled(clock);
    }

    @Test
    public void testSetAlarmAndUpdates() {
        final TimeOfDayClock clock = this.timeOfDay();
        clock.setTime(12, 0, 0, 0);
        clock.setAlarm(6, 0, 0, 0);
        clock.update(1);

        this.checkTime(clock, 12, 0, 0, 1);
        this.checkAlarm(clock, 6, 0, 0, 0);
    }

    @Test
    public void testSetAlarmAndUpdates2() {
        final TimeOfDayClock clock = this.timeOfDay();
        clock.setTime(3, 0, 0, 0);
        clock.setAlarm(6, 0, 0, 0);

        for (int m = 0; m < 60; m++) {
            for (int s = 0; s < 60; s++) {
                clock.update(SECOND); // 1 second
            }
        }

        this.checkTime(clock, 4, 0, 0, 0);
        this.checkAlarm(clock, 6, 0, 0, 0);
    }

    @Test
    public void testSetAlarmAndUpdatesFired() {
        final TimeOfDayClock clock = this.timeOfDay();

        clock.setTime(12, 0, 0, 0);
        clock.setAlarm(12, 0, 1, 0);
        clock.update(1);
        this.checkAlarmFired(clock, false);

        clock.update(SECOND);
        this.checkAlarmFired(clock, true);

        this.checkTime(clock, 12, 0, 1, 1);
        this.checkAlarm(clock, 12, 0, 1, 0);

        clock.update(SECOND);
        this.checkAlarmFired(clock, false);
    }

    private TimeOfDayClock timeOfDayAlarmFails() {
        return TimeOfDayClock.with(() -> {
            throw new UnsupportedOperationException();
        });
    }

    private TimeOfDayClock timeOfDay() {
        this.alarm = false;
        return TimeOfDayClock.with(() -> alarm = true);
    }

    private boolean alarm;

    private void checkTime(final TimeOfDayClock clock,
                           final int hours,
                           final int minutes,
                           final int seconds,
                           final int nanos) {
        assertEquals(hours, clock.timeHours(), () -> "time hours " + clock);
        assertEquals(minutes, clock.timeMinutes(), () -> "time minutes " + clock);
        assertEquals(seconds, clock.timeSeconds(), () -> "time seconds " + clock);
        assertEquals(nanos, clock.timeNano(), () -> "time nano " + clock);
    }

    private void checkAlarmDisabled(final TimeOfDayClock clock) {
        this.checkAlarm(clock, -1, -1, -1, -1);
    }

    private void checkAlarm(final TimeOfDayClock clock,
                            final int hours,
                            final int minutes,
                            final int seconds,
                            final int nanos) {
        assertEquals(hours, clock.alarmHours(), () -> "alarm hours " + clock);
        assertEquals(minutes, clock.alarmMinutes(), () -> "alarm minutes " + clock);
        assertEquals(seconds, clock.alarmSeconds(), () -> "alarm seconds " + clock);
        assertEquals(nanos, clock.alarmNano(), () -> "alarm nano " + clock);
    }

    private void checkAlarmFired(final TimeOfDayClock clock,
                                 final boolean alarm) {
        assertEquals(alarm, this.alarm, () -> clock.toString());
        this.alarm = false;
    }

    // ToString.........................................................................................................

    @Test
    public void testToStringTimeNano() {
        final TimeOfDayClock clock = this.timeOfDay();
        clock.setTime(0, 0, 0, SECOND / 2);

        this.toStringAndCheck(clock, "time: 0:00:00.5");
    }

    @Test
    public void testToStringTimeSeconds() {
        final TimeOfDayClock clock = this.timeOfDay();
        clock.setTime(0, 0, 12, SECOND / 2);

        this.toStringAndCheck(clock, "time: 0:00:12.5");
    }

    @Test
    public void testToStringTimeMinutes() {
        final TimeOfDayClock clock = this.timeOfDay();
        clock.setTime(0, 12, 34, SECOND / 2);

        this.toStringAndCheck(clock, "time: 0:12:34.5");
    }

    @Test
    public void testToStringTime2() {
        final TimeOfDayClock clock = this.timeOfDay();
        clock.setTime(1, 12, 34, SECOND / 2);

        this.toStringAndCheck(clock, "time: 1:12:34.5");
    }

    @Test
    public void testToStringTime3() {
        final TimeOfDayClock clock = this.timeOfDay();
        clock.setTime(12, 34, 56, SECOND / 10);

        this.toStringAndCheck(clock, "time: 12:34:56.1");
    }

    @Test
    public void testToStringTime4() {
        final TimeOfDayClock clock = this.timeOfDay();
        clock.setTime(23, 45, 1, 0);

        this.toStringAndCheck(clock, "time: 23:45:01.0");
    }

    @Test
    public void testToStringTimeAndAlarm() {
        final TimeOfDayClock clock = this.timeOfDay();
        clock.setTime(23, 45, 1, 0);
        clock.setAlarm(12, 34, 56, SECOND / 10);

        this.toStringAndCheck(clock, "time: 23:45:01.0, alarm: 12:34:56.1");
    }

    // ClassTesting.....................................................................................................

    @Override
    public Class<TimeOfDayClock> type() {
        return TimeOfDayClock.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }
}
