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
import walkingkooka.reflect.ClassTesting;
import walkingkooka.reflect.JavaVisibility;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public final class CpuInstructionSharedOneTest implements ClassTesting<CpuInstructionSharedOne> {

    // asl..............................................................................................................

    @Test
    public void testAslCarryClear() {
        this.aslAndCheck(
            (byte) 0,
            "-----1--",
            (byte) 0,
            "-Z---1--"
        );
    }

    @Test
    public void testAslCarryClear2() {
        this.aslAndCheck(
            (byte) 0x80,
            "-----1--",
            (byte) 0,
            "CZ---1--"
        );
    }

    @Test
    public void testAslCarrySet() {
        this.aslAndCheck(
            (byte) 0,
            "C----1--",
            (byte) 0x00,
            "-Z------"
        );
    }

    @Test
    public void testAslCarrySet2() {
        this.aslAndCheck(
            (byte) 0x80,
            "C----1--",
            (byte) 0x00,
            "CZ------"
        );
    }

    @Test
    public void testAsl() {
        this.aslAndCheck(
            (byte) 0xF0,
            "C----1--",
            (byte) 0xE0,
            "C------N"
        );
    }

    @Test
    public void testAsl2() {
        this.aslAndCheck(
            (byte) 0x0F,
            "-----1--",
            (byte) 0x1E,
            "--------"
        );
    }

    private void aslAndCheck(final byte value,
                             final String flags,
                             final byte expectedValue,
                             final String expectedFlags) {
        this.processValueAndCheck(
            CpuInstructionSharedOne::asl,
            value,
            flags,
            expectedValue,
            expectedFlags
        );
    }

    // lsr..............................................................................................................

    @Test
    public void testLsrCarryClear() {
        this.lsrAndCheck(
            (byte) 0,
            "-----1--",
            (byte) 0,
            "-Z---1--"
        );
    }

    @Test
    public void testLsrCarryClear2() {
        this.lsrAndCheck(
            (byte) 0x01,
            "-----1--",
            (byte) 0,
            "CZ---1--"
        );
    }

    @Test
    public void testLsrCarrySet() {
        this.lsrAndCheck(
            (byte) 0,
            "C----1--",
            (byte) 0x00,
            "-Z------"
        );
    }

    @Test
    public void testLsrCarrySet2() {
        this.lsrAndCheck(
            (byte) 0x01,
            "C----1--",
            (byte) 0x00,
            "CZ------"
        );
    }

    @Test
    public void testLsr() {
        this.lsrAndCheck(
            (byte) 0xF0,
            "C----1--",
            (byte) 0x78,
            "--------"
        );
    }

    @Test
    public void testLsr2() {
        this.lsrAndCheck(
            (byte) 0x0F,
            "-----1--",
            (byte) 0x07,
            "C-------"
        );
    }

    private void lsrAndCheck(final byte value,
                             final String flags,
                             final byte expectedValue,
                             final String expectedFlags) {
        this.processValueAndCheck(
            CpuInstructionSharedOne::lsr,
            value,
            flags,
            expectedValue,
            expectedFlags
        );
    }
    
    // rol..............................................................................................................

    @Test
    public void testRolCarryClear() {
        this.rolAndCheck(
            (byte) 0,
            "-----1--",
            (byte) 0,
            "-Z---1--"
        );
    }

    @Test
    public void testRolCarryClear2() {
        this.rolAndCheck(
            (byte) 0x80,
            "-----1--",
            (byte) 0,
            "CZ---1--"
        );
    }

    @Test
    public void testRolCarrySet() {
        this.rolAndCheck(
            (byte) 0,
            "C----1--",
            (byte) 0x01,
            "--------"
        );
    }

    @Test
    public void testRolCarrySet2() {
        this.rolAndCheck(
            (byte) 0x80,
            "C----1--",
            (byte) 0x01,
            "C-------"
        );
    }

    @Test
    public void testRol() {
        this.rolAndCheck(
            (byte) 0xF0,
            "C----1--",
            (byte) 0xE1,
            "C------N"
        );
    }

    @Test
    public void testRol2() {
        this.rolAndCheck(
            (byte) 0x0F,
            "-----1--",
            (byte) 0x1E,
            "--------"
        );
    }

    private void rolAndCheck(final byte value,
                             final String flags,
                             final byte expectedValue,
                             final String expectedFlags) {
        this.processValueAndCheck(
            CpuInstructionSharedOne::rol,
            value,
            flags,
            expectedValue,
            expectedFlags
        );
    }
    
    // ror..............................................................................................................

    @Test
    public void testRorCarryClear() {
        this.rorAndCheck(
            (byte) 0,
            "-----1--",
            (byte) 0,
            "-Z---1--"
        );
    }

    @Test
    public void testRorCarryClear2() {
        this.rorAndCheck(
            (byte) 0x01,
            "-----1--",
            (byte) 0,
            "CZ---1--"
        );
    }

    @Test
    public void testRorCarrySet() {
        this.rorAndCheck(
            (byte) 0,
            "C----1--",
            (byte) 0x80,
            "-------N"
        );
    }

    @Test
    public void testRorCarrySet2() {
        this.rorAndCheck(
            (byte) 0x01,
            "C----1--",
            (byte) 0x80,
            "C------N"
        );
    }

    @Test
    public void testRor() {
        this.rorAndCheck(
            (byte) 0xFF,
            "C----1--",
            (byte) 0xFF,
            "C------N"
        );
    }

    @Test
    public void testRor2() {
        this.rorAndCheck(
            (byte) 0xF0,
            "-----1--",
            (byte) 0x78,
            "-----1--"
        );
    }

    @Test
    public void testRor3() {
        this.rorAndCheck(
            (byte) 0x0F,
            "-----1--",
            (byte) 0x07,
            "C----1--"
        );
    }

    @Test
    public void testRorBinaryDecimalModeInterruptOverflowUnchanged() {
        this.rorAndCheck(
            (byte) 0xF0,
            "-ZIDB1VN",
            (byte) 0x78,
            "--IDB1V-"
        );
    }

    private void rorAndCheck(final byte value,
                             final String flags,
                             final byte expectedValue,
                             final String expectedFlags) {
        this.processValueAndCheck(
            CpuInstructionSharedOne::ror,
            value,
            flags,
            expectedValue,
            expectedFlags
        );
    }

    private void processValueAndCheck(final BiFunction<Byte, CpuContext, Byte> function,
                                      final byte value,
                                      final String flags,
                                      final byte expectedValue,
                                      final String expectedFlags) {
        this.processValueAndCheck(
            function,
            value,
            CpuFlags.parse(flags),
            expectedValue,
            CpuFlags.parse(expectedFlags)
        );
    }

    private void processValueAndCheck(final BiFunction<Byte, CpuContext, Byte> function,
                                      final byte value,
                                      final CpuFlags flags,
                                      final byte expectedValue,
                                      final CpuFlags expectedFlags) {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.fake()
        );

        context.setFlags(
            flags.value()
        );

        this.checkEquals(
            expectedValue,
            function.apply(
                value,
                context
            ),
            () -> function + " " + Integer.toHexString(value)
        );

        final CpuFlags actualFlags = CpuFlags.create();
        actualFlags.setValue(context.flags());

        this.checkEquals(
            expectedFlags,
            actualFlags
        );
    }

    private void checkEquals(final Byte expected,
                             final Byte actual,
                             final Supplier<String> message) {
        this.checkEquals(
            "$" + Integer.toHexString(0xff & expected),
            "$" + Integer.toHexString(0xff & actual),
            message
        );
    }

    // class............................................................................................................

    @Override
    public Class<CpuInstructionSharedOne> type() {
        return CpuInstructionSharedOne.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }
}
