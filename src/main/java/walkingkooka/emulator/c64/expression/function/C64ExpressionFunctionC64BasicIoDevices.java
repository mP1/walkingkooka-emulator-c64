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

import walkingkooka.emulator.c64.AddressBus;

/**
 * A really barebones block of 4K representing the IO Devices. Most writes do nothing and most reads return 0 except
 * for a few registers.
 */
final class C64ExpressionFunctionC64BasicIoDevices implements AddressBus {

    private final static int RASTER = 0xD012 - 0xD000;

    static C64ExpressionFunctionC64BasicIoDevices with() {
        return new C64ExpressionFunctionC64BasicIoDevices();
    }

    private C64ExpressionFunctionC64BasicIoDevices() {
        super();
    }

    @Override
    public byte read(final int offset) {
        final byte value;

        switch (offset) {
            case RASTER:
                value = this.raster++;
                break;
            default:
                value = 0;
                break;
        }
        return value;
    }

    private byte raster;

    @Override
    public void write(final int offset,
                      final byte value) {
        // ignore
    }

    @Override
    public int size() {
        return 4 * 1024;
    }
}
