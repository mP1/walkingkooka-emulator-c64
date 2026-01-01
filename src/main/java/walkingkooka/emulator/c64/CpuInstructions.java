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
     * {@see CpuInstructionSharedUnaryAslA}
     */
    public static CpuInstruction asla() {
        return CpuInstructionSharedUnaryAslA.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedBranchBcc}
     */
    public static CpuInstruction bcc() {
        return CpuInstructionSharedBranchBcc.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedBranchBcs}
     */
    public static CpuInstruction bcs() {
        return CpuInstructionSharedBranchBcs.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedBranchBeq}
     */
    public static CpuInstruction beq() {
        return CpuInstructionSharedBranchBeq.INSTANCE;
    }
    
    /**
     * {@see CpuInstructionSharedBranchBmi}
     */
    public static CpuInstruction bmi() {
        return CpuInstructionSharedBranchBmi.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedBranchBne}
     */
    public static CpuInstruction bne() {
        return CpuInstructionSharedBranchBne.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedBranchBpl}
     */
    public static CpuInstruction bpl() {
        return CpuInstructionSharedBranchBpl.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedBrk}
     */
    public static CpuInstruction brk() {
        return CpuInstructionSharedBrk.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedBranchBvc}
     */
    public static CpuInstruction bvc() {
        return CpuInstructionSharedBranchBvc.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedBranchBvs}
     */
    public static CpuInstruction bvs() {
        return CpuInstructionSharedBranchBvs.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedClearOrSetClc}
     */
    public static CpuInstruction clc() {
        return CpuInstructionSharedClearOrSetClc.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedClearOrSetCld}
     */
    public static CpuInstruction cld() {
        return CpuInstructionSharedClearOrSetCld.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedClearOrSetCli}
     */
    public static CpuInstruction cli() {
        return CpuInstructionSharedClearOrSetCli.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedClearOrSetClv}
     */
    public static CpuInstruction clv() {
        return CpuInstructionSharedClearOrSetClv.INSTANCE;
    }

    /**
     * {@see FakeCpuInstruction}
     */
    public static FakeCpuInstruction fake() {
        return new FakeCpuInstruction();
    }

    /**
     * {@see CpuInstructionSharedJmpAbs}
     */
    public static CpuInstruction jmpAbs() {
        return CpuInstructionSharedJmpAbs.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedJmpAbs}
     */
    public static CpuInstruction jmpIndirect() {
        return CpuInstructionSharedJmpAbs.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedJsr}
     */
    public static CpuInstruction jsr() {
        return CpuInstructionSharedJsr.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedNop}
     */
    public static CpuInstruction nop() {
        return CpuInstructionSharedNop.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedTransferPha}
     */
    public static CpuInstruction pha() {
        return CpuInstructionSharedTransferPha.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedTransferPhp}
     */
    public static CpuInstruction php() {
        return CpuInstructionSharedTransferPhp.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedTransferPla}
     */
    public static CpuInstruction pla() {
        return CpuInstructionSharedTransferPla.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedRti}
     */
    public static CpuInstruction rti() {
        return CpuInstructionSharedRti.INSTANCE;
    }
    
    /**
     * {@see CpuInstructionSharedRts}
     */
    public static CpuInstruction rts() {
        return CpuInstructionSharedRts.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedClearOrSetSec}
     */
    public static CpuInstruction sec() {
        return CpuInstructionSharedClearOrSetSec.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedClearOrSetSed}
     */
    public static CpuInstruction sed() {
        return CpuInstructionSharedClearOrSetSed.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedClearOrSetSei}
     */
    public static CpuInstruction sei() {
        return CpuInstructionSharedClearOrSetSei.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedTransferTax}
     */
    public static CpuInstruction tax() {
        return CpuInstructionSharedTransferTax.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedTransferTay}
     */
    public static CpuInstruction tay() {
        return CpuInstructionSharedTransferTay.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedTransferTsx}
     */
    public static CpuInstruction tsx() {
        return CpuInstructionSharedTransferTsx.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedTransferTxa}
     */
    public static CpuInstruction txa() {
        return CpuInstructionSharedTransferTxa.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedTransferTxs}
     */
    public static CpuInstruction txs() {
        return CpuInstructionSharedTransferTxs.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedTransferTya}
     */
    public static CpuInstruction tya() {
        return CpuInstructionSharedTransferTya.INSTANCE;
    }

    /**
     * Stop creation
     */
    private CpuInstructions() {
        throw new UnsupportedOperationException();
    }
}
