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
     * {@see CpuInstructionSharedBinaryBinaryFunctionAdcAbs}
     */
    public static CpuInstruction adcAbs() {
        return CpuInstructionSharedBinaryBinaryFunctionAdcAbs.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryBinaryFunctionAdcAbsX}
     */
    public static CpuInstruction adcAbsX() {
        return CpuInstructionSharedBinaryBinaryFunctionAdcAbsX.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryBinaryFunctionAdcAbsY}
     */
    public static CpuInstruction adcAbsY() {
        return CpuInstructionSharedBinaryBinaryFunctionAdcAbsY.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryBinaryFunctionAdcImm}
     */
    public static CpuInstruction adcImm() {
        return CpuInstructionSharedBinaryBinaryFunctionAdcImm.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryBinaryFunctionAdcIndX}
     */
    public static CpuInstruction adcIndX() {
        return CpuInstructionSharedBinaryBinaryFunctionAdcIndX.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryBinaryFunctionAdcIndY}
     */
    public static CpuInstruction adcIndY() {
        return CpuInstructionSharedBinaryBinaryFunctionAdcIndY.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryBinaryFunctionAdcZp}
     */
    public static CpuInstruction adcZp() {
        return CpuInstructionSharedBinaryBinaryFunctionAdcZp.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryBinaryFunctionAdcZpX}
     */
    public static CpuInstruction adcZpX() {
        return CpuInstructionSharedBinaryBinaryFunctionAdcZpX.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryBinaryFunctionAndAbs}
     */
    public static CpuInstruction andAbs() {
        return CpuInstructionSharedBinaryBinaryFunctionAndAbs.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryBinaryFunctionAndAbsX}
     */
    public static CpuInstruction andAbsX() {
        return CpuInstructionSharedBinaryBinaryFunctionAndAbsX.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryBinaryFunctionAndAbsY}
     */
    public static CpuInstruction andAbsY() {
        return CpuInstructionSharedBinaryBinaryFunctionAndAbsY.instance();
    }
    
    /**
     * {@see CpuInstructionSharedBinaryBinaryFunctionAndImm}
     */
    public static CpuInstruction andImm() {
        return CpuInstructionSharedBinaryBinaryFunctionAndImm.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryBinaryFunctionAndIndX}
     */
    public static CpuInstruction andIndX() {
        return CpuInstructionSharedBinaryBinaryFunctionAndIndX.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryBinaryFunctionAndIndY}
     */
    public static CpuInstruction andIndY() {
        return CpuInstructionSharedBinaryBinaryFunctionAndIndY.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryBinaryFunctionAndZp}
     */
    public static CpuInstruction andZp() {
        return CpuInstructionSharedBinaryBinaryFunctionAndZp.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryBinaryFunctionAndZpX}
     */
    public static CpuInstruction andZpX() {
        return CpuInstructionSharedBinaryBinaryFunctionAndZpX.instance();
    }

    /**
     * {@see CpuInstructionSharedUnaryAslA}
     */
    public static CpuInstruction aslA() {
        return CpuInstructionSharedUnaryAslA.instance();
    }

    /**
     * {@see CpuInstructionSharedUnaryAslZp}
     */
    public static CpuInstruction aslZp() {
        return CpuInstructionSharedUnaryAslZp.instance();
    }

    /**
     * {@see CpuInstructionSharedUnaryAslZpX}
     */
    public static CpuInstruction aslZpX() {
        return CpuInstructionSharedUnaryAslZpX.instance();
    }

    /**
     * {@see CpuInstructionSharedUnaryAslAbs}
     */
    public static CpuInstruction aslAbs() {
        return CpuInstructionSharedUnaryAslAbs.instance();
    }

    /**
     * {@see CpuInstructionSharedUnaryAslAbsX}
     */
    public static CpuInstruction aslAbsX() {
        return CpuInstructionSharedUnaryAslAbsX.instance();
    }

    /**
     * {@see CpuInstructionSharedBranchBcc}
     */
    public static CpuInstruction bcc() {
        return CpuInstructionSharedBranchBcc.instance();
    }

    /**
     * {@see CpuInstructionSharedBranchBcs}
     */
    public static CpuInstruction bcs() {
        return CpuInstructionSharedBranchBcs.instance();
    }

    /**
     * {@see CpuInstructionSharedBranchBeq}
     */
    public static CpuInstruction beq() {
        return CpuInstructionSharedBranchBeq.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryBinaryConsumerBitAbs}
     */
    public static CpuInstruction bitAbs() {
        return CpuInstructionSharedBinaryBinaryConsumerBitAbs.instance();
    }
    
    /**
     * {@see CpuInstructionSharedBinaryBinaryConsumerBitZp}
     */
    public static CpuInstruction bitZp() {
        return CpuInstructionSharedBinaryBinaryConsumerBitZp.instance();
    }

    /**
     * {@see CpuInstructionSharedBranchBmi}
     */
    public static CpuInstruction bmi() {
        return CpuInstructionSharedBranchBmi.instance();
    }

    /**
     * {@see CpuInstructionSharedBranchBne}
     */
    public static CpuInstruction bne() {
        return CpuInstructionSharedBranchBne.instance();
    }

    /**
     * {@see CpuInstructionSharedBranchBpl}
     */
    public static CpuInstruction bpl() {
        return CpuInstructionSharedBranchBpl.instance();
    }

    /**
     * {@see CpuInstructionSharedBrk}
     */
    public static CpuInstruction brk() {
        return CpuInstructionSharedBrk.instance();
    }

    /**
     * {@see CpuInstructionSharedBranchBvc}
     */
    public static CpuInstruction bvc() {
        return CpuInstructionSharedBranchBvc.instance();
    }

    /**
     * {@see CpuInstructionSharedBranchBvs}
     */
    public static CpuInstruction bvs() {
        return CpuInstructionSharedBranchBvs.instance();
    }

    /**
     * {@see CpuInstructionSharedClearOrSetClc}
     */
    public static CpuInstruction clc() {
        return CpuInstructionSharedClearOrSetClc.instance();
    }

    /**
     * {@see CpuInstructionSharedClearOrSetCld}
     */
    public static CpuInstruction cld() {
        return CpuInstructionSharedClearOrSetCld.instance();
    }

    /**
     * {@see CpuInstructionSharedClearOrSetCli}
     */
    public static CpuInstruction cli() {
        return CpuInstructionSharedClearOrSetCli.instance();
    }

    /**
     * {@see CpuInstructionSharedClearOrSetClv}
     */
    public static CpuInstruction clv() {
        return CpuInstructionSharedClearOrSetClv.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryBinaryConsumerCmpAbs}
     */
    public static CpuInstruction cmpAbs() {
        return CpuInstructionSharedBinaryBinaryConsumerCmpAbs.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryBinaryConsumerCmpAbsX}
     */
    public static CpuInstruction cmpAbsX() {
        return CpuInstructionSharedBinaryBinaryConsumerCmpAbsX.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryBinaryConsumerCmpAbsY}
     */
    public static CpuInstruction cmpAbsY() {
        return CpuInstructionSharedBinaryBinaryConsumerCmpAbsY.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryBinaryConsumerCmpImm}
     */
    public static CpuInstruction cmpImm() {
        return CpuInstructionSharedBinaryBinaryConsumerCmpImm.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryBinaryConsumerCmpIndX}
     */
    public static CpuInstruction cmpIndX() {
        return CpuInstructionSharedBinaryBinaryConsumerCmpIndX.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryBinaryConsumerCmpIndY}
     */
    public static CpuInstruction cmpIndY() {
        return CpuInstructionSharedBinaryBinaryConsumerCmpIndY.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryBinaryConsumerCmpZp}
     */
    public static CpuInstruction cmpZp() {
        return CpuInstructionSharedBinaryBinaryConsumerCmpZp.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryBinaryConsumerCmpZpX}
     */
    public static CpuInstruction cmpZpX() {
        return CpuInstructionSharedBinaryBinaryConsumerCmpZpX.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryBinaryConsumerCpxAbs}
     */
    public static CpuInstruction cpxAbs() {
        return CpuInstructionSharedBinaryBinaryConsumerCpxAbs.instance();
    }
    
    /**
     * {@see CpuInstructionSharedBinaryBinaryConsumerCpxImm}
     */
    public static CpuInstruction cpxImm() {
        return CpuInstructionSharedBinaryBinaryConsumerCpxImm.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryBinaryConsumerCpxZp}
     */
    public static CpuInstruction cpxZp() {
        return CpuInstructionSharedBinaryBinaryConsumerCpxZp.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryBinaryConsumerCpyAbs}
     */
    public static CpuInstruction cpyAbs() {
        return CpuInstructionSharedBinaryBinaryConsumerCpyAbs.instance();
    }
    
    /**
     * {@see CpuInstructionSharedBinaryBinaryConsumerCpyImm}
     */
    public static CpuInstruction cpyImm() {
        return CpuInstructionSharedBinaryBinaryConsumerCpyImm.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryBinaryConsumerCpyZp}
     */
    public static CpuInstruction cpyZp() {
        return CpuInstructionSharedBinaryBinaryConsumerCpyZp.instance();
    }
    
    /**
     * {@see CpuInstructionSharedDecAbs}
     */
    public static CpuInstruction decAbs() {
        return CpuInstructionSharedUnaryDecAbs.instance();
    }

    /**
     * {@see CpuInstructionSharedDecAbsX}
     */
    public static CpuInstruction decAbsX() {
        return CpuInstructionSharedUnaryDecAbsX.instance();
    }

    /**
     * {@see CpuInstructionSharedDecZp}
     */
    public static CpuInstruction decZp() {
        return CpuInstructionSharedUnaryDecZp.instance();
    }

    /**
     * {@see CpuInstructionSharedDecZpx}
     */
    public static CpuInstruction decZpX() {
        return CpuInstructionSharedUnaryDecZpX.instance();
    }

    /**
     * {@see CpuInstructionSharedDex}
     */
    public static CpuInstruction dex() {
        return CpuInstructionSharedUnaryDex.instance();
    }

    /**
     * {@see CpuInstructionSharedDey}
     */
    public static CpuInstruction dey() {
        return CpuInstructionSharedUnaryDey.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryBinaryFunctionEorAbs}
     */
    public static CpuInstruction eorAbs() {
        return CpuInstructionSharedBinaryBinaryFunctionEorAbs.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryBinaryFunctionEorAbsX}
     */
    public static CpuInstruction eorAbsX() {
        return CpuInstructionSharedBinaryBinaryFunctionEorAbsX.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryBinaryFunctionEorAbsY}
     */
    public static CpuInstruction eorAbsY() {
        return CpuInstructionSharedBinaryBinaryFunctionEorAbsY.instance();
    }
    
    /**
     * {@see CpuInstructionSharedBinaryBinaryFunctionEorImm}
     */
    public static CpuInstruction eorImm() {
        return CpuInstructionSharedBinaryBinaryFunctionEorImm.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryBinaryFunctionEorIndX}
     */
    public static CpuInstruction eorIndX() {
        return CpuInstructionSharedBinaryBinaryFunctionEorIndX.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryBinaryFunctionEorIndY}
     */
    public static CpuInstruction eorIndY() {
        return CpuInstructionSharedBinaryBinaryFunctionEorIndY.instance();
    }
    
    /**
     * {@see CpuInstructionSharedBinaryBinaryFunctionEorZp}
     */
    public static CpuInstruction eorZp() {
        return CpuInstructionSharedBinaryBinaryFunctionEorZp.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryBinaryFunctionEorZpX}
     */
    public static CpuInstruction eorZpX() {
        return CpuInstructionSharedBinaryBinaryFunctionEorZpX.instance();
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
        return CpuInstructionSharedUnaryIncAbs.instance();
    }

    /**
     * {@see CpuInstructionSharedIncAbsX}
     */
    public static CpuInstruction incAbsX() {
        return CpuInstructionSharedUnaryIncAbsX.instance();
    }

    /**
     * {@see CpuInstructionSharedIncZp}
     */
    public static CpuInstruction incZp() {
        return CpuInstructionSharedUnaryIncZp.instance();
    }

    /**
     * {@see CpuInstructionSharedIncZpx}
     */
    public static CpuInstruction incZpX() {
        return CpuInstructionSharedUnaryIncZpX.instance();
    }

    /**
     * {@see CpuInstructionSharedInx}
     */
    public static CpuInstruction inx() {
        return CpuInstructionSharedUnaryInx.instance();
    }

    /**
     * {@see CpuInstructionSharedIny}
     */
    public static CpuInstruction iny() {
        return CpuInstructionSharedUnaryIny.instance();
    }

    /**
     * {@see CpuInstructionSharedJmpAbs}
     */
    public static CpuInstruction jmpAbs() {
        return CpuInstructionSharedJmpAbs.instance();
    }

    /**
     * {@see CpuInstructionSharedJmpAbs}
     */
    public static CpuInstruction jmpIndirect() {
        return CpuInstructionSharedJmpAbs.instance();
    }

    /**
     * {@see CpuInstructionSharedJsr}
     */
    public static CpuInstruction jsr() {
        return CpuInstructionSharedJsr.instance();
    }

    /**
     * {@see CpuInstructionSharedUnaryLsrA}
     */
    public static CpuInstruction lsrA() {
        return CpuInstructionSharedUnaryLsrA.instance();
    }

    /**
     * {@see CpuInstructionSharedUnaryLsrAbs}
     */
    public static CpuInstruction lsrAbs() {
        return CpuInstructionSharedUnaryLsrAbs.instance();
    }

    /**
     * {@see CpuInstructionSharedUnaryLsrAbsX}
     */
    public static CpuInstruction lsrAbsx() {
        return CpuInstructionSharedUnaryLsrAbsX.instance();
    }

    /**
     * {@see CpuInstructionSharedUnaryLsrZp}
     */
    public static CpuInstruction lsrZp() {
        return CpuInstructionSharedUnaryLsrZp.instance();
    }

    /**
     * {@see CpuInstructionSharedUnaryLsrZpX}
     */
    public static CpuInstruction lsrZpX() {
        return CpuInstructionSharedUnaryLsrZpX.instance();
    }

    /**
     * {@see CpuInstructionSharedNop}
     */
    public static CpuInstruction nop() {
        return CpuInstructionSharedNop.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryBinaryFunctionOrAbs}
     */
    public static CpuInstruction orAbs() {
        return CpuInstructionSharedBinaryBinaryFunctionOrAbs.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryBinaryFunctionOrAbsX}
     */
    public static CpuInstruction orAbsX() {
        return CpuInstructionSharedBinaryBinaryFunctionOrAbsX.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryBinaryFunctionOrAbsY}
     */
    public static CpuInstruction orAbsY() {
        return CpuInstructionSharedBinaryBinaryFunctionOrAbsY.instance();
    }
    
    /**
     * {@see CpuInstructionSharedBinaryBinaryFunctionOrImm}
     */
    public static CpuInstruction orImm() {
        return CpuInstructionSharedBinaryBinaryFunctionOrImm.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryBinaryFunctionOrIndX}
     */
    public static CpuInstruction orIndX() {
        return CpuInstructionSharedBinaryBinaryFunctionOrIndX.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryBinaryFunctionOrIndY}
     */
    public static CpuInstruction orIndY() {
        return CpuInstructionSharedBinaryBinaryFunctionOrIndY.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryBinaryFunctionOrZp}
     */
    public static CpuInstruction orZp() {
        return CpuInstructionSharedBinaryBinaryFunctionOrZp.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryBinaryFunctionOrZpX}
     */
    public static CpuInstruction orZpX() {
        return CpuInstructionSharedBinaryBinaryFunctionOrZpX.instance();
    }

    /**
     * {@see CpuInstructionSharedTransferPha}
     */
    public static CpuInstruction pha() {
        return CpuInstructionSharedTransferPha.instance();
    }

    /**
     * {@see CpuInstructionSharedTransferPhp}
     */
    public static CpuInstruction php() {
        return CpuInstructionSharedTransferPhp.instance();
    }

    /**
     * {@see CpuInstructionSharedTransferPla}
     */
    public static CpuInstruction pla() {
        return CpuInstructionSharedTransferPla.instance();
    }

    /**
     * {@see CpuInstructionSharedUnaryRolA}
     */
    public static CpuInstruction rolA() {
        return CpuInstructionSharedUnaryRolA.instance();
    }

    /**
     * {@see CpuInstructionSharedUnaryRolAbs}
     */
    public static CpuInstruction rolAbs() {
        return CpuInstructionSharedUnaryRolAbs.instance();
    }

    /**
     * {@see CpuInstructionSharedUnaryRolAbsX}
     */
    public static CpuInstruction rolAbsX() {
        return CpuInstructionSharedUnaryRolAbsX.instance();
    }

    /**
     * {@see CpuInstructionSharedUnaryRolZp}
     */
    public static CpuInstruction rolZp() {
        return CpuInstructionSharedUnaryRolZp.instance();
    }

    /**
     * {@see CpuInstructionSharedUnaryRolZpX}
     */
    public static CpuInstruction rolZpX() {
        return CpuInstructionSharedUnaryRolZpX.instance();
    }

    /**
     * {@see CpuInstructionSharedUnaryRorA}
     */
    public static CpuInstruction rorA() {
        return CpuInstructionSharedUnaryRorA.instance();
    }

    /**
     * {@see CpuInstructionSharedUnaryRorAbs}
     */
    public static CpuInstruction rorAbs() {
        return CpuInstructionSharedUnaryRorAbs.instance();
    }

    /**
     * {@see CpuInstructionSharedUnaryRorAbsX}
     */
    public static CpuInstruction rorAbsX() {
        return CpuInstructionSharedUnaryRorAbsX.instance();
    }

    /**
     * {@see CpuInstructionSharedUnaryRorZp}
     */
    public static CpuInstruction rorZp() {
        return CpuInstructionSharedUnaryRorZp.instance();
    }

    /**
     * {@see CpuInstructionSharedUnaryRorZpX}
     */
    public static CpuInstruction rorZpX() {
        return CpuInstructionSharedUnaryRorZpX.instance();
    }
    
    /**
     * {@see CpuInstructionSharedRti}
     */
    public static CpuInstruction rti() {
        return CpuInstructionSharedRti.instance();
    }
    
    /**
     * {@see CpuInstructionSharedRts}
     */
    public static CpuInstruction rts() {
        return CpuInstructionSharedRts.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryBinaryFunctionSbcImm}
     */
    public static CpuInstruction sbcImm() {
        return CpuInstructionSharedBinaryBinaryFunctionSbcImm.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryBinaryFunctionSbcZp}
     */
    public static CpuInstruction sbcZp() {
        return CpuInstructionSharedBinaryBinaryFunctionSbcZp.instance();
    }
    
    /**
     * {@see CpuInstructionSharedClearOrSetSec}
     */
    public static CpuInstruction sec() {
        return CpuInstructionSharedClearOrSetSec.instance();
    }

    /**
     * {@see CpuInstructionSharedClearOrSetSed}
     */
    public static CpuInstruction sed() {
        return CpuInstructionSharedClearOrSetSed.instance();
    }

    /**
     * {@see CpuInstructionSharedClearOrSetSei}
     */
    public static CpuInstruction sei() {
        return CpuInstructionSharedClearOrSetSei.instance();
    }

    /**
     * {@see CpuInstructionSharedTransferTax}
     */
    public static CpuInstruction tax() {
        return CpuInstructionSharedTransferTax.instance();
    }

    /**
     * {@see CpuInstructionSharedTransferTay}
     */
    public static CpuInstruction tay() {
        return CpuInstructionSharedTransferTay.instance();
    }

    /**
     * {@see CpuInstructionSharedTransferTsx}
     */
    public static CpuInstruction tsx() {
        return CpuInstructionSharedTransferTsx.instance();
    }

    /**
     * {@see CpuInstructionSharedTransferTxa}
     */
    public static CpuInstruction txa() {
        return CpuInstructionSharedTransferTxa.instance();
    }

    /**
     * {@see CpuInstructionSharedTransferTxs}
     */
    public static CpuInstruction txs() {
        return CpuInstructionSharedTransferTxs.instance();
    }

    /**
     * {@see CpuInstructionSharedTransferTya}
     */
    public static CpuInstruction tya() {
        return CpuInstructionSharedTransferTya.instance();
    }

    /**
     * Stop creation
     */
    private CpuInstructions() {
        throw new UnsupportedOperationException();
    }
}
