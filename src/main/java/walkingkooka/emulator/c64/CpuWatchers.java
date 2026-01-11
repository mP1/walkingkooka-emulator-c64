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

import walkingkooka.watch.Watchers;

import java.util.Objects;

/**
 * A collection of {@link CpuWatcher} with support for firing different types of events.
 */
public class CpuWatchers implements CpuWatcher {

    public static CpuWatchers empty() {
        return new CpuWatchers();
    }

    private CpuWatchers() {
        super();
    }

    public final Runnable add(final CpuWatcher watcher) {
        Objects.requireNonNull(watcher, "watcher");

        return this.watchers.add(
            (e) -> e.accept(watcher)
        );
    }

    // CpuWatcher.......................................................................................................

    @Override
    public void onBreakpoint(final CpuContext context) {
        this.fire(
            CpuWatcherEventBreakpoint.with(context)
        );
    }

    @Override
    public void onInvalidOpcode(final CpuContext context) {
        this.fire(
            CpuWatcherEventInvalidOpcode.with(context)
        );
    }

    @Override
    public void onNmi(final CpuContext context) {
        this.fire(
            CpuWatcherEvent.nmi(context)
        );
    }

    final void fire(final CpuWatcherEvent event) {
        this.watchers.accept(event);
    }

    private final Watchers<CpuWatcherEvent> watchers = Watchers.create();

    // Object...........................................................................................................

    @Override
    public final String toString() {
        return this.watchers.toString();
    }
}
