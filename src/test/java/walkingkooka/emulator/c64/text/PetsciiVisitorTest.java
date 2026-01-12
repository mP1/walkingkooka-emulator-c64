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
import walkingkooka.emulator.c64.text.PetsciiVisitorTest.TestPetsciiVisitor;
import walkingkooka.reflect.JavaVisibility;

public final class PetsciiVisitorTest implements PetsciiVisitorTesting<TestPetsciiVisitor> {

    @Test
    public void testVisitZero() {
        new TestPetsciiVisitor() {
            @Override
            public void visitCharacter(final char c) {
                this.visited = c;
            }
        }.acceptAndCheck((byte) 48, '0');
    }

    @Test
    public void testVisitNine() {
        new TestPetsciiVisitor() {
            @Override
            public void visitCharacter(final char c) {
                this.visited = c;
            }
        }.acceptAndCheck((byte) 57, '9');
    }

    @Test
    public void testVisitLowercaseA() {
        new TestPetsciiVisitor() {
            @Override
            public void visitCharacter(final char c) {
                this.visited = c;
            }
        }.acceptAndCheck((byte) 65, 'a');
    }

    @Test
    public void testVisitUpperCaseA() {
        new TestPetsciiVisitor() {
            @Override
            public void visitCharacter(final char c) {
                this.visited = c;
            }
        }.acceptAndCheck((byte) 97, 'A');
    }

    @Test
    public void testVisitSpace() {
        new TestPetsciiVisitor() {
            @Override
            public void visitCharacter(final char c) {
                this.visited = c;
            }
        }.acceptAndCheck((byte) 32, ' ');
    }

    @Test
    public void testVisitSemiColon() {
        new TestPetsciiVisitor() {
            @Override
            public void visitCharacter(final char c) {
                this.visited = c;
            }
        }.acceptAndCheck((byte) 59, ';');
    }

    @Test
    public void testVisitStop() {
        new TestPetsciiVisitor() {
            @Override
            public void visitStop() {
                this.visited = '1';
            }
        }.acceptAndCheck((byte) 3, '1');
    }

    @Override
    public TestPetsciiVisitor createVisitor() {
        return new TestPetsciiVisitor();
    }

    // class............................................................................................................

    @Override
    public void testTestNaming() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Class<TestPetsciiVisitor> type() {
        return TestPetsciiVisitor.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }

    class TestPetsciiVisitor extends PetsciiVisitor {
        void acceptAndCheck(final byte value,
                            final char expected) {
            this.visited = 0;
            this.accept(value);

            PetsciiVisitorTest.this.checkEquals(
                expected,
                this.visited
            );
        }

        char visited;

        @Override
        public String toString() {
            return this.getClass().getSimpleName();
        }
    }
}
