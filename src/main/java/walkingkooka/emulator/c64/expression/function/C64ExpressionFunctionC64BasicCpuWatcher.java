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

import walkingkooka.emulator.c64.CpuContext;
import walkingkooka.emulator.c64.CpuInstructions;
import walkingkooka.emulator.c64.CpuWatcher;
import walkingkooka.terminal.TerminalContext;
import walkingkooka.text.printer.Printer;

import java.util.Objects;

/**
 * A {@link CpuWatcher} that handles breakpoints which bridge a basic session with the input and output of the given
 * {@link TerminalContext}.
 */
final class C64ExpressionFunctionC64BasicCpuWatcher<C extends TerminalContext> implements CpuWatcher {

    // jump table of a few routines that need to be trapped and re-routed.

    private final static short CHROUT = (short) 0xFFD2;

    // Read the system clock
    private final static short RDTIM = (short) 0xFFDE;

    // appears within the kernal there are direct jmps to SCNKEY without using the jump table.
    // Scan the keyboard
    private final static short SCNKEY = (short) 0xEA87; // 0xFF9F;

    // Set the system clock
    private final static short SETTIM = (short) 0xFFDB;

    // Check if STOP key pressed
    private final static short STOP = (short) 0xFFE1;

    // Update the system clock
    private final static short UDTIM = (short) 0xFFEA;

    static <C extends TerminalContext> C64ExpressionFunctionC64BasicCpuWatcher<C> with(final C terminalContext) {
        return new C64ExpressionFunctionC64BasicCpuWatcher(
            Objects.requireNonNull(terminalContext, "terminalContext")
        );
    }

    private C64ExpressionFunctionC64BasicCpuWatcher(final C terminalContext) {
        super();

        this.terminalContext = terminalContext;
    }

    @Override
    public void onBreakpoint(final CpuContext cpuContext) {
        switch (cpuContext.pc()) {
            case CHROUT:
                this.chrout(cpuContext);
                break;
            case RDTIM:
                this.rdtim(cpuContext);
                break;
            case SCNKEY:
                this.scnkey(cpuContext);
                break;
            case SETTIM:
                this.settim(cpuContext);
                break;
            case STOP:
                this.stop(cpuContext);
                break;
            case UDTIM:
                this.udtim(cpuContext);
                break;
            default:
                throw new IllegalStateException("Unknown breakpoint: " + cpuContext);
        }
    }

    @Override
    public void onInvalidOpcode(final CpuContext context) {
        final Printer printer = this.terminalContext.error();
        printer.println("\n*** INVALID OPCODE ***");
        printer.println(context.toString());

        throw new RuntimeException("Invalid opcode");
    }

    @Override
    public void onNmi(final CpuContext context) {
        final Printer printer = this.terminalContext.error();

        printer.println("\n*** NMI ***");
        printer.println(context.toString());
    }

    @Override
    public void onReset(final CpuContext context) {
        final Printer printer = this.terminalContext.error();

        printer.println("\n*** RESET ***");
        printer.println(context.toString());
    }

    boolean shouldRun() {
        return this.run < 3;
    }

    private int run;

    void addBreakpoints(final CpuContext cpuContext) {
        cpuContext.addBreakpoint(CHROUT);
        cpuContext.addBreakpoint(RDTIM);
        cpuContext.addBreakpoint(SCNKEY);
        cpuContext.addBreakpoint(SETTIM);
        cpuContext.addBreakpoint(STOP);
        cpuContext.addBreakpoint(UDTIM);
    }

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
    private void chrout(final CpuContext cpuContext) {
        C64ExpressionFunctionC64BasicChroutPetsciiVisitor.chrout(
            cpuContext.a(),
            this.terminalContext.output()
        );

        this.executeRts(cpuContext);

        // The status-register carry bit will always be clear upon return, unless output to tape is aborted by pressing the RUN/STOP key.
        cpuContext.setCarry(false);
    }

