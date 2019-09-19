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

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * The first CIA.
 */
final class Ciaa extends Cia {

    /**
     * Creates a {@link Ciaa}.
     * Note the two {@link Consumer} are used to register listeners both key press and key release events after the AWT
     * key codes have been translated into {@link HardwareMatrixKey keys}.
     */
    static Ciaa with(final Consumer<Consumer<HardwareMatrixKey>> keyPress,
                     final Consumer<Consumer<HardwareMatrixKey>> keyRelease,
                     final Runnable interrupt) {
        Objects.requireNonNull(keyPress, "keyPress");
        Objects.requireNonNull(keyRelease, "keyRelease");
        Objects.requireNonNull(interrupt, "interrupt");

        return new Ciaa(keyPress,
                keyRelease,
                interrupt);
    }

    private Ciaa(final Consumer<Consumer<HardwareMatrixKey>> keyPress,
                 final Consumer<Consumer<HardwareMatrixKey>> keyRelease,
                 final Runnable interrupt) {
        super(interrupt);

        keyPress.accept(this.keysDown::add);
        keyRelease.accept(this.keysDown::remove);
    }

    /**
     * Tracks keys that are currently down or pressed; When the keyboard matrix is scanned the selected column/row
     * will be used to retrieve keys that are "down".
     */
    private final EnumSet<HardwareMatrixKey> keysDown = EnumSet.noneOf(HardwareMatrixKey.class);

    // PRA
    @Override
    byte readDataPortA() {
        return this.readKeyboardRowKeys();
    }

    // PRB
    @Override
    byte readDataPortB() {
        return this.readKeyboardColumnKeys();
    }

    private byte readKeyboardRowKeys() {
        return readKeyboardMatrix(this.dataDirectionPortB,
                this.dataDirectionPortA,
                this.dataPortB,
                KEYS_IN_ROW,
                KEY_COLUMN);
    }

    private final static Function<Bit, Set<HardwareMatrixKey>> KEYS_IN_ROW = HardwareMatrixKey::keysInRow;
    private final static Function<HardwareMatrixKey, Bit> KEY_COLUMN = HardwareMatrixKey::column;

    private byte readKeyboardColumnKeys() {
        return this.readKeyboardMatrix(this.dataDirectionPortA,
                this.dataDirectionPortB,
                this.dataPortA,
                KEYS_IN_COLUMN,
                KEY_ROW);
    }

    private final static Function<Bit, Set<HardwareMatrixKey>> KEYS_IN_COLUMN = HardwareMatrixKey::keysInColumn;
    private final static Function<HardwareMatrixKey, Bit> KEY_ROW = HardwareMatrixKey::row;

    private byte readKeyboardMatrix(final byte outputs,
                                    final byte inputs,
                                    final byte selected,
                                    final Function<Bit, Set<HardwareMatrixKey>> keysForLine,
                                    final Function<HardwareMatrixKey, Bit> keySelect) {
        byte value = -1;

        for (Bit bit : Bit.values()) {
            // OUTPUT when true, INPUT when false, COL/ROW selected when false
            if (bit.read(outputs) && false == bit.read(inputs) && false == bit.read(selected)) {
                // read all keys for COL $bit
                for (HardwareMatrixKey key : keysForLine.apply(bit)) {
                    if (this.keysDown.contains(key)) {
                        value &= keySelect.apply(key).not(); // clear bit if key down
                    }
                }
            }
        }

        return value;
    }

    @Override
    void writeDataPortA(final byte value) {
        this.dataPortA = value;
    }

    /**
     * A true selects that bit for output, and false for input
     */
    private byte dataPortA;

    @Override
    void writeDataPortB(final byte value) {
        this.dataPortB = value;
    }

    /**
     * A true selects that bit for output, and false for input
     */
    private byte dataPortB;
}
