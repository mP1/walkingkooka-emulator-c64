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
import walkingkooka.reflect.ClassTesting2;
import walkingkooka.reflect.JavaVisibility;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertSame;

public final class CpuWatchersTest implements ClassTesting2<CpuWatchers> {

    @Test
    public void testFireBreakpoint() {
        final CpuWatchers watchers = CpuWatchers.empty();

        final CpuContext context = CpuContexts.fake();

        final AtomicBoolean fired = new AtomicBoolean();
        watchers.add(
            new FakeCpuWatcher() {
                @Override
                public void onBreakpoint(final CpuContext c) {
                    assertSame(context, c);
                    fired.set(true);
                }
            }
        );

        watchers.onBreakpoint(context);

        this.checkEquals(
            true,
            fired.get()
        );
    }

    // class............................................................................................................

    @Override
    public Class<CpuWatchers> type() {
        return CpuWatchers.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }
}