    private void rdtim(final CpuContext cpuContext) {
        // System.out.println("\n*** BREAKPOINT RDTIM ***");
        // System.out.println(cpuContext);

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
    private void scnkey(final CpuContext cpuContext) {
        final int keyboardBufferSize = 0xFF & cpuContext.readByte(XMAX);
        int index = cpuContext.readByte(NDX);
        final int readCount = keyboardBufferSize - index;
        if (readCount > 0) {
            final String input = this.terminalContext.input()
                .readText(
                    readCount,
                    1 // timeout terminal should buffer ahead as necessary
                );
            final C64ExpressionFunctionC64BasicScnKeyPetsciiReverseVisitor petsciiTranslator = C64ExpressionFunctionC64BasicScnKeyPetsciiReverseVisitor.translate(input);

            final byte[] allPetscii = petsciiTranslator.petscii();
            for (final byte petscii : allPetscii) {
                cpuContext.writeByte(
                    (short) (KEYD + index),
                    petscii
                );
                index++;
            }

            cpuContext.writeByte(
                NDX,
                (byte) index
            );

            // three RUN stops will kill cpu execute loop
            if (allPetscii.length > 0) {
                this.run = petsciiTranslator.stop ?
                    this.run + 1 :
                    0;
            }
        }

        // $7F: Stop key is pressed.
        // $FF: Stop key is not pressed.
        cpuContext.writeByte(
            STKEY,
            this.stop ?
                (byte) 0x7f :
                (byte) 0xff
        );

        cpuContext.writeByte(
            LSTX,
            (byte) 0x40 // no key
        );

        cpuContext.writeByte(
            SFDX,
            (byte) 0x40 // no key
        );

        cpuContext.writeByte(
            SHFLAG,
            (byte) 0
        );
        cpuContext.writeByte(
            LSTSHF,
            (byte) 0
        );

        this.executeRts(cpuContext);
    }

    // https://www.pagetable.com/c64ref/c64mem/#STKEY
    // Stop key indicator
    // Values:
    //
    // $7F: Stop key is pressed.
    // $FF: Stop key is not pressed.

    private final static short STKEY = 0x91;

    // https://www.pagetable.com/c64ref/c64mem/#LSTX
    // Matrix code of key previously pressed
    // Values:
    //
    // $00-$3F: Keyboard matrix code.
    // $40: No key was pressed at the time of previous check.

    private final static short LSTX = 0xC5;

    // https://www.c64-wiki.com/wiki/198
    // The zeropage address 198 ($C6, official name NDX) is used by the Kernal system to hold the number of keyboard entries
    // waiting in the ten-character keyboard buffer (see address 631-640).
    private final static short NDX = 0xC6;

    // https://www.pagetable.com/c64ref/c64mem/#SFDX
    // Matrix code of key currently being pressed
    // Values:
    //
    // $00-$3F: Keyboard matrix code.
    // $40: No key is currently pressed.
    private final static short SFDX = 0xCB;

    // https://www.pagetable.com/c64ref/c64mem/#0277
    ///
    // This buffer, sometimes also referred to as the keyboard queue, holds the ASCII values of the characters entered
    // from the keyboard. The interrupt routine which scans the keyboard deposits a character here each time a key is pressed.
    // When BASIC sees that there are characters waiting, it removes and prints them, one by one, in the order in which they were entered.
    //
    // This kind of a buffer is known as FIFO, for First In, First Out. The buffer will hold up to ten characters,
    // allowing you to type faster than the computer prints characters, without losing characters. The maximum number of
    // characters this buffer can hold at one time is ten (as determined by the value at 649 ($0289)). Characters entered
    // after the buffer is full will be ignored.
    private final static short KEYD = 0x277;

    // https://www.pagetable.com/c64ref/c64mem/#289
    // The value here indicates the number of characters waiting in the keyboard buffer at 631 ($0277).
    // The maximum number of characters in the keyboard buffer at any one time is determined by the value in
    // location 649 ($0289), which defaults to 10.
    private final static short XMAX = 0x289;

    // https://www.pagetable.com/c64ref/c64mem/#SHFLAG
    //
    // Bit #0: 1 = One or more of left Shift, right Shift or Shift Lock is currently being pressed or locked.
    // Bit #1: 1 = Commodore is currently being pressed.
    // Bit #2: 1 = Control is currently being pressed.
    private final static short SHFLAG = 0x28D;

    // https://www.pagetable.com/c64ref/c64mem/#LSTSHF
    //
    // Previous value of shift key indicator
    // Bits:
    //
    // Bit #0: 1 = One or more of left Shift, right Shift or Shift Lock was pressed or locked at the time of previous check.
    // Bit #1: 1 = Commodore was pressed at the time of previous check.
    // Bit #2: 1 = Control was pressed at the time of previous check.
    private final static short LSTSHF = 0x028E;

    private void settim(final CpuContext cpuContext) {
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
    private void stop(final CpuContext cpuContext) {
        final Printer printer = this.terminalContext.output();

        printer.println("\n*** BREAKPOINT STOP ***");
        printer.println(cpuContext.toString());

        cpuContext.setZero(this.stop);
        this.stop = false;

        this.executeRts(cpuContext);
    }

    private boolean stop;

    private void udtim(final CpuContext cpuContext) {
        // System.out.println("\n*** BREAKPOINT UDTIM ***");
        // System.out.println(cpuContext);

        this.executeRts(cpuContext);
    }

    private void executeRts(final CpuContext cpuContext) {
        CpuInstructions.rts()
            .execute(cpuContext);
        cpuContext.setA(
            (byte) 0
        );
    }

    private final C terminalContext;
}
