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

import walkingkooka.collect.list.Lists;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * A VIC address bus pointing to the correct bank.<br>
 * <a href="https://en.wikipedia.org/wiki/MOS_Technology_VIC-II#Character_graphics"></a>
 * <pre>
 * The character ROM is mapped into two of the VIC-II's four "windows", at $1000-$1FFF and $9000-$9FFF, although the CPU
 * cannot see it there (the character ROM may be switched into $D000-$DFFF where it is visible to the CPU, but not the VIC-II).
 * Thus graphics data or video buffers cannot be placed at $1000-$1FFF or $9000-$9FFF because the VIC-II will see the character ROM
 * there instead. Because these areas of RAM could not be used by the VIC-II graphics chip, they were frequently used for music/sound
 * effects (the SID chip). The C64 has the ability to have RAM and ROM at the same address in memory but the CPU would "see"
 * one and the VIC-II chip would "see" the other.
 * </pre>
 */
public final class VicMapper {

    static VicMapper with(final AddressBus characters,
                          final AddressBus memory,
                          final Consumer<AddressBus> vicAddressBus) {
        Objects.requireNonNull(characters, "characters");
        Objects.requireNonNull(memory, "memory");
        Objects.requireNonNull(vicAddressBus, "vicAddressBus");

        return new VicMapper(characters,
                memory,
                vicAddressBus);
    }

    private VicMapper(final AddressBus characters,
                      final AddressBus memory,
                      final Consumer<AddressBus> vicAddressBus) {
        super();

        final int bankCount = VicBank.values().length;
        this.banks = new AddressBus[bankCount];
        int offset = 0;

        for (int bank = 0; bank < bankCount; bank++) {
            final List<AddressBus> pages = Lists.array();

            for (int pageNumber = 0; pageNumber < 0x100 / bankCount; pageNumber++) {
                AddressBus page;

                if ((offset >= 0x1000 && offset < 0x2000) || (offset >= 0x9000 && offset < 0xa000)) {
                    page = characters;
                } else {
                    page = memory;
                }

                pages.add(page);

                offset += 0x100;
            }
            this.banks[bank] = AddressBuses.paged(pages);
        }

        this.vicAddressBus = vicAddressBus;
    }

    VicBank bank() {
        return this.bank;
    }

    /**
     * Updates the bank and fires the {@link Consumer} with the new address.
     */
    void setBank(final VicBank bank) {
        this.bank = bank;
        this.vicAddressBus.accept(this.banks[bank.number]);
    }

    private VicBank bank = VicBank.BANK0;

    private AddressBus[] banks;

    private final Consumer<AddressBus> vicAddressBus;

    @Override
    public String toString() {
        return "Bank " + this.bank.number;
    }
}
