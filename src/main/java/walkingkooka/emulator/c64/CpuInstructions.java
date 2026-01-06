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
     * {@see CpuInstructionSharedBinaryFunctionAdcAbs}
     */
    public static CpuInstruction adcAbs() {
        return CpuInstructionSharedBinaryFunctionAdcAbs.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryFunctionAdcAbsX}
     */
    public static CpuInstruction adcAbsX() {
        return CpuInstructionSharedBinaryFunctionAdcAbsX.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryFunctionAdcAbsY}
     */
    public static CpuInstruction adcAbsY() {
        return CpuInstructionSharedBinaryFunctionAdcAbsY.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryFunctionAdcImm}
     */
    public static CpuInstruction adcImm() {
        return CpuInstructionSharedBinaryFunctionAdcImm.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryFunctionAdcIndX}
     */
    public static CpuInstruction adcIndX() {
        return CpuInstructionSharedBinaryFunctionAdcIndX.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryFunctionAdcIndY}
     */
    public static CpuInstruction adcIndY() {
        return CpuInstructionSharedBinaryFunctionAdcIndY.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryFunctionAdcZp}
     */
    public static CpuInstruction adcZp() {
        return CpuInstructionSharedBinaryFunctionAdcZp.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryFunctionAdcZpX}
     */
    public static CpuInstruction adcZpX() {
        return CpuInstructionSharedBinaryFunctionAdcZpX.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryFunctionAndAbs}
     */
    public static CpuInstruction andAbs() {
        return CpuInstructionSharedBinaryFunctionAndAbs.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryFunctionAndAbsX}
     */
    public static CpuInstruction andAbsX() {
        return CpuInstructionSharedBinaryFunctionAndAbsX.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryFunctionAndAbsY}
     */
    public static CpuInstruction andAbsY() {
        return CpuInstructionSharedBinaryFunctionAndAbsY.instance();
    }
    
    /**
     * {@see CpuInstructionSharedBinaryFunctionAndImm}
     */
    public static CpuInstruction andImm() {
        return CpuInstructionSharedBinaryFunctionAndImm.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryFunctionAndIndX}
     */
    public static CpuInstruction andIndX() {
        return CpuInstructionSharedBinaryFunctionAndIndX.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryFunctionAndIndY}
     */
    public static CpuInstruction andIndY() {
        return CpuInstructionSharedBinaryFunctionAndIndY.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryFunctionAndZp}
     */
    public static CpuInstruction andZp() {
        return CpuInstructionSharedBinaryFunctionAndZp.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryFunctionAndZpX}
     */
    public static CpuInstruction andZpX() {
        return CpuInstructionSharedBinaryFunctionAndZpX.instance();
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
     * {@see CpuInstructionSharedBinaryConsumerBitAbs}
     */
    public static CpuInstruction bitAbs() {
        return CpuInstructionSharedBinaryConsumerBitAbs.instance();
    }
    
    /**
     * {@see CpuInstructionSharedBinaryConsumerBitZp}
     */
    public static CpuInstruction bitZp() {
        return CpuInstructionSharedBinaryConsumerBitZp.instance();
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
     * {@see CpuInstructionSharedBinaryConsumerCompareCmpAbs}
     */
    public static CpuInstruction cmpAbs() {
        return CpuInstructionSharedBinaryConsumerCompareCmpAbs.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryConsumerCompareCmpAbsX}
     */
    public static CpuInstruction cmpAbsX() {
        return CpuInstructionSharedBinaryConsumerCompareCmpAbsX.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryConsumerCompareCmpAbsY}
     */
    public static CpuInstruction cmpAbsY() {
        return CpuInstructionSharedBinaryConsumerCompareCmpAbsY.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryConsumerCompareCmpImm}
     */
    public static CpuInstruction cmpImm() {
        return CpuInstructionSharedBinaryConsumerCompareCmpImm.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryConsumerCompareCmpIndX}
     */
    public static CpuInstruction cmpIndX() {
        return CpuInstructionSharedBinaryConsumerCompareCmpIndX.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryConsumerCompareCmpIndY}
     */
    public static CpuInstruction cmpIndY() {
        return CpuInstructionSharedBinaryConsumerCompareCmpIndY.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryConsumerCompareCmpZp}
     */
    public static CpuInstruction cmpZp() {
        return CpuInstructionSharedBinaryConsumerCompareCmpZp.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryConsumerCompareCmpZpX}
     */
    public static CpuInstruction cmpZpX() {
        return CpuInstructionSharedBinaryConsumerCompareCmpZpX.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryConsumerCompareCpxAbs}
     */
    public static CpuInstruction cpxAbs() {
        return CpuInstructionSharedBinaryConsumerCompareCpxAbs.instance();
    }
    
    /**
     * {@see CpuInstructionSharedBinaryConsumerCompareCpxImm}
     */
    public static CpuInstruction cpxImm() {
        return CpuInstructionSharedBinaryConsumerCompareCpxImm.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryConsumerCompareCpxZp}
     */
    public static CpuInstruction cpxZp() {
        return CpuInstructionSharedBinaryConsumerCompareCpxZp.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryConsumerCompareCpyAbs}
     */
    public static CpuInstruction cpyAbs() {
        return CpuInstructionSharedBinaryConsumerCompareCpyAbs.instance();
    }
    
    /**
     * {@see CpuInstructionSharedBinaryConsumerCompareCpyImm}
     */
    public static CpuInstruction cpyImm() {
        return CpuInstructionSharedBinaryConsumerCompareCpyImm.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryConsumerCompareCpyZp}
     */
    public static CpuInstruction cpyZp() {
        return CpuInstructionSharedBinaryConsumerCompareCpyZp.instance();
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
     * {@see CpuInstructionSharedBinaryFunctionEorAbs}
     */
    public static CpuInstruction eorAbs() {
        return CpuInstructionSharedBinaryFunctionEorAbs.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryFunctionEorAbsX}
     */
    public static CpuInstruction eorAbsX() {
        return CpuInstructionSharedBinaryFunctionEorAbsX.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryFunctionEorAbsY}
     */
    public static CpuInstruction eorAbsY() {
        return CpuInstructionSharedBinaryFunctionEorAbsY.instance();
    }
    
    /**
     * {@see CpuInstructionSharedBinaryFunctionEorImm}
     */
    public static CpuInstruction eorImm() {
        return CpuInstructionSharedBinaryFunctionEorImm.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryFunctionEorIndX}
     */
    public static CpuInstruction eorIndX() {
        return CpuInstructionSharedBinaryFunctionEorIndX.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryFunctionEorIndY}
     */
    public static CpuInstruction eorIndY() {
        return CpuInstructionSharedBinaryFunctionEorIndY.instance();
    }
    
    /**
     * {@see CpuInstructionSharedBinaryFunctionEorZp}
     */
    public static CpuInstruction eorZp() {
        return CpuInstructionSharedBinaryFunctionEorZp.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryFunctionEorZpX}
     */
    public static CpuInstruction eorZpX() {
        return CpuInstructionSharedBinaryFunctionEorZpX.instance();
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
     * {@see CpuInstructionSharedLoadALdaAbs}
     */
    public static CpuInstruction ldaAbs() {
        return CpuInstructionSharedLoadALdaAbs.instance();
    }

    /**
     * {@see CpuInstructionSharedLoadALdaAbsX}
     */
    public static CpuInstruction ldaAbsX() {
        return CpuInstructionSharedLoadALdaAbsX.instance();
    }

    /**
     * {@see CpuInstructionSharedLoadALdaAbsY}
     */
    public static CpuInstruction ldaAbsY() {
        return CpuInstructionSharedLoadALdaAbsY.instance();
    }
    
    /**
     * {@see CpuInstructionSharedLoadALdaImm}
     */
    public static CpuInstruction ldaImm() {
        return CpuInstructionSharedLoadALdaImm.instance();
    }

    /**
     * {@see CpuInstructionSharedLoadALdaIndX}
     */
    public static CpuInstruction ldaIndX() {
        return CpuInstructionSharedLoadALdaIndX.instance();
    }

    /**
     * {@see CpuInstructionSharedLoadALdaIndY}
     */
    public static CpuInstruction ldaIndY() {
        return CpuInstructionSharedLoadALdaIndY.instance();
    }

    /**
     * {@see CpuInstructionSharedLoadALdaZp}
     */
    public static CpuInstruction ldaZp() {
        return CpuInstructionSharedLoadALdaZp.instance();
    }

    /**
     * {@see CpuInstructionSharedLoadALdaZpX}
     */
    public static CpuInstruction ldaZpX() {
        return CpuInstructionSharedLoadALdaZpX.instance();
    }

    /**
     * {@see CpuInstructionSharedLoadXLdxAbs}
     */
    public static CpuInstruction ldxAbs() {
        return CpuInstructionSharedLoadXLdxAbs.instance();
    }

    /**
     * {@see CpuInstructionSharedLoadXLdxAbsY}
     */
    public static CpuInstruction ldxAbsY() {
        return CpuInstructionSharedLoadXLdxAbsY.instance();
    }
    
    /**
     * {@see CpuInstructionSharedLoadXLdxImm}
     */
    public static CpuInstruction ldxImm() {
        return CpuInstructionSharedLoadXLdxImm.instance();
    }

    /**
     * {@see CpuInstructionSharedLoadXLdxZp}
     */
    public static CpuInstruction ldxZp() {
        return CpuInstructionSharedLoadXLdxZp.instance();
    }

    /**
     * {@see CpuInstructionSharedLoadXLdxZpY}
     */
    public static CpuInstruction ldxZpY() {
        return CpuInstructionSharedLoadXLdxZpY.instance();
    }

    /**
     * {@see CpuInstructionSharedLoadYLdyAbs}
     */
    public static CpuInstruction ldyAbs() {
        return CpuInstructionSharedLoadYLdyAbs.instance();
    }

    /**
     * {@see CpuInstructionSharedLoadYLdyAbsX}
     */
    public static CpuInstruction ldyAbsX() {
        return CpuInstructionSharedLoadYLdyAbsX.instance();
    }

    /**
     * {@see CpuInstructionSharedLoadYLdyImm}
     */
    public static CpuInstruction ldyImm() {
        return CpuInstructionSharedLoadYLdyImm.instance();
    }

    /**
     * {@see CpuInstructionSharedLoadYLdyZp}
     */
    public static CpuInstruction ldyZp() {
        return CpuInstructionSharedLoadYLdyZp.instance();
    }

    /**
     * {@see CpuInstructionSharedLoadYLdyZpX}
     */
    public static CpuInstruction ldyZpX() {
        return CpuInstructionSharedLoadYLdyZpX.instance();
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
    public static CpuInstruction lsrAbsX() {
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
     * {@see CpuInstructionSharedBinaryFunctionOrAbs}
     */
    public static CpuInstruction orAbs() {
        return CpuInstructionSharedBinaryFunctionOrAbs.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryFunctionOrAbsX}
     */
    public static CpuInstruction orAbsX() {
        return CpuInstructionSharedBinaryFunctionOrAbsX.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryFunctionOrAbsY}
     */
    public static CpuInstruction orAbsY() {
        return CpuInstructionSharedBinaryFunctionOrAbsY.instance();
    }
    
    /**
     * {@see CpuInstructionSharedBinaryFunctionOrImm}
     */
    public static CpuInstruction orImm() {
        return CpuInstructionSharedBinaryFunctionOrImm.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryFunctionOrIndX}
     */
    public static CpuInstruction orIndX() {
        return CpuInstructionSharedBinaryFunctionOrIndX.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryFunctionOrIndY}
     */
    public static CpuInstruction orIndY() {
        return CpuInstructionSharedBinaryFunctionOrIndY.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryFunctionOrZp}
     */
    public static CpuInstruction orZp() {
        return CpuInstructionSharedBinaryFunctionOrZp.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryFunctionOrZpX}
     */
    public static CpuInstruction orZpX() {
        return CpuInstructionSharedBinaryFunctionOrZpX.instance();
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
     * {@see CpuInstructionSharedTransferPlp}
     */
    public static CpuInstruction plp() {
        return CpuInstructionSharedTransferPlp.instance();
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
     * {@see CpuInstructionSharedBinaryFunctionSbcAbs}
     */
    public static CpuInstruction sbcAbs() {
        return CpuInstructionSharedBinaryFunctionSbcAbs.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryFunctionSbcAbsX}
     */
    public static CpuInstruction sbcAbsX() {
        return CpuInstructionSharedBinaryFunctionSbcAbsX.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryFunctionSbcAbsY}
     */
    public static CpuInstruction sbcAbsY() {
        return CpuInstructionSharedBinaryFunctionSbcAbsY.instance();
    }
    
    /**
     * {@see CpuInstructionSharedBinaryFunctionSbcImm}
     */
    public static CpuInstruction sbcImm() {
        return CpuInstructionSharedBinaryFunctionSbcImm.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryFunctionSbcIndX}
     */
    public static CpuInstruction sbcIndX() {
        return CpuInstructionSharedBinaryFunctionSbcIndX.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryFunctionSbcIndY}
     */
    public static CpuInstruction sbcIndY() {
        return CpuInstructionSharedBinaryFunctionSbcIndY.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryFunctionSbcZp}
     */
    public static CpuInstruction sbcZp() {
        return CpuInstructionSharedBinaryFunctionSbcZp.instance();
    }

    /**
     * {@see CpuInstructionSharedBinaryFunctionSbcZpX}
     */
    public static CpuInstruction sbcZpX() {
        return CpuInstructionSharedBinaryFunctionSbcZpX.instance();
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
     * {@see CpuInstructionSharedStoreAStaAbs}
     */
    public static CpuInstruction staAbs() {
        return CpuInstructionSharedStoreAStaAbs.instance();
    }

    /**
     * {@see CpuInstructionSharedStoreAStaAbsX}
     */
    public static CpuInstruction staAbsX() {
        return CpuInstructionSharedStoreAStaAbsX.instance();
    }

    /**
     * {@see CpuInstructionSharedStoreAStaAbsY}
     */
    public static CpuInstruction staAbsY() {
        return CpuInstructionSharedStoreAStaAbsY.instance();
    }

    /**
     * {@see CpuInstructionSharedStoreAStaIndX}
     */
    public static CpuInstruction staIndX() {
        return CpuInstructionSharedStoreAStaIndX.instance();
    }

    /**
     * {@see CpuInstructionSharedStoreAStaIndY}
     */
    public static CpuInstruction staIndY() {
        return CpuInstructionSharedStoreAStaIndY.instance();
    }

    /**
     * {@see CpuInstructionSharedStoreAStaZp}
     */
    public static CpuInstruction staZp() {
        return CpuInstructionSharedStoreAStaZp.instance();
    }

    /**
     * {@see CpuInstructionSharedStoreAStaZpX}
     */
    public static CpuInstruction staZpX() {
        return CpuInstructionSharedStoreAStaZpX.instance();
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
