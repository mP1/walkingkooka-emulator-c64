[![Build Status](https://travis-ci.com/mP1/walkingkooka-emulator-c64.svg?branch=master)](https://travis-ci.com/mP1/walkingkooka-emulator-c64.svg?branch=master)
[![Coverage Status](https://coveralls.io/repos/github/mP1/walkingkooka-emulator-c64/badge.svg?branch=master)](https://coveralls.io/github/mP1/walkingkooka-emulator-c64?branch=master)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Language grade: Java](https://img.shields.io/lgtm/grade/java/g/mP1/walkingkooka-emulator-c64.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/mP1/walkingkooka-emulator-c64/context:java)
[![Total alerts](https://img.shields.io/lgtm/alerts/g/mP1/walkingkooka-emulator-c64.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/mP1/walkingkooka-emulator-c64/alerts/)



# walkingkooka-emulator-c64
A C64 java emulator with unit tests for each and every individual component.

[Hardware technical documentation](https://www.c64-wiki.com)

## Hardware completion status
- 6510 CPU: Nothing
- CIAA: TimerA, TimerB, TOD, INTS: TimerA underflow, TimerB underflow Clock=Alarm
- CIAB: TimerA, TimerB, TOD, INTS: TimerA underflow, TimerB underflow Clock=Alarm, VIC Mapper switch on PRA. 
- VICII: Nothing
- SID: Nothing
- Memory: ROM, RAM, Bank switching
