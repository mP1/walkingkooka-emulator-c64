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

import walkingkooka.type.PublicStaticHelper;

import java.util.List;
import java.util.function.Consumer;

/**
 * Collection of factory methods to create devices which provide an {@link AddressBus} for interaction with the CPU.
 */
public final class AddressBuses implements PublicStaticHelper {

    /**
     * {@see BaseOffsetAddressBus}
     */
    public static AddressBus baseOffset(final int baseOffset,
                                        final AddressBus bus) {
        return BaseOffsetAddressBus.with(baseOffset, bus);
    }

    /**
     * {@see Ciaa}
     */
    public static AddressBus ciaa(final Consumer<Consumer<HardwareMatrixKey>> keyPress,
                                  final Consumer<Consumer<HardwareMatrixKey>> keyRelease,
                                  final Runnable interrupt) {
        return Ciaa.with(keyPress,
                keyRelease,
                interrupt);
    }

    /**
     * {@see Ciab}
     */
    public static AddressBus ciab(final VicMapper mapper,
                                  final Runnable interrupt) {
        return Ciab.with(mapper, interrupt);
    }

    /**
     * {@see FakeAddressBus}
     */
    public static AddressBus fake() {
        return new FakeAddressBus();
    }

    /**
     * {@see Memory}
     */
    public static AddressBus memory(final int size) {
        return Memory.with(size);
    }

    /**
     * {@see PagedAddressBus}
     */
    public static AddressBus paged(final List<AddressBus> pages) {
        return PagedAddressBus.with(pages);
    }

    /**
     * {@see Rom}
     */
    public static AddressBus rom(final byte[] values,
                                 final AddressBus write) {
        return Rom.with(values, write);
    }


    // ................................................................................................................

    /**
     * Stop creation.
     */
    private AddressBuses() {
        throw new UnsupportedOperationException();
    }
}
