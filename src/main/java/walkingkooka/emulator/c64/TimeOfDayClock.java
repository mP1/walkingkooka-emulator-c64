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

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Represents the time of day clock within a CIA chip. The alarm test is always performed after an update, and the watcher
 * notified when a match happens.
 * <br>
 * Related features for TOD that must be done externally in the CIA chip itself include:
 * <ul>
 * <li>Firing the CPU interrupt when the alarm is matched</li>
 * <li>Disabling or ignoring alarm events</li>
 * <li>Writes and latching to the TOD register</li>
 * <li>Reading and formatting the value and alarm from/to with millis</li>
 * </ul>
 */
final class TimeOfDayClock implements Updatable {

    /**
     * Creates a new {@link TimeOfDayClock}.
     */
    static TimeOfDayClock with(final Runnable watcher) {
        Objects.requireNonNull(watcher, "watcher");

        return new TimeOfDayClock(watcher);
    }

    private TimeOfDayClock(final Runnable watcher) {
        super();
        this.watcher = watcher;
    }

    @Override
    public void update(final int cycles) {
        long before = this.time;
        final long alarm = this.alarm;

        if (before > alarm) {
            before = before + cycles;
        } else {
            before = before + cycles;
            if (before > alarm) {
                this.watcher.run();
            }
        }

        if (before > TICKS_DAY) {
            before = before - TICKS_DAY;
        }
        this.time = before;
    }

    int timeHours() {
        return extractHours(this.time);
    }

    int timeMinutes() {
        return extractMinutes(this.time);
    }

    int timeSeconds() {
        return extractSeconds(this.time);
    }

    int timeNano() {
        return extractNano(this.time);
    }

    void setTime(final int hours,
                  final int minutes,
                  final int seconds,
                  final int nano) {
        this.time = computeTicks(hours, minutes, seconds, nano);
    }

    private long time;

    int alarmHours() {
        final long alarm = this.alarm;
        return -1 == alarm ?
                -1 :
                extractHours(alarm);
    }

    int alarmMinutes() {
        final long alarm = this.alarm;
        return -1 == alarm ?
                -1 :
                extractMinutes(alarm);
    }

    int alarmSeconds() {
        final long alarm = this.alarm;
        return -1 == alarm ?
                -1 :
                extractSeconds(alarm);
    }

    int alarmNano() {
        final long alarm = this.alarm;
        return -1 == alarm ?
                -1 :
                extractNano(alarm);
    }

    void setAlarm(final int hours,
                 final int minutes,
                 final int seconds,
                 final int nano) {
        this.alarm = computeTicks(hours, minutes, seconds, nano);
    }

    /**
     * The alarm value which when matched with the time will fire the listener.
     */
    private long alarm = -1;

    // helpers..........................................................................................................

    private static int extractHours(final long value) {
        return (int)(value / HOURS ) % 24;
    }

    private static int extractMinutes(final long value) {
        return (int)(value / MINUTES ) % 60;
    }

    private static int extractSeconds(final long value) {
        return (int)(value / SECONDS ) % 60;
    }

    private static int extractNano(final long value) {
        return (int)(value % SECONDS);
    }

    private static long computeTicks(final int hours,
                                    final int minutes,
                                    final int seconds,
                                    final int nano) {
        return (hours * HOURS +
                minutes * MINUTES +
                seconds * SECONDS +
                nano * NANO) % TICKS_DAY;
    }

    private final static long NANO = 1;

    private final static long SECONDS = 1000_000_000;

    private final static long MINUTES = 60 * SECONDS;

    private final static long HOURS = 60 * MINUTES;

    /**
     * The number of ticks in a day used to overflow the clock value.
     */
    private final static long TICKS_DAY = 24 * HOURS;

    /**
     * Fired when the value and alarm match.
     */
    private final Runnable watcher;

    // toString.........................................................................................................

    @Override
    public String toString() {
        final ToStringBuilder b = ToStringBuilder.empty()
                .disable(ToStringBuilderOption.QUOTE)
                .labelSeparator(": ")
                .separator(", ");

        b.label("time");
        b.value(TIME_FORMATTER.format(LocalTime.of(this.timeHours(),
                this.timeMinutes(),
                this.timeSeconds(),
                this.timeNano())));

        final long alarm = this.alarm;
        if (-1 != alarm) {
            b.label("alarm");
            b.value(TIME_FORMATTER.format(LocalTime.of(this.alarmHours(),
                    this.alarmMinutes(),
                    this.alarmSeconds(),
                    this.alarmNano())));
        }

        return b.build();
    }

    private final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("H:mm:ss.S");
}
