[![Coverage Status](https://coveralls.io/repos/github/mP1/walkingkooka-emulator-c64/badge.svg?branch=master)](https://coveralls.io/github/mP1/walkingkooka-emulator-c64?branch=master)

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
