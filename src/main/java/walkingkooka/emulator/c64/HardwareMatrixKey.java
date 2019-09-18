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

import walkingkooka.collect.list.Lists;
import walkingkooka.collect.map.Maps;
import walkingkooka.collect.set.Sets;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Mapping all keyboard keys, with each knowing their keyboard row and column.
 */
enum HardwareMatrixKey {
    // ROW7
    STOP(Bit.BIT7, Bit.BIT7),
    SLASH(Bit.BIT7, Bit.BIT6),
    COMMA(Bit.BIT7, Bit.BIT5),
    N(Bit.BIT7, Bit.BIT4),
    V(Bit.BIT7, Bit.BIT3),
    X(Bit.BIT7, Bit.BIT2),
    LEFT_SHIFT(Bit.BIT7, Bit.BIT1),
    DOWN(Bit.BIT7, Bit.BIT0),

    // ROW6
    Q(Bit.BIT6, Bit.BIT7),
    CAROT(Bit.BIT6, Bit.BIT6),
    ATSIGN(Bit.BIT6, Bit.BIT5),
    O(Bit.BIT6, Bit.BIT4),
    U(Bit.BIT6, Bit.BIT3),
    T(Bit.BIT6, Bit.BIT2),
    E(Bit.BIT6, Bit.BIT1),
    F5(Bit.BIT6, Bit.BIT0),

    // ROW5
    COMMODORE(Bit.BIT5, Bit.BIT7),
    EQUALS(Bit.BIT5, Bit.BIT6),
    COLON(Bit.BIT5, Bit.BIT5),
    K(Bit.BIT5, Bit.BIT4),
    H(Bit.BIT5, Bit.BIT3),
    F(Bit.BIT5, Bit.BIT2),
    S(Bit.BIT5, Bit.BIT1),
    F3(Bit.BIT5, Bit.BIT0),

    // ROW4
    SPACE(Bit.BIT4, Bit.BIT7),
    RIGHT_SHIFT(Bit.BIT4, Bit.BIT6),
    DOT(Bit.BIT4, Bit.BIT5),
    M(Bit.BIT4, Bit.BIT4),
    B(Bit.BIT4, Bit.BIT3),
    C(Bit.BIT4, Bit.BIT2),
    Z(Bit.BIT4, Bit.BIT1),
    F1(Bit.BIT4, Bit.BIT0),

    // ROW3
    TWO(Bit.BIT3, Bit.BIT7),
    HOME(Bit.BIT3, Bit.BIT6),
    DASH(Bit.BIT3, Bit.BIT5),
    ZERO(Bit.BIT3, Bit.BIT4),
    EIGHT(Bit.BIT3, Bit.BIT3),
    SIX(Bit.BIT3, Bit.BIT2),
    FOUR(Bit.BIT3, Bit.BIT1),
    F7(Bit.BIT3, Bit.BIT0),

    // ROW2
    CONTROL(Bit.BIT2, Bit.BIT7),
    SEMICOLON(Bit.BIT2, Bit.BIT6),
    L(Bit.BIT2, Bit.BIT5),
    J(Bit.BIT2, Bit.BIT4),
    G(Bit.BIT2, Bit.BIT3),
    D(Bit.BIT2, Bit.BIT2),
    A(Bit.BIT2, Bit.BIT1),
    RIGHT(Bit.BIT2, Bit.BIT0),

    // ROW1
    ESC(Bit.BIT1, Bit.BIT7),
    STAR(Bit.BIT1, Bit.BIT6),
    P(Bit.BIT1, Bit.BIT5),
    I(Bit.BIT1, Bit.BIT4),
    Y(Bit.BIT1, Bit.BIT3),
    R(Bit.BIT1, Bit.BIT2),
    W(Bit.BIT1, Bit.BIT1),
    RETURN(Bit.BIT1, Bit.BIT0),

    // ROW0
    ONE(Bit.BIT0, Bit.BIT7),
    POUND(Bit.BIT0, Bit.BIT6),
    PLUS(Bit.BIT0, Bit.BIT5),
    NINE(Bit.BIT0, Bit.BIT4),
    SEVEN(Bit.BIT0, Bit.BIT3),
    FIVE(Bit.BIT0, Bit.BIT2),
    THREE(Bit.BIT0, Bit.BIT1),
    DELETE(Bit.BIT0, Bit.BIT0);

    HardwareMatrixKey(final Bit row, final Bit column) {
        this.row = row;
        this.column = column;
    }

    Bit row() {
        return this.row;
    }

    private final Bit row;

    Bit column() {
        return this.column;
    }

    private final Bit column;

    /**
     * Returns all the keys in the given column.
     */
    static Set<HardwareMatrixKey> keysInColumn(final Bit column) {
        return COLUMNS.get(column.number());
    }

    private static final List<Set<HardwareMatrixKey>> COLUMNS;

    /**
     * Returns all the keys in the given row.
     */
    static Set<HardwareMatrixKey> keysInRow(final Bit row) {
        return ROWS.get(row.number());
    }

    private static final List<Set<HardwareMatrixKey>> ROWS;

    /**
     * Builds the {@link #COLUMNS} and {@link #ROWS} lookup filtered lists.
     */
    static {
        // row -> key
        // column -> key
        final Map<Bit, Set<HardwareMatrixKey>> rowToKeys = Maps.ordered();
        final Map<Bit, Set<HardwareMatrixKey>> columnToKeys = Maps.ordered();

        final List<Set<HardwareMatrixKey>> columns = Lists.array();
        final List<Set<HardwareMatrixKey>> rows = Lists.array();

        for (Bit bit : Bit.values()) {
            rowToKeys.put(bit, Sets.sorted());
            columnToKeys.put(bit, Sets.sorted());

            columns.add(null);
            rows.add(null);
        }

        for (HardwareMatrixKey key : values()) {
            rowToKeys.get(key.row).add(key);
            columnToKeys.get(key.column).add(key);
        }

        for (Bit bit : Bit.values()) {
            final Set<HardwareMatrixKey> columnKeys = columnToKeys.get(bit);
            final Set<HardwareMatrixKey> rowKeys = rowToKeys.get(bit);

            final int number = bit.number();
            columns.set(number, Sets.readOnly(EnumSet.copyOf(columnKeys)));
            rows.set(number, Sets.readOnly(EnumSet.copyOf(rowKeys)));
        }

        ROWS = rows;
        COLUMNS = columns;
    }
}
