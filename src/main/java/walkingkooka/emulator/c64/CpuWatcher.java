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

public interface CpuWatcher {

    /**
     * This is fired by the CPU when a breakpoint is encountered.
     */
    void onBreakpoint(final CpuContext context);

    /**
     * This is fired by the cpu when an invalid opcode is executed.
     */
    void onInvalidOpcode(final CpuContext context);

    /**
     * This is fired by the cpu when a NMI is executed.
     */
    void onNmi(final CpuContext context);
}
