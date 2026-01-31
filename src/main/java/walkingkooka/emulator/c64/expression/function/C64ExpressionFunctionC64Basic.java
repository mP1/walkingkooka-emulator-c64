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
import walkingkooka.emulator.c64.CpuWatcher;
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

    // jump table of a few routines that need to be trapped and re-routed.

    private final static short CHROUT = (short) 0xFFD2;

    // Get a character
    private final static short GETIN = (short) 0xFFE4;

    // Read the system clock
    private final static short RDTIM = (short) 0xFFDE;

    // appears within the kernal there are direct jmps to SCNKEY without using the jump table.
    // Scan the keyboard
    private final static short SCNKEY = (short) 0xEA87; // 0xFF9F;

    // Set system message
    private final static short SETMSG = (short) 0xFF90;

    // Set the system clock
    private final static short SETTIM = (short) 0xFFDB;

    // Check if STOP key pressed
    private final static short STOP = (short) 0xFFE1;

    // Update the system clock
    private final static short UDTIM = (short) 0xFFEA;

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
            )
        );

        cpuContext.addWatcher(
            new CpuWatcher() {
                @Override
                public void onBreakpoint(final CpuContext cpuContext) {
                    switch (cpuContext.pc()) {
                        case CHROUT:
                            chrout(cpuContext, context);
                            break;
                        case GETIN:
                            getin(cpuContext, context);
                            break;
                        case RDTIM:
                            rdtim(cpuContext, context);
                            break;
                        case SCNKEY:
                            scnkey(cpuContext, context);
                            break;
                        case SETMSG:
                            setmsg(cpuContext, context);
                            break;
                        case SETTIM:
                            settim(cpuContext, context);
                            break;
                        case STOP:
                            stop(cpuContext, context);
                            break;
                        case UDTIM:
                            udtim(cpuContext, context);
                            break;
                        default:
                            throw new IllegalStateException("Unknown breakpoint: " + cpuContext);
                    }
                }

                @Override
                public void onInvalidOpcode(final CpuContext context) {
                    System.out.println("\n*** INVALID OPCODE ***");
                    System.out.println(context);

                    throw new RuntimeException("Invalid opcode");
                }

                @Override
                public void onNmi(final CpuContext context) {
                    System.out.println("\n*** NMI ***");
                    System.out.println(context);
                }

                @Override
                public void onReset(final CpuContext context) {
                    System.out.println("\n*** RESET ***");
                    System.out.println(context);
                }
            }
        );

        cpuContext.addBreakpoint(CHROUT);
        cpuContext.addBreakpoint(GETIN);
        cpuContext.addBreakpoint(RDTIM);
        cpuContext.addBreakpoint(SCNKEY);
        //cpuContext.addBreakpoint(SETMSG);
        cpuContext.addBreakpoint(SETTIM);
        cpuContext.addBreakpoint(STOP);
        cpuContext.addBreakpoint(UDTIM);

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
                        Math.min(
                            1000 / 60 - System.currentTimeMillis() - timeBefore,
                            30
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

    // https://www.pagetable.com/c64ref/kernal/#READST

    /**
     * <pre>
     * Communication registers: A
     * Preparatory routines: None
     * Error returns: None
     * Stack requirements: 2
     * Registers affected: A
     * Description: This routine returns the current status of the I/O devices in the accumulator. The routine is usually called after new communication to an I/O device. The routine gives you information about device status, or errors that have occurred during the I/O operation.
     *
     * The bits returned in the accumulator contain the following information: (see table below)
     *
     * ST Bit Position	ST Numeric Value	Cassette Read	Serial Bus R/W	Tape Verify + Load
     * 0	1		time out write
     * 1	2		time out read
     * 2	4	short block		short block
     * 3	8	long block		long block
     * 4	16	unrecoverable read error		any mismatch
     * 5	32	checksum error		checksum error
     * 6	64	end of file	EOI line
     * 7	-128	end of tape	device not present	end of tape
     * How to Use:
     * Call this routine.
     * Decode the information in the A register as it refers to your pro- gram.
     * EXAMPLE:
     * ;CHECK FOR END OF FILE DURING READ
     *      JSR READST
     *      AND #64                       ;CHECK EOF BIT (EOF=END OF FILE)
     *      BNE EOF                       ;BRANCH ON EOF
     * </pre>
     */
    private void chrout(final CpuContext cpuContext,
                        final C terminalContext) {
        // System.out.println("\n*** BREAKPOINT CHROUT ***");
        // System.out.println(cpuContext);

        C64ExpressionFunctionC64BasicChroutPetsciiVisitor.chrout(
            cpuContext.a(),
            terminalContext.output()
        );

        this.executeRts(cpuContext);

        // The status-register carry bit will always be clear upon return, unless output to tape is aborted by pressing the RUN/STOP key.
        cpuContext.setCarry(false);
    }

    private void getin(final CpuContext cpuContext,
                       final C terminalContext) {
        System.out.println("\n*** BREAKPOINT GETIN ***");
        System.out.println(cpuContext);

        this.executeRts(cpuContext);
    }

    private void rdtim(final CpuContext cpuContext,
                       final C terminalContext) {
        System.out.println("\n*** BREAKPOINT RDTIM ***");
        System.out.println(cpuContext);

        this.executeRts(cpuContext);
    }

    /**
     * <pre>
     * https://www.pagetable.com/c64ref/kernal/#SCNKEY
     *
     * JMP EA87/EB1E to the Keyboard Scan routine (see chapter 4) to check for a keypress. If a valid key is found down and the keyboard buffer is not full, the ASCII code value for the key is placed in the buffer.
     *
     * SCNKEY is useful if you have written a machine language program that runs with IRQ interrupts disabled, but you still want to scan the keyboard.
     * </pre>
     */
    private void scnkey(final CpuContext cpuContext,
                        final C terminalContext) {
        System.out.println("\n*** BREAKPOINT SCNKEY ***");
        System.out.println(cpuContext);

        int index = cpuContext.readByte(NDX);
        final int readCount = KEYBOARD_BUFFER_SIZE - index;
        if (readCount > 0) {
            final String input = terminalContext.input()
                .readText(
                    readCount,
                    READ_TIMEOUT_MILLIS
                );
            for (final byte petscii : C64ExpressionFunctionC64BasicScnKeyPetsciiReverseVisitor.translate(input)) {
                cpuContext.writeByte(
                    (short) (KEYBOARD_BUFFER + index),
                    petscii
                );
                index++;
            }

            cpuContext.writeByte(
                NDX,
                (byte) index
            );
        }

        this.executeRts(cpuContext);
    }

    // https://www.c64-wiki.com/wiki/198
    // The zeropage address 198 ($C6, official name NDX) is used by the Kernal system to hold the number of keyboard entries
    // waiting in the ten-character keyboard buffer (see address 631-640).
    private final byte NDX = (byte) 198;

    private final short KEYBOARD_BUFFER = (short) 631;

    private final int KEYBOARD_BUFFER_SIZE = 10;

    /**
     * Max wait time when reading the keyboard.
     */
    private final long READ_TIMEOUT_MILLIS = 50;

    private void setmsg(final CpuContext cpuContext,
                        final C terminalContext) {
        System.out.println("\n*** BREAKPOINT SETMSG ***");
        System.out.println(cpuContext);

        this.executeRts(cpuContext);
    }

    private void settim(final CpuContext cpuContext,
                        final C terminalContext) {
        System.out.println("\n*** BREAKPOINT SETTIM ***");
        System.out.println(cpuContext);

        this.executeRts(cpuContext);
    }

    /**
     * <pre>
     * https://www.pagetable.com/c64ref/kernal/#STOP
     *
     * This routine checks whether the RUN/STOP key is currently pressed. It returns with the status-register Z bit clear
     * if the key is not pressed, or with the bit set if it is pressed. Additionally, if RUN/STOP is pressed the CLRCH routine
     * is called to restore default I/O channels, and the count of keys in the keyboard buffer is reset to zero.
     * The JMP to the STOP execution routine is by way of the ISTOP indirect vector at $0328-$0329. You can modify the actions
     * of the routine by changing the vector to point to a routine of your own.
     * </pre>
     */
    private void stop(final CpuContext cpuContext,
                      final C terminalContext) {
        System.out.println("\n*** BREAKPOINT STOP ***");
        System.out.println(cpuContext);

        cpuContext.setZero(false);
        this.executeRts(cpuContext);
    }

    private void udtim(final CpuContext cpuContext,
                       final C terminalContext) {
        System.out.println("\n*** BREAKPOINT UDTIM ***");
        System.out.println(cpuContext);

        this.executeRts(cpuContext);
    }

    private void executeRts(final CpuContext cpuContext) {
        CpuInstructions.rts()
            .execute(cpuContext);
        cpuContext.setA(
            (byte) 0
        );
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
