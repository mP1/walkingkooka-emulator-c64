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
import walkingkooka.collect.map.Maps;
import walkingkooka.collect.set.SetTesting;
import walkingkooka.collect.set.Sets;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class HardwareMatrixKeyTest implements SetTesting {
    @Test
    public void testKeysInColumns() {
        for (Bit bit : Bit.values()) {
            final Set<HardwareMatrixKey> keys = HardwareMatrixKey.keysInColumn(bit);
            assertEquals(8, keys.size(), () -> "keys for " + bit);
        }
    }

    @Test
    public void testKeysInRows() {
        for (Bit bit : Bit.values()) {
            final Set<HardwareMatrixKey> keys = HardwareMatrixKey.keysInRow(bit);
            assertEquals(8, keys.size(), () -> "keys for " + bit);
        }
    }

    @Test
    public void testAllKeysHaveUniqueColumnAndRow() {
        final Map<String, Set<HardwareMatrixKey>> columnAndRowToKeys = Maps.ordered();

        for (HardwareMatrixKey key : HardwareMatrixKey.values()) {
            final String columnAndRow = key.column() + "," + key.row();
            Set<HardwareMatrixKey> keys = columnAndRowToKeys.get(columnAndRow);
            if (null == keys) {
                keys = Sets.ordered();
                columnAndRowToKeys.put(columnAndRow, keys);
            }
            keys.add(key);
        }

        assertEquals(64, columnAndRowToKeys.size(), "Matrix should have 64 keys");

        final List<HardwareMatrixKey> duplicates = Lists.array();
        columnAndRowToKeys.values()
            .forEach(keys -> {
                if (keys.size() > 1) {
                    duplicates.addAll(keys);
                }
            });

        assertEquals(Lists.empty(),
            duplicates,
            "Keys with column/rows also used by other keys");
    }
}
