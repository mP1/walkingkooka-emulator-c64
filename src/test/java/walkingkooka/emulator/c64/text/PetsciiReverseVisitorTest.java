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

package walkingkooka.emulator.c64.text;

import org.junit.jupiter.api.Test;
import walkingkooka.emulator.c64.text.PetsciiReverseVisitorTest.TestPetsciiReverseVisitor;
import walkingkooka.reflect.JavaVisibility;

public final class PetsciiReverseVisitorTest implements PetsciiReverseVisitorTesting<TestPetsciiReverseVisitor> {

    @Test
    public void testVisitZero() {
        new TestPetsciiReverseVisitor() {
            @Override
            public void visitCharacter(final byte b) {
                this.visited = b;
            }
        }.acceptAndCheck(
            '0',
            (byte) 48
        );
    }

    @Test
    public void testVisitNine() {
        new TestPetsciiReverseVisitor() {
            @Override
            public void visitCharacter(final byte b) {
                this.visited = b;
            }
        }.acceptAndCheck(
            '9',
            (byte) 57
        );
    }

    @Test
    public void testVisitLowercaseA() {
        new TestPetsciiReverseVisitor() {
            @Override
            public void visitCharacter(final byte b) {
                this.visited = b;
            }
        }.acceptAndCheck(
            'a',
            (byte) 65
        );
    }

    @Test
    public void testVisitUpperCaseA() {
        new TestPetsciiReverseVisitor() {
            @Override
            public void visitCharacter(final byte b) {
                this.visited = b;
            }
        }.acceptAndCheck(
            'A',
            (byte) 97
        );
    }

    @Test
    public void testVisitSpace() {
        new TestPetsciiReverseVisitor() {
            @Override
            public void visitCharacter(final byte b) {
                this.visited = b;
            }
        }.acceptAndCheck(
            ' ',
            (byte) 32
        );
    }

    @Test
    public void testVisitSemiColon() {
        new TestPetsciiReverseVisitor() {
            @Override
            public void visitCharacter(final byte b) {
                this.visited = b;
            }
        }.acceptAndCheck(
            ';',
            (byte) 59
        );
    }

    @Test
    public void testVisitInvalidCharacter() {
        new TestPetsciiReverseVisitor() {
            @Override
            public void visitInvalidCharacter(final char c) {
                this.visited = c;
            }
        }.acceptAndCheck(
            '\u0001',
            '\u0001'
        );
    }

    @Override
    public TestPetsciiReverseVisitor createVisitor() {
        return new TestPetsciiReverseVisitor();
    }

    // class............................................................................................................

    @Override
    public void testTestNaming() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Class<TestPetsciiReverseVisitor> type() {
        return TestPetsciiReverseVisitor.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }

    class TestPetsciiReverseVisitor extends PetsciiReverseVisitor {
        final void acceptAndCheck(final char value,
                                  final byte expected) {
            this.acceptAndCheck(
                value,
                (Object) expected
            );
        }

        final void acceptAndCheck(final char value,
                                  final char expected) {
            this.acceptAndCheck(
                value,
                (Object) expected
            );
        }

        private void acceptAndCheck(final char value,
                                    final Object expected) {
            this.visited = 0;
            this.accept(value);

            PetsciiReverseVisitorTest.this.checkEquals(
                expected,
                this.visited
            );
        }

        Object visited;

        @Override
        public String toString() {
            return this.getClass().getSimpleName();
        }
    }
}
