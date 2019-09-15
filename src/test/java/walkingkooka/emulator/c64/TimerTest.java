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

public final class TimerTest implements ClassTesting2<Timer>, ToStringTesting<Timer> {

    @Test
    public void testSetLatchSetLatch() {
        final Timer timer = this.timerUnderflowFails();
        timer.setLatch(1000);
        timer.setLatch(200);

        this.checkValue(timer, 0); // setLatch doesnt load value
    }

    @Test
    public void testSetLatchReloadLatchAndUpdate() {
        final Timer timer = this.timerUnderflowFails();

        timer.setLatch(1000);
        timer.loadLatch();
        timer.update(901);

        this.checkValue(timer, 99);
    }

    @Test
    public void testSetLatchReloadLatchAndMultipleUpdates() {
        final Timer timer = this.timerUnderflowFails();

        timer.setLatch(1000);
        timer.loadLatch();

        timer.update(10);
        timer.update(200);
        this.checkValue(timer, 1000 - 10 - 200);
    }

    @Test
    public void testMultipleReloadLatchAndUpdates() {
        final Timer timer = this.timerUnderflowFails();

        timer.setLatch(1000);
        timer.loadLatch();
        timer.update(10);

        timer.setLatch(99);
        timer.loadLatch();
        this.checkValue(timer, 99);

        timer.update(77);
        this.checkValue(timer, 22);
    }

    @Test
    public void testUnderflows() {
        final Timer timer = this.timerUnderflowSupported();

        timer.setLatch(100);
        timer.loadLatch();

        timer.update(99);
        this.checkUnderflowed(timer, false);

        timer.update(1);
        this.checkUnderflowed(timer, false);

        timer.update(1);
        this.checkUnderflowed(timer, true);
        this.checkValue(timer, -1);
    }

    private Timer timerUnderflowFails() {
        return Timer.with(this::underflowFails);
    }

    private void underflowFails() {
        throw new UnsupportedOperationException();
    }

    private Timer timerUnderflowSupported() {
        this.underflowed = false;
        return Timer.with(this::underflowed);
    }

    private void underflowed() {
        this.underflowed = true;
    }

    private boolean underflowed;

    private void checkValue(final Timer timer,
                            final int value) {
        assertEquals(value, timer.value(), () -> timer.toString());
    }

    private void checkUnderflowed(final Timer timer,
                                  final boolean value) {
        assertEquals(value, this.underflowed, () -> timer.toString());
    }

    @Test
    public void testSetLatchUpdateSetLatch() {
        final Timer timer = this.timerUnderflowFails();
        timer.setLatch(1000);
        timer.loadLatch();

        timer.update(500);
        timer.setLatch(200);
        timer.loadLatch();

        this.checkValue(timer, 200);
    }

    // ToString.........................................................................................................

    @Test
    public void testToString() {
        final Timer timer = this.timerUnderflowSupported();
        timer.setLatch(100);
        timer.loadLatch();
        timer.update(98);

        this.toStringAndCheck(timer, "2/100");
    }

    // ClassTesting.....................................................................................................

    @Override
    public Class<Timer> type() {
        return Timer.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }
}
