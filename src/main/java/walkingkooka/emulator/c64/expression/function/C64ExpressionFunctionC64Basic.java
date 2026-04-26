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

package walkingkooka.emulator.c64.expression.function;

import walkingkooka.Cast;
import walkingkooka.emulator.c64.AddressBus;
import walkingkooka.emulator.c64.AddressBuses;
import walkingkooka.emulator.c64.Cpu;
import walkingkooka.emulator.c64.CpuContext;
import walkingkooka.emulator.c64.CpuContexts;
import walkingkooka.emulator.c64.CpuInstructions;
import walkingkooka.emulator.c64.Cpus;
import walkingkooka.emulator.c64.PageTableComAddressSymbolsFunction;
import walkingkooka.environment.EnvironmentContexts;
import walkingkooka.io.TextReader;
import walkingkooka.terminal.TerminalContext;
import walkingkooka.terminal.TerminalContexts;
import walkingkooka.terminal.TerminalId;
import walkingkooka.terminal.expression.FakeTerminalExpressionEvaluationContext;
import walkingkooka.terminal.expression.TerminalExpressionEvaluationContext;
import walkingkooka.text.printer.Printer;
import walkingkooka.tree.expression.ExpressionPurityContext;
import walkingkooka.tree.expression.function.ExpressionFunction;
import walkingkooka.tree.expression.function.ExpressionFunctionParameter;

import java.util.List;

/**
 * A function that may be executed within a terminal session and supports entering basic expressions that execute and
 * print stuff.
 */
final class C64ExpressionFunctionC64Basic<C extends TerminalExpressionEvaluationContext> extends C64ExpressionFunction<Integer, C> {

    private final static boolean DISASSEMBLE = false;

    /**
     * Type safe instance getter.
     */
    static <C extends TerminalExpressionEvaluationContext> C64ExpressionFunctionC64Basic<C> instance() {
        return Cast.to(INSTANCE);
    }

    /**
     * Singleton
     */
    private final static C64ExpressionFunctionC64Basic<?> INSTANCE = new C64ExpressionFunctionC64Basic<>();

    private C64ExpressionFunctionC64Basic() {
        super("c64Basic");
    }

    @Override
    public List<ExpressionFunctionParameter<?>> parameters(int i) {
        return NO_PARAMETERS;
    }

    @Override
    public Class<Integer> returnType() {
        return Integer.class;
    }

    @Override
    public boolean isPure(final ExpressionPurityContext context) {
        return false;
    }

    @Override
    public Integer apply(final List<Object> values,
                         final C context) {
        final Cpu cpu = Cpus.basic(
            CpuInstructions.all()
        );

        final AddressBus ram = AddressBuses.memory(
            256 * 256
        );
        final CpuContext cpuContext = CpuContexts.basic(
            AddressBuses.cpu(
                ram, // writable memory
                AddressBuses.basicRom(
                    AddressBuses.baseOffset(
                        AddressBus.BASIC_BASE,
                        ram
                    ) // write
                ),
                AddressBuses.characterGenerator(
                    AddressBuses.baseOffset(
                        AddressBus.CHARACTER_GENERATOR_BASE,
                        ram
                    ) // write
                ),
                C64ExpressionFunctionC64BasicIoDevices.with(), // IODevices
                //AddressBuses.memory(4 * 1024), // i,oDevices
                AddressBuses.kernalRom(
                    AddressBuses.baseOffset(
                        AddressBus.KERNAL_BASE,
                        ram
                    ) // write
                )
            ),
            PageTableComAddressSymbolsFunction.INSTANCE
        );

        final C64ExpressionFunctionC64BasicCpuWatcher<C> breapoints = C64ExpressionFunctionC64BasicCpuWatcher.with(context);

        cpuContext.addWatcher(breapoints);
        breapoints.addBreakpoints(cpuContext);

        int exitCode = 0;

        cpuContext.writeByte(
            (short) 0,
            AddressBus.DEFAULT_CPU_DATA_DIRECTION
        );
        cpuContext.writeByte(
            (short) 1,
            AddressBus.DEFAULT_CPU_PORT
        );
        cpuContext.reset();

        int fireInterruptCountdown = INSTRUCTIONS_PER_JIFFY;
        long timeBefore = System.currentTimeMillis();

        while (true) {
            cpuContext.handleInterrupts();

            if (DISASSEMBLE) {
                context.output()
                    .println(
                        cpuContext +
                            " " +
                            cpu.disassemble(cpuContext)
                    );
            }
            try {
                cpu.step(cpuContext);
            } catch (final RuntimeException cause) {
                exitCode = 1;
                final Printer printer = context.error();
                printer.println(cause.getMessage());
                printer.flush();
                break;
            }

            fireInterruptCountdown--;
            if (fireInterruptCountdown <= 0) {
                // assume 4 ticks per instruction
                // assume PAL 60 frames a second.
                try {
                    Thread.sleep(
                        Math.max(
                            1000 / 60 - System.currentTimeMillis() - timeBefore,
                            1
                        )
                    );
                } catch (final InterruptedException ignored) {

                }

                cpuContext.irq();

                fireInterruptCountdown = INSTRUCTIONS_PER_JIFFY;
            }

        }

        return exitCode;
    }

    /**
     * Cycles are not counted for each instruction, use a rough approximation of 4 cycles which is good
     * enough for our purposes of firing a TIMER IRQ which will trigger a SCNKEY to read any entered keys.
     */
    private final int INSTRUCTIONS_PER_JIFFY = 1 * 1000 * 1000 / 4 / 60;

    public static void main(final String[] main) {
        final TerminalContext terminalContext = TerminalContexts.system(
            TerminalId.with(1),
            (s, c) -> {
                throw new UnsupportedOperationException();
            },
            (Object exitValue) -> {
                throw new UnsupportedOperationException();
            },
            EnvironmentContexts.fake()
        );
        final TerminalExpressionEvaluationContext context = new FakeTerminalExpressionEvaluationContext() {
            @Override
            public TextReader input() {
                return terminalContext.input();
            }

            @Override
            public Printer output() {
                return terminalContext.output();
            }

            @Override
            public Printer error() {
                return terminalContext.error();
            }
        };
        final C64ExpressionFunctionC64Basic<TerminalExpressionEvaluationContext> basic = instance();
        basic.apply(
            ExpressionFunction.NO_PARAMETER_VALUES,
            context
        );
    }
}
