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

import walkingkooka.reflect.PublicStaticHelper;

/**
 * A collection of {@link CpuInstruction} factory methods.
 */
public final class CpuInstructions implements PublicStaticHelper {

    /**
     * {@see CpuInstructionBranchBcc}
     */
    public static CpuInstruction bcc() {
        return CpuInstructionBranchBcc.INSTANCE;
    }

    /**
     * {@see CpuInstructionBranchBcs}
     */
    public static CpuInstruction bcs() {
        return CpuInstructionBranchBcs.INSTANCE;
    }

    /**
     * {@see CpuInstructionBranchBeq}
     */
    public static CpuInstruction beq() {
        return CpuInstructionBranchBeq.INSTANCE;
    }
    
    /**
     * {@see CpuInstructionBranchBmi}
     */
    public static CpuInstruction bmi() {
        return CpuInstructionBranchBmi.INSTANCE;
    }

    /**
     * {@see CpuInstructionBranchBne}
     */
    public static CpuInstruction bne() {
        return CpuInstructionBranchBne.INSTANCE;
    }

    /**
     * {@see CpuInstructionBranchBpl}
     */
    public static CpuInstruction bpl() {
        return CpuInstructionBranchBpl.INSTANCE;
    }

    /**
     * {@see CpuInstructionBranchBvc}
     */
    public static CpuInstruction bvc() {
        return CpuInstructionBranchBvc.INSTANCE;
    }

    /**
     * {@see CpuInstructionBranchBvs}
     */
    public static CpuInstruction bvs() {
        return CpuInstructionBranchBvs.INSTANCE;
    }

    /**
     * {@see CpuInstructionClearOrSetClc}
     */
    public static CpuInstruction clc() {
        return CpuInstructionClearOrSetClc.INSTANCE;
    }

    /**
     * {@see CpuInstructionClearOrSetCld}
     */
    public static CpuInstruction cld() {
        return CpuInstructionClearOrSetCld.INSTANCE;
    }

    /**
     * {@see CpuInstructionClearOrSetCli}
     */
    public static CpuInstruction cli() {
        return CpuInstructionClearOrSetCli.INSTANCE;
    }

    /**
     * {@see CpuInstructionClearOrSetClv}
     */
    public static CpuInstruction clv() {
        return CpuInstructionClearOrSetClv.INSTANCE;
    }

    /**
     * {@see FakeCpuInstruction}
     */
    public static FakeCpuInstruction fake() {
        return new FakeCpuInstruction();
    }

    /**
     * {@see CpuInstructionNop}
     */
    public static CpuInstruction nop() {
        return CpuInstructionNop.INSTANCE;
    }

    /**
     * {@see CpuInstructionClearOrSetSec}
     */
    public static CpuInstruction sec() {
        return CpuInstructionClearOrSetSec.INSTANCE;
    }

    /**
     * {@see CpuInstructionClearOrSetSed}
     */
    public static CpuInstruction sed() {
        return CpuInstructionClearOrSetSed.INSTANCE;
    }

    /**
     * Stop creation
     */
    private CpuInstructions() {
        throw new UnsupportedOperationException();
    }
}
