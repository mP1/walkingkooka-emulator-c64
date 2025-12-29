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
import walkingkooka.collect.map.Maps;
import walkingkooka.collect.set.Sets;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.reflect.MethodAttributes;
import walkingkooka.reflect.PublicStaticHelperTesting;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class CpuInstructionsTest implements PublicStaticHelperTesting<CpuInstructions> {

    @Test
    public void testInstructionsUniqueOpCodes() throws Exception {
        final Map<Byte, Set<CpuInstruction>> opcodeToCpuInstruction = Maps.sorted();

        int i = 0;

        for (final Method method : CpuInstructions.class.getDeclaredMethods()) {
            if (MethodAttributes.STATIC.is(method) && JavaVisibility.of(method) == JavaVisibility.PUBLIC) {
                if (method.getReturnType() == CpuInstruction.class && method.getParameterCount() == 0) {
                    final CpuInstruction cpuInstruction = (CpuInstruction) method.invoke(null);

                    final Byte opcode = cpuInstruction.opcode();

                    Set<CpuInstruction> same = opcodeToCpuInstruction.get(opcode);
                    if (null == same) {
                        same = Sets.hash();
                        opcodeToCpuInstruction.put(opcode, same);
                    }

                    same.add(cpuInstruction);

                    i++;
                }
            }
        }

        for (final Iterator<Set<CpuInstruction>> sames = opcodeToCpuInstruction.values().iterator(); sames.hasNext(); ) {
            final Set<CpuInstruction> same1 = sames.next();
            if (same1.size() == 1) {
                sames.remove();
            }
        }

        this.checkEquals(
            Maps.empty(),
            opcodeToCpuInstruction
        );

        this.checkNotEquals(
            0,
            i
        );
    }

    // class............................................................................................................

    @Override
    public boolean canHavePublicTypes(final Method method) {
        return false;
    }

    @Override
    public Class<CpuInstructions> type() {
        return CpuInstructions.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }
}
