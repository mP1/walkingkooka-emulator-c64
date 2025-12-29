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
import walkingkooka.collect.list.Lists;
import walkingkooka.reflect.JavaVisibility;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class BasicCpuTest implements CpuTesting<BasicCpu> {

    // with.............................................................................................................

    @Test
    public void testWithNullInstructionsFails() {
        assertThrows(
            NullPointerException.class,
            () -> BasicCpu.with(null)
        );
    }

    // Cpu..............................................................................................................

    @Override
    public BasicCpu createCpu() {
        final List<CpuInstruction> instructions = Lists.array();
        for (int i = 0; i < 256; i++) {
            final byte opcode = (byte) i;

            instructions.add(
                new FakeCpuInstruction() {
                    @Override
                    public byte opcode() {
                        return opcode;
                    }

                    @Override
                    public int length() {
                        return 1;
                    }

                    @Override
                    public void execute(final CpuContext context) {

                    }
                }
            );
        }

        return BasicCpu.with(instructions);
    }

    @Override
    public CpuContext createCpuContext() {
        return CpuContexts.basic(
            AddressBuses.memory(256 * 256)
        );
    }

    // class............................................................................................................

    @Override
    public Class<BasicCpu> type() {
        return BasicCpu.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }
}
