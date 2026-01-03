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
    public static CpuInstruction aslA() {
        return CpuInstructionSharedUnaryAslA.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedUnaryAslZp}
     */
    public static CpuInstruction aslZp() {
        return CpuInstructionSharedUnaryAslZp.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedUnaryAslZpx}
     */
    public static CpuInstruction aslZpx() {
        return CpuInstructionSharedUnaryAslZpx.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedUnaryAslAbs}
     */
    public static CpuInstruction aslAbs() {
        return CpuInstructionSharedUnaryAslAbs.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedUnaryAslAbsX}
     */
    public static CpuInstruction aslAbsX() {
        return CpuInstructionSharedUnaryAslAbsX.INSTANCE;
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
     * {@see CpuInstructionSharedDecAbs}
     */
    public static CpuInstruction decAbs() {
        return CpuInstructionSharedUnaryDecAbs.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedDecAbsX}
     */
    public static CpuInstruction decAbsX() {
        return CpuInstructionSharedUnaryDecAbsX.INSTANCE;
    }
    
    /**
     * {@see FakeCpuInstruction}
     */
    public static FakeCpuInstruction fake() {
        return new FakeCpuInstruction();
    }

    /**
     * {@see CpuInstructionSharedIncAbs}
     */
    public static CpuInstruction incAbs() {
        return CpuInstructionSharedUnaryIncAbs.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedIncAbsX}
     */
    public static CpuInstruction incAbsX() {
        return CpuInstructionSharedUnaryIncAbsX.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedIncZp}
     */
    public static CpuInstruction incZp() {
        return CpuInstructionSharedUnaryIncZp.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedIncZpx}
     */
    public static CpuInstruction incZpx() {
        return CpuInstructionSharedUnaryIncZpx.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedInx}
     */
    public static CpuInstruction inx() {
        return CpuInstructionSharedUnaryInx.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedIny}
     */
    public static CpuInstruction iny() {
        return CpuInstructionSharedUnaryIny.INSTANCE;
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
     * {@see CpuInstructionSharedUnaryLsrA}
     */
    public static CpuInstruction lsrA() {
        return CpuInstructionSharedUnaryLsrA.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedUnaryLsrAbs}
     */
    public static CpuInstruction lsrAbs() {
        return CpuInstructionSharedUnaryLsrAbs.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedUnaryLsrAbsX}
     */
    public static CpuInstruction lsrAbsx() {
        return CpuInstructionSharedUnaryLsrAbsX.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedUnaryLsrZp}
     */
    public static CpuInstruction lsrZp() {
        return CpuInstructionSharedUnaryLsrZp.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedUnaryLsrZpx}
     */
    public static CpuInstruction lsrZpx() {
        return CpuInstructionSharedUnaryLsrZpx.INSTANCE;
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
     * {@see CpuInstructionSharedUnaryRolA}
     */
    public static CpuInstruction rolA() {
        return CpuInstructionSharedUnaryRolA.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedUnaryRolAbs}
     */
    public static CpuInstruction rolAbs() {
        return CpuInstructionSharedUnaryRolAbs.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedUnaryRolAbsX}
     */
    public static CpuInstruction rolAbsX() {
        return CpuInstructionSharedUnaryRolAbsX.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedUnaryRolZp}
     */
    public static CpuInstruction rolZp() {
        return CpuInstructionSharedUnaryRolZp.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedUnaryRolZpx}
     */
    public static CpuInstruction rolZpx() {
        return CpuInstructionSharedUnaryRolZpx.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedUnaryRorA}
     */
    public static CpuInstruction rorA() {
        return CpuInstructionSharedUnaryRorA.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedUnaryRorAbs}
     */
    public static CpuInstruction rorAbs() {
        return CpuInstructionSharedUnaryRorAbs.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedUnaryRorAbsX}
     */
    public static CpuInstruction rorAbsX() {
        return CpuInstructionSharedUnaryRorAbsX.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedUnaryRorZp}
     */
    public static CpuInstruction rorZp() {
        return CpuInstructionSharedUnaryRorZp.INSTANCE;
    }

    /**
     * {@see CpuInstructionSharedUnaryRorZpx}
     */
    public static CpuInstruction rorZpx() {
        return CpuInstructionSharedUnaryRorZpx.INSTANCE;
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
