[![Build Status](https://github.com/mP1/walkingkooka-emulator-c64/actions/workflows/build.yaml/badge.svg)](https://github.com/mP1/walkingkooka-emulator-c64/actions/workflows/build.yaml/badge.svg)
[![Coverage Status](https://coveralls.io/repos/github/mP1/walkingkooka-emulator-c64/badge.svg)](https://coveralls.io/github/mP1/walkingkooka-emulator-c64)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Language grade: Java](https://img.shields.io/lgtm/grade/java/g/mP1/walkingkooka-emulator-c64.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/mP1/walkingkooka-emulator-c64/context:java)
[![Total alerts](https://img.shields.io/lgtm/alerts/g/mP1/walkingkooka-emulator-c64.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/mP1/walkingkooka-emulator-c64/alerts/)



# walkingkooka-emulator-c64
A C64 java emulator with unit tests for each and every individual component.

[Hardware technical documentation](https://www.c64-wiki.com)

## Hardware completion status

- 6510 CPU: *DONE*
- 6510 Instructions *DONE* -> [doco](https://www.masswerk.at/6502/6502_instruction_set.html)
- CIAA: TimerA, TimerB, TOD, INTS: TimerA underflow, TimerB underflow Clock=Alarm
- CIAB: TimerA, TimerB, TOD, INTS: TimerA underflow, TimerB underflow Clock=Alarm, VIC Mapper switch on PRA. 
- VICII: Nothing
- SID: Nothing
- Memory: ROM, RAM, Bank switching *DONE*

## ROMS

[Roms](https://www.commodore.ca/manuals/funet/cbm/firmware/computers/c64/index-t.html)

### [Functions](https://github.com/mP1/walkingkooka-tree/blob/master/src/main/java/walkingkooka/tree/expression/function/ExpressionFunction.java)

Functions that will be useful within a terminal session

[c64basic](https://github.com/mP1/walkingkooka-emulator-c64/blob/master/src/main/java/walkingkooka/emulator/c64/expression/function/C64ExpressionFunctionC64Basic.java)
Mostly functional, support for entering and executing simple basic statements works as expected.

```
PRINT 12+34
46
```

Several Cpu breakpoints are set to intercept the following KERNEL routines.

- CHROUT Now prints the given PETSCII character after translating to unicode to the terminal output.
- SCNKEY Reads the petscii from the terminal input if one is present.
- RDTIM [TODO](https://github.com/mP1/walkingkooka-emulator-c64/issues/580)
- SETTIM [TODO](https://github.com/mP1/walkingkooka-emulator-c64/issues/581)
- STOP Terminals need a way to receive interrupts from the user.

### $TI

In basic reading and write the magic variable $TI reads/writes using two KERNEL routines RDTIM and SETTIM, which
actually
reference a few zeropage bytes. These bytes are continuous updated whenever the CIA Timer clock triggers an IRQ many
times a second. Because the timer chips are not implemented this interrupt never happens, and the clock zero page values
are not updated.

Currently the two KERNEL routines do nothing. These need to be interfaced with the `EnvironmentContext`.

#### Poke

Pokes do write the given byte value to memory. It is thus possible to change/corrupt BASIC programs and variables, but
because no hardware devices are emulated or mapped it memory, no hardware magic happens.

- It is not possible to change the colour of any thing printed, because BASIC CHROUT routine now prints the PETSCII
  character after converting it to unicode to the terminal output.
- VIC, SID, COLOR, all memory that was hardware devices is now ram - it just reads and writes bytes and has no side
  effects.
- BASIC and KERNEL ROMS are still mapped otherwise BASIC itself would not operate.

