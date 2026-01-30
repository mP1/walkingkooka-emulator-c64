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

import walkingkooka.emulator.c64.text.PetsciiReverseVisitor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

final class C64ExpressionFunctionC64BasicScnKeyPetsciiReverseVisitor extends PetsciiReverseVisitor {

    static byte[] translate(final String text) {
        final C64ExpressionFunctionC64BasicScnKeyPetsciiReverseVisitor visitor = new C64ExpressionFunctionC64BasicScnKeyPetsciiReverseVisitor();
        for (final char c : text.toCharArray()) {
            visitor.accept(c);
        }
        final ByteArrayOutputStream buffer = visitor.buffer;
        try {
            buffer.flush();
        } catch (final IOException ignore) {
            // never happens
        }
        return buffer.toByteArray();
    }

    C64ExpressionFunctionC64BasicScnKeyPetsciiReverseVisitor() {
        super();
    }

    @Override
    protected void visitCharacter(final byte value) {
        this.buffer.write(value);
    }

    private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();

    // Object...........................................................................................................

    @Override
    public String toString() {
        return "SCNKEY";
    }
}
