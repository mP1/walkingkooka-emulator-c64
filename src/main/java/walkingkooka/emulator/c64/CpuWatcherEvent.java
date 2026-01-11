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
import java.util.function.Consumer;

abstract class CpuWatcherEvent implements Consumer<CpuWatcher> {

    static CpuWatcherEventBreakpoint breakpoint(final CpuContext context) {
        return CpuWatcherEventBreakpoint.with(context);
    }

    CpuWatcherEvent(final CpuContext context) {
        super();
        this.context = Objects.requireNonNull(context, "context");
    }

    @Override
    public final void accept(final CpuWatcher watcher) {
        this.fire(watcher);
    }

    /**
     * Sub-classes should implement this method, note any caught {@link Exception} will be logged as an ERROR.
     */
    abstract void fire(final CpuWatcher watcher);

    final CpuContext context;

    // Object...........................................................................................................

    @Override
    public abstract String toString();
}
