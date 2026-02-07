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
- 6510 Instructions *WIP* -> [doco](https://www.masswerk.at/6502/6502_instruction_set.html)
- CIAA: TimerA, TimerB, TOD, INTS: TimerA underflow, TimerB underflow Clock=Alarm
- CIAB: TimerA, TimerB, TOD, INTS: TimerA underflow, TimerB underflow Clock=Alarm, VIC Mapper switch on PRA. 
- VICII: Nothing
- SID: Nothing
- Memory: ROM, RAM, Bank switching

## ROMS

[Roms](https://www.commodore.ca/manuals/funet/cbm/firmware/computers/c64/index-t.html)

### [Functions](https://github.com/mP1/walkingkooka-tree/blob/master/src/main/java/walkingkooka/tree/expression/function/ExpressionFunction.java)

Functions that will be useful within a terminal session

- [c64basic](https://github.com/mP1/walkingkooka-emulator-c64/blob/master/src/main/java/walkingkooka/emulator/c64/expression/function/C64ExpressionFunctionC64Basic.java)
  *WIP*