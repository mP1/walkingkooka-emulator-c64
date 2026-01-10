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
        // TODO maybe include a NOP instruction for invalid op-codes
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
                AddressBuses.memory(4 * 1024), // i,oDevices
                AddressBuses.kernalRom(
                    AddressBuses.baseOffset(
                        AddressBus.KERNAL_BASE,
                        ram
                    ) // write
                )
            )
        );

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

        while (true) {
            cpuContext.handleInterrupts();

            context.output()
                .println(
                    cpuContext +
                        " " +
                        cpu.disassemble(cpuContext)
                );
            try {
                cpu.step(cpuContext);
            } catch (final RuntimeException cause) {
                exitCode = 1;
                final Printer printer = context.error();
                printer.println(cause.getMessage());
                printer.flush();
                break;
            }
        }

        return exitCode;
    }

    public static void main(final String[] main) {
        final TerminalContext terminalContext = TerminalContexts.system(
            TerminalId.with(1),
            (s, c) -> {
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
