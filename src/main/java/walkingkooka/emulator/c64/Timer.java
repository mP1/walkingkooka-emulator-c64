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
 * One of the individual 16-bit {@link Timer timers} within a  CIA chip.
 * The given {@link Runnable} will be called upon underflow, the latch value is not reloaded on underflow.
 * <br>
 * Numerous features of a CIA chip related to timers will have to be emulated by the chip and not this timer.
 * These includes:
 * <ul>
 * <li>Updating one timer on underflow of another</li>
 * <li>Firing interrupts to the CPU if they are enabled</li>
 * <li>Start and stopping the timer</li>
 * <li>Any write to bit(s) that is enabled by writing to a non Timer register</li>
 * </ul>
 */
final class Timer implements Updatable {

    /**
     * The initial default value for a {@link Timer} without any {@link #loadLatch()}.
     */
    final static int DEFAULT_VALUE = -1;

    static Timer with(final Runnable underflow) {
        Objects.requireNonNull(underflow, "underflow");

        return new Timer(underflow);
    }

    private Timer(final Runnable underflow) {
        super();
        this.underflow = underflow;
    }

    /**
     * Sets the start of latch value of the countdown.
     */
    void setLatch(final int latch) {
        this.latch = latch;
    }

    private int latch;

    /**
     * Sets the value to the latch
     */
    void loadLatch() {
        this.value = this.latch;
    }

    @Override
    public void update(final int cycles) {
        final int before = this.value;
        if (before >= 0) {
            this.value = before - cycles;
            if (this.value < 0) {
                this.value = -1;
                this.underflow.run();
            }
        }
    }

    /**
     * Reads the current countdown value.
     */
    int value() {
        return this.value;
    }

    /**
     * The count down value in ticks
     */
    private int value = DEFAULT_VALUE;

    /**
     * A {@link Runnable} that is notified when an underflow occurs.
     */
    private final Runnable underflow;

    // Object...........................................................................................................

    @Override
    public String toString() {
        return this.value + "/" + this.latch;
    }
}
