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

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class CiaaTest extends CiaTestCase<Ciaa> {

    // with.............................................................................................................

    @Test
    public void testWithNullKeyPressedFails() {
        assertThrows(NullPointerException.class, () -> {
            Ciaa.with(null,
                    (r) -> {},
                    () -> {});
        });
    }

    @Test
    public void testWithNullKeyReleasedFails() {
        assertThrows(NullPointerException.class, () -> {
            Ciaa.with((p) -> {},
                    null,
                    () -> {});
        });
    }

    // portB............................................................................................................

    @Test
    public void testPortAWithoutAnyKeysPressed() {
        final Ciaa ciaa = this.createCiaInterruptFails();

        ciaa.write(Cia.DDRA, ZERO); // port A = inputs
        ciaa.write(Cia.DDRB, NEGATIVE_ONE); // port B = outputs
        ciaa.write(Cia.PRA, ZERO); // unimportant
        ciaa.write(Cia.PRB, HardwareMatrixKey.S.column().not()); // testing key column

        this.readAndCheck(ciaa, Cia.PRA, NEGATIVE_ONE);
    }

    @Test
    public void testPortAPressedKeySelectedColumnAndRow() {
        final Ciaa ciaa = this.createCiaInterruptFails();

        final HardwareMatrixKey key = HardwareMatrixKey.S;

        ciaa.write(Cia.DDRA, ZERO); // port A = inputs
        ciaa.write(Cia.DDRB, NEGATIVE_ONE); // port B = outputs
        ciaa.write(Cia.PRA, ZERO); // unimportant
        ciaa.write(Cia.PRB, key.row().not()); // testing key row

        this.keyPressed.accept(key);

        this.readAndCheck(ciaa, Cia.PRA, key.column().not()); // row cleared
    }

    @Test
    public void testPortAPressedKeySelectedColumnAndRow2() {
        final Ciaa ciaa = this.createCiaInterruptFails();

        final HardwareMatrixKey key = HardwareMatrixKey.Q;

        ciaa.write(Cia.DDRA, ZERO); // port A = inputs
        ciaa.write(Cia.DDRB, NEGATIVE_ONE); // port B = outputs
        ciaa.write(Cia.PRA, ZERO); // unimportant
        ciaa.write(Cia.PRB, key.row().not()); // testing key row

        this.keyPressed.accept(key);

        this.readAndCheck(ciaa, Cia.PRA, key.column().not()); // column cleared
    }

    @Test
    public void testPortAPressedKeyDifferentColumnAndRow() {
        final Ciaa ciaa = this.createCiaInterruptFails();

        // col=1, row=5
        final HardwareMatrixKey key = HardwareMatrixKey.S;

        ciaa.write(Cia.DDRA, ZERO); // port A = inputs
        ciaa.write(Cia.DDRB, NEGATIVE_ONE); // port B = outputs
        ciaa.write(Cia.PRA, ZERO); // unimportant
        ciaa.write(Cia.PRB, key.row().right().not()); // testing key row

        this.keyPressed.accept(key);

        this.readAndCheck(ciaa, Cia.PRA, NEGATIVE_ONE);
    }

    @Test
    public void testPortAPressedAndReleasedKeySelectedColumnAndRow() {
        final Ciaa ciaa = this.createCiaInterruptFails();

        final HardwareMatrixKey key = HardwareMatrixKey.S;

        ciaa.write(Cia.DDRA, ZERO); // port A = inputs
        ciaa.write(Cia.DDRB, NEGATIVE_ONE); // port B = outputs
        ciaa.write(Cia.PRA, ZERO); // unimportant
        ciaa.write(Cia.PRB, key.row().not()); // testing key row

        this.keyPressed.accept(key);
        this.keyReleased.accept(key);

        this.readAndCheck(ciaa, Cia.PRA, NEGATIVE_ONE);
    }
    
    // portB............................................................................................................

    @Test
    public void testPortBWithoutAnyKeysPressed() {
        final Ciaa ciaa = this.createCiaInterruptFails();

        ciaa.write(Cia.DDRA, NEGATIVE_ONE); // port A = outputs
        ciaa.write(Cia.DDRB, ZERO); // port B = inputs
        ciaa.write(Cia.PRA, Bit.BIT1.not()); // testing column 1 (COL1) of the matrix
        ciaa.write(Cia.PRB, ZERO); // unimportant

        this.readAndCheck(ciaa, Cia.PRB, NEGATIVE_ONE);
    }

    @Test
    public void testPortBPressedKeySelectedColumnAndRow() {
        final Ciaa ciaa = this.createCiaInterruptFails();

        final HardwareMatrixKey key = HardwareMatrixKey.S;

        ciaa.write(Cia.DDRA, NEGATIVE_ONE); // port A = outputs
        ciaa.write(Cia.DDRB, ZERO); // port B = inputs
        ciaa.write(Cia.PRA, key.column().not()); // testing key column 1 (COL1) of the matrix
        ciaa.write(Cia.PRB, ZERO); // unimportant

        this.keyPressed.accept(key);

        this.readAndCheck(ciaa, Cia.PRB, key.row().not()); // row cleared
    }

    @Test
    public void testPortBPressedKeySelectedColumnAndRow2() {
        final Ciaa ciaa = this.createCiaInterruptFails();

        final HardwareMatrixKey key = HardwareMatrixKey.Q;

        ciaa.write(Cia.DDRA, NEGATIVE_ONE); // port A = outputs
        ciaa.write(Cia.DDRB, ZERO); // port B = inputs
        ciaa.write(Cia.PRA, key.column().not()); // testing column key column
        ciaa.write(Cia.PRB, ZERO); // unimportant

        this.keyPressed.accept(key);

        this.readAndCheck(ciaa, Cia.PRB, key.row().not()); // row cleared
    }

    @Test
    public void testPortBPressedKeyDifferentColumnAndRow() {
        final Ciaa ciaa = this.createCiaInterruptFails();

        // col=1, row=5
        final HardwareMatrixKey key = HardwareMatrixKey.S;

        ciaa.write(Cia.DDRA, NEGATIVE_ONE); // port A = outputs
        ciaa.write(Cia.DDRB, ZERO); // port B = inputs
        ciaa.write(Cia.PRA, key.column().right().not()); // testing different column
        ciaa.write(Cia.PRB, ZERO); // unimportant

        this.keyPressed.accept(key);

        this.readAndCheck(ciaa, Cia.PRB, NEGATIVE_ONE);
    }

    @Test
    public void testPortBPressedAndReleasedKeySelectedColumnAndRow() {
        final Ciaa ciaa = this.createCiaInterruptFails();

        final HardwareMatrixKey key = HardwareMatrixKey.S;

        ciaa.write(Cia.DDRA, NEGATIVE_ONE); // port A = outputs
        ciaa.write(Cia.DDRB, ZERO); // port B = inputs
        ciaa.write(Cia.PRA, key.column().not()); // testing key column
        ciaa.write(Cia.PRB, ZERO); // unimportant

        this.keyPressed.accept(key);
        this.keyReleased.accept(key);

        this.readAndCheck(ciaa, Cia.PRB, NEGATIVE_ONE);
    }

    // helpers..........................................................................................................

    @Override
    Ciaa createCia(final Runnable interrupts) {
        return Ciaa.with((p) -> this.keyPressed = p,
                (r) -> this.keyReleased = r,
                interrupts);
    }

    private Consumer<HardwareMatrixKey> keyPressed;
    private Consumer<HardwareMatrixKey> keyReleased;

    @Override
    public Class<Ciaa> type() {
        return Ciaa.class;
    }
}
