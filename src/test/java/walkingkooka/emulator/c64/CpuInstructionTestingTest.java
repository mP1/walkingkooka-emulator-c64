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
import walkingkooka.emulator.c64.CpuInstructionTestingTest.TestCpuInstruction;

import java.util.Objects;
import java.util.function.Consumer;

public final class CpuInstructionTestingTest implements CpuInstructionTesting<TestCpuInstruction> {

    private final static short PC = (short) 0xff00;

    private final static short PC_PLUS_1 = (short) (PC + 1);

    // executeBranchOrJumpAndCheck......................................................................................

    @Test
    public void testExecuteBranchOrJumpAndCheck() {
        final short begin = 0x1000;
        final byte length = 5;

        final CpuContext context = CpuContexts.basic(
            AddressBuses.fake()
        );
        context.setPc(begin);

        this.executeBranchOrJumpAndCheck(
            new TestCpuInstruction(
                length,
                (c) -> c.setPc(
                    (short) (c.pc() + length)
                )
            ),
            context,
            (short) (begin + length)
        );
    }

    // executeAccumulatorAndCheck.......................................................................................

    @Test
    public void testExecuteAccumulatorAndCheckWithDifferentValueFails() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.fake()
        );
        context.setA((byte) 0x11);
        context.setPc(PC);

        final byte value = (byte) 0x22;

        final CpuFlags flags = CpuFlags.create();
        flags.setBreak(true);

        this.runAndThrows(
            () -> this.executeAccumulatorAndCheck(
                new TestCpuInstruction(
                    1,
                    (c) -> {
                        //c.setA(value);
                        c.setBreak(true);
                        c.setPc(PC_PLUS_1);
                    }
                ),
                context,
                value,
                flags
            )
        );
    }

    @Test
    public void testExecuteAccumulatorAndCheckWithDifferentFlagsFails() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.fake()
        );
        context.setA((byte) 0x11);
        context.setPc(PC);

        final byte value = (byte) 0x22;

        final CpuFlags flags = CpuFlags.create();

        this.runAndThrows(
            () -> this.executeAccumulatorAndCheck(
                new TestCpuInstruction(
                    1,
                    (c) -> {
                        c.setA(value);
                        c.setBreak(true);
                        c.setPc(PC_PLUS_1);
                    }
                ),
                context,
                value,
                flags
            )
        );
    }

    @Test
    public void testExecuteAccumulatorAndCheckWithDifferentPcFails() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.fake()
        );
        context.setA((byte) 0x11);
        context.setPc(PC);

        final byte value = (byte) 0x22;

        final CpuFlags flags = CpuFlags.create();

        this.runAndThrows(
            () -> this.executeAccumulatorAndCheck(
                new TestCpuInstruction(
                    1,
                    (c) -> {
                        c.setA(value);
                        c.setBreak(true);
                    }
                ),
                context,
                value,
                flags
            )
        );
    }

    @Test
    public void testExecuteAccumulatorAndCheck() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.fake()
        );
        context.setA((byte) 0x11);
        context.setPc(PC);

        final byte value = (byte) 0x22;

        final CpuFlags flags = CpuFlags.create();
        flags.setBreak(true);

        this.executeAccumulatorAndCheck(
            new TestCpuInstruction(
                1,
                (c) -> {
                    c.setA(value);
                    c.setBreak(true);
                    c.setPc(PC);
                }
            ),
            context,
            value,
            flags
        );
    }

    // executeXAndCheck.................................................................................................

    @Test
    public void testExecuteXAndCheckWithDifferentValueFails() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.fake()
        );
        context.setX((byte) 0x11);
        context.setPc(PC);

        final byte value = (byte) 0x22;

        final CpuFlags flags = CpuFlags.create();
        flags.setBreak(true);

        this.runAndThrows(
            () -> this.executeXAndCheck(
                new TestCpuInstruction(
                    1,
                    (c) -> {
                        //c.setX(value);
                        c.setBreak(true);
                        c.setPc(PC_PLUS_1);
                    }
                ),
                context,
                value,
                flags
            )
        );
    }

    @Test
    public void testExecuteXAndCheckWithDifferentFlagsFails() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.fake()
        );
        context.setX((byte) 0x11);
        context.setPc(PC);

        final byte value = (byte) 0x22;

        final CpuFlags flags = CpuFlags.create();

        this.runAndThrows(
            () -> this.executeXAndCheck(
                new TestCpuInstruction(
                    1,
                    (c) -> {
                        c.setX(value);
                        c.setBreak(true);
                        c.setPc(PC_PLUS_1);
                    }
                ),
                context,
                value,
                flags
            )
        );
    }

    @Test
    public void testExecuteXAndCheckWithDifferentPcFails() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.fake()
        );
        context.setX((byte) 0x11);
        context.setPc(PC);

        final byte value = (byte) 0x22;

        final CpuFlags flags = CpuFlags.create();

        this.runAndThrows(
            () -> this.executeXAndCheck(
                new TestCpuInstruction(
                    1,
                    (c) -> {
                        c.setX(value);
                        c.setBreak(true);
                    }
                ),
                context,
                value,
                flags
            )
        );
    }

    @Test
    public void testExecuteXAndCheck() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.fake()
        );
        context.setX((byte) 0x11);
        context.setPc(PC);

        final byte value = (byte) 0x22;

        final CpuFlags flags = CpuFlags.create();
        flags.setBreak(true);

        this.executeXAndCheck(
            new TestCpuInstruction(
                1,
                (c) -> {
                    c.setX(value);
                    c.setBreak(true);
                    c.setPc(PC);
                }
            ),
            context,
            value,
            flags
        );
    }

    // executeYAndCheck.................................................................................................

    @Test
    public void testExecuteYAndCheckWithDifferentValueFails() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.fake()
        );
        context.setY((byte) 0x11);
        context.setPc(PC);

        final byte value = (byte) 0x22;

        final CpuFlags flags = CpuFlags.create();
        flags.setBreak(true);

        this.runAndThrows(
            () -> this.executeYAndCheck(
                new TestCpuInstruction(
                    1,
                    (c) -> {
                        //c.setY(value);
                        c.setBreak(true);
                        c.setPc(PC_PLUS_1);
                    }
                ),
                context,
                value,
                flags
            )
        );
    }

    @Test
    public void testExecuteYAndCheckWithDifferentFlagsFails() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.fake()
        );
        context.setY((byte) 0x11);
        context.setPc(PC);

        final byte value = (byte) 0x22;

        final CpuFlags flags = CpuFlags.create();

        this.runAndThrows(
            () -> this.executeYAndCheck(
                new TestCpuInstruction(
                    1,
                    (c) -> {
                        c.setY(value);
                        c.setBreak(true);
                        c.setPc(PC_PLUS_1);
                    }
                ),
                context,
                value,
                flags
            )
        );
    }

    @Test
    public void testExecuteYAndCheckWithDifferentPcFails() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.fake()
        );
        context.setY((byte) 0x11);
        context.setPc(PC);

        final byte value = (byte) 0x22;

        final CpuFlags flags = CpuFlags.create();

        this.runAndThrows(
            () -> this.executeYAndCheck(
                new TestCpuInstruction(
                    1,
                    (c) -> {
                        c.setY(value);
                        c.setBreak(true);
                    }
                ),
                context,
                value,
                flags
            )
        );
    }

    @Test
    public void testExecuteYAndCheck() {
        final CpuContext context = CpuContexts.basic(
            AddressBuses.fake()
        );
        context.setY((byte) 0x11);
        context.setPc(PC);

        final byte value = (byte) 0x22;

        final CpuFlags flags = CpuFlags.create();
        flags.setBreak(true);

        this.executeYAndCheck(
            new TestCpuInstruction(
                1,
                (c) -> {
                    c.setY(value);
                    c.setBreak(true);
                    c.setPc(PC);
                }
            ),
            context,
            value,
            flags
        );
    }

    private void runAndThrows(final Runnable runnable) {
        boolean failed = false;

        try {
            runnable.run();
        } catch (AssertionError e) {
            failed = true;
        }

        this.checkEquals(
            true,
            failed
        );
    }

    @Override
    public TestCpuInstruction createCpuInstruction() {
        return new TestCpuInstruction(
            1,
            (c) -> {
                throw new UnsupportedOperationException();
            }
        );
    }

    final static class TestCpuInstruction implements CpuInstruction {

        TestCpuInstruction(final int length,
                           final Consumer<CpuContext> execute) {
            this.length = length;
            this.execute = execute;
        }

        @Override
        public byte opcode() {
            return 0;
        }

        @Override
        public int length() {
            return this.length;
        }

        private final int length;

        @Override
        public void execute(final CpuContext context) {
            Objects.requireNonNull(context, "context");

            this.execute.accept(context);
        }

        private final Consumer<CpuContext> execute;

        @Override
        public String disassemble(final CpuContext context) {
            Objects.requireNonNull(context, "context");

            throw new UnsupportedOperationException();
        }
    }
}
