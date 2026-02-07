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

import walkingkooka.collect.map.Maps;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public final class PageTableComAddressSymbolsFunction implements Function<Short, Optional<String>> {

    // https://www.pagetable.com/c64ref/c64mem
    //
    // var rows = document.querySelectorAll("#disassembly_table tr")
    // var s = "";
    // for(i = 1; i < rows.length; i++) {
    //	var row = rows[i];
    //	var ths = row.getElementsByTagName("th");
    //	if(ths && ths[0] && ths[1]) {
    //		var address = ths[0].textContent.trim();
    //		if(address.startsWith("$")) {
    //			var label = ths[1].textContent.trim();
    //			if(label.length > 0) {
    //				s = s + address.trim() + "," + label + "\n";
    //			}
    //		}
    //	}
    // }
    //
    // console.log(s);
    private final static String SOURCE = "$0000,D6510\n" +
        "$0001,R6510\n" +
        "$0003-$0004,ADRAY1\n" +
        "$0005-$0006,ADRAY2\n" +
        "$0007,CHARAC\n" +
        "$0008,ENDCHR\n" +
        "$0009,TRMPOS\n" +
        "$000A,VERCK\n" +
        "$000B,COUNT\n" +
        "$000C,DIMFLG\n" +
        "$000D,VALTYP\n" +
        "$000E,INTFLG\n" +
        "$000F,GARBFL\n" +
        "$0010,SUBFLG\n" +
        "$0011,INPFLG\n" +
        "$0012,TANSGN\n" +
        "$0013,CHANNL\n" +
        "$0014-$0015,LINNUM\n" +
        "$0016,TEMPPT\n" +
        "$0017-$0018,LASTPT\n" +
        "$0019-$0021,TEMPST\n" +
        "$0022-$0025,INDEX\n" +
        "$0022-$0023,INDEX1\n" +
        "$0024-$0025,INDEX2\n" +
        "$0026-$002A,RES\n" +
        "$0026,RESHO\n" +
        "$0027,RESMOH\n" +
        "$0028,RESMO\n" +
        "$0029,RESLO\n" +
        "$002B-$002C,TXTTAB\n" +
        "$002D-$002E,VARTAB\n" +
        "$002F-$0030,ARYTAB\n" +
        "$0031-$0032,STREND\n" +
        "$0033-$0034,FRETOP\n" +
        "$0035-$0036,FRESPC\n" +
        "$0037-$0038,MEMSIZ\n" +
        "$0039-$003A,CURLIN\n" +
        "$003B-$003C,OLDLIN\n" +
        "$003D-$003E,OLDTXT\n" +
        "$003F-$0040,DATLIN\n" +
        "$0041-$0042,DATPTR\n" +
        "$0043-$0044,INPPTR\n" +
        "$0045-$0046,VARNAM\n" +
        "$0047-$0048,VARPNT\n" +
        "$0047,FDECPT\n" +
        "$0049-$004A,FORPNT\n" +
        "$0049,ANDMSK\n" +
        "$004A,EORMSK\n" +
        "$004B-$004C,OPPTR\n" +
        "$004B,VARTXT\n" +
        "$004D,OPMASK\n" +
        "$004E-$0052,TEMPF3\n" +
        "$004E-$004F,DEFPNT\n" +
        "$004E,GRBPNT\n" +
        "$0050-$0052,DSCPNT\n" +
        "$0053,FOUR6\n" +
        "$0054-$0056,JMPER\n" +
        "$0055,SIZE\n" +
        "$0056,OLDOV\n" +
        "$0057-$005B,TEMPF1\n" +
        "$0058,HIGHDS\n" +
        "$005A,HIGHTR\n" +
        "$005C-$0060,TEMPF2\n" +
        "$005D,LOWDS\n" +
        "$005F,LOWTR\n" +
        "$0058,ARYPNT\n" +
        "$005F,GRBTOP\n" +
        "$005D,DECCNT\n" +
        "$005E,TENEXP\n" +
        "$005F,DPTFLG\n" +
        "$0060,EXPSGN\n" +
        "$0061-$0066,FAC\n" +
        "$0061,FACEXP\n" +
        "$0062-$0065,FACHO\n" +
        "$0062,FACHO\n" +
        "$0063,FACMOH\n" +
        "$0064,FACMO\n" +
        "$0065,FACLO\n" +
        "$0066,FACSGN\n" +
        "$0067,SGNFLG\n" +
        "$0068,BITS\n" +
        "$0069-$006E,ARG\n" +
        "$0069,ARGEXP\n" +
        "$006A,ARGHO\n" +
        "$006B,ARGMOH\n" +
        "$006C,ARGMO\n" +
        "$006D,ARGLO\n" +
        "$006E,ARGSGN\n" +
        "$006F-$0070,STRNG1\n" +
        "$006F,ARISGN\n" +
        "$006F-$0070,STRNG1\n" +
        "$0070,FACOV\n" +
        "$0071-$0072,FBUFPT\n" +
        "$0073-$008A,CHRGET\n" +
        "$0079,CHRGOT\n" +
        "$007A-$007B,TXTPTR\n" +
        "$008B-$008F,RNDX\n" +
        "$0090,STATUS\n" +
        "$0091,STKEY\n" +
        "$0092,SVXT\n" +
        "$0093,VERCK\n" +
        "$0094,C3P0\n" +
        "$0095,BSOUR\n" +
        "$0096,SYNO\n" +
        "$0097,XSAV\n" +
        "$0098,LDTND\n" +
        "$0099,DFLTN\n" +
        "$009A,DFLTO\n" +
        "$009B,PRTY\n" +
        "$009C,DPSW\n" +
        "$009D,MSGFLG\n" +
        "$009E,PTR1\n" +
        "$009F,PTR2\n" +
        "$00A0-$00A2,TIME\n" +
        "$00A3,R2D2\n" +
        "$00A4,FIRT\n" +
        "$00A5,CNTDN\n" +
        "$00A6,BUFPT\n" +
        "$00A7,INBIT\n" +
        "$00A8,BITCI\n" +
        "$00A9,RINONE\n" +
        "$00AA,RIDATA\n" +
        "$00AB,RIPRTY\n" +
        "$00AC,SAL\n" +
        "$00AD,SAH\n" +
        "$00AE,EAL\n" +
        "$00AF,EAH\n" +
        "$00B0,CMP0\n" +
        "$00B1,TEMP\n" +
        "$00B2-$00B3,TAPE1\n" +
        "$00B4,BITTS\n" +
        "$00B5,NXTBIT\n" +
        "$00B6,RODATA\n" +
        "$00B7,FNLEN\n" +
        "$00B8,LA\n" +
        "$00B9,SA\n" +
        "$00BA,FA\n" +
        "$00BB-$00BC,FNADR\n" +
        "$00BD,ROPRTY\n" +
        "$00BE,FSBLK\n" +
        "$00BF,MYCH\n" +
        "$00C0,CAS1\n" +
        "$00C1-$00C2,STAL\n" +
        "$00C1,TMP0\n" +
        "$00C2,STAH\n" +
        "$00C3-$00C4,MEMUSS\n" +
        "$00C5,LSTX\n" +
        "$00C6,NDX\n" +
        "$00C7,RVS\n" +
        "$00C8,INDX\n" +
        "$00C9,LSXP\n" +
        "$00CA,LSTP\n" +
        "$00CB,SFDX\n" +
        "$00CC,BLNSW\n" +
        "$00CD,BLNCT\n" +
        "$00CE,GDBLN\n" +
        "$00CF,BLNON\n" +
        "$00D0,CRSW\n" +
        "$00D1-$00D2,PNT\n" +
        "$00D3,PNTR\n" +
        "$00D4,QTSW\n" +
        "$00D5,LNMX\n" +
        "$00D6,TBLX\n" +
        "$00D7,DATA\n" +
        "$00D8,INSRT\n" +
        "$00D9-$00F2,LDTB1\n" +
        "$00F3-$00F4,USER\n" +
        "$00F5-$00F6,KEYTAB\n" +
        "$00F7-$00F8,RIBUF\n" +
        "$00F9-$00FA,ROBUF\n" +
        "$00FB-$00FE,FREKZP\n" +
        "$00FF-$010A,BASZPT\n" +
        "$0100-$013E,BAD\n" +
        "$0100-$013D,BAD\n" +
        "$0200-$0258,BUF\n" +
        "$0259-$0262,LAT\n" +
        "$0263-$026C,FAT\n" +
        "$026D-$0276,SAT\n" +
        "$0277-$0280,KEYD\n" +
        "$0281-$0282,MEMSTR\n" +
        "$0283-$0284,MEMSIZ\n" +
        "$0285,TIMOUT\n" +
        "$0286,COLOR\n" +
        "$0287,GDCOL\n" +
        "$0288,HIBASE\n" +
        "$0289,XMAX\n" +
        "$028A,RPTFLA\n" +
        "$028B,KOUNT\n" +
        "$028C,DELAY\n" +
        "$028D,SHFLAG\n" +
        "$028E,LSTSHF\n" +
        "$028F-$0290,KEYLOG\n" +
        "$0291,MODE\n" +
        "$0292,AUTODN\n" +
        "$0293,M51CTR\n" +
        "$0294,M51CDR\n" +
        "$0295-$0296,M51AJB\n" +
        "$0297,RSSTAT\n" +
        "$0298,BITNUM\n" +
        "$0299-$029A,BAUDOF\n" +
        "$029B,RIDBE\n" +
        "$029C,RIDBS\n" +
        "$029D,RODBS\n" +
        "$029E,RODBE\n" +
        "$029F-$02A0,IRQTMP\n" +
        "$02A1,ENABL\n" +
        "$02A2,CASTON\n" +
        "$02A3,KIKA26\n" +
        "$02A4,STUPID\n" +
        "$02A5,LINTMP\n" +
        "$02A6,PALNTS\n" +
        "$0300-$0301,IERROR\n" +
        "$0302-$0303,IMAIN\n" +
        "$0304-$0305,ICRNCH\n" +
        "$0306-$0307,IQPLOP\n" +
        "$0308-$0309,IGONE\n" +
        "$030A-$030B,IEVAL\n" +
        "$030C,SAREG\n" +
        "$030D,SXREG\n" +
        "$030E,SYREG\n" +
        "$030F,SPREG\n" +
        "$0310-$0312,USRPOK\n" +
        "$0311-$0312,USRADD\n" +
        "$0314-$0315,CINV\n" +
        "$0316-$0317,CBINV\n" +
        "$0318-$0319,NMINV\n" +
        "$031A-$031B,IOPEN\n" +
        "$031C-$031D,ICLOSE\n" +
        "$031E-$031F,ICHKIN\n" +
        "$0320-$0321,ICKOUT\n" +
        "$0322-$0323,ICLRCH\n" +
        "$0324-$0325,IBASIN\n" +
        "$0326-$0327,IBSOUT\n" +
        "$0328-$0329,ISTOP\n" +
        "$032A-$032B,IGETIN\n" +
        "$032C-$032D,ICLALL\n" +
        "$032E-$032F,USRCMD\n" +
        "$0330-$0331,ILOAD\n" +
        "$0332-$0333,ISAVE\n" +
        "$033C-$03FB,TBUFFR";

    /**
     * Singleton
     */
    public final static PageTableComAddressSymbolsFunction INSTANCE = new PageTableComAddressSymbolsFunction();

    private PageTableComAddressSymbolsFunction() {
        // "$0000,D6510\n" +
        //        "$0001,R6510\n" +
        //        "$0003-$0004,ADRAY1\n" +

        final Map<Short, String> addressToSymbols = Maps.sorted();

        for (String line : SOURCE.split("\n")) {
            final int comma = line.indexOf(',');
            final String address = line.substring(0, comma);
            final String label = line.substring(comma + 1);

            final int dash = address.indexOf('-');
            if (-1 == dash) {
                addressToSymbols.put(
                    parseAddress(address),
                    label
                );
            } else {
                final String lower = address.substring(0, dash);
                final String upper = address.substring(dash + 1);

                addressToSymbols.put(
                    parseAddress(lower),
                    "< " + label
                );
                addressToSymbols.put(
                    parseAddress(upper),
                    "> " + label
                );
            }
        }

        this.addressToSymbols = addressToSymbols;
    }

    private static short parseAddress(final String address) {
        final Integer integer = Integer.parseInt(
            address.substring(1),
            16
        );
        return integer.shortValue();
    }

    @Override
    public Optional<String> apply(final Short address) {
        return Optional.ofNullable(
            this.addressToSymbols.get(address)
        );
    }

    private final Map<Short, String> addressToSymbols;

    // Object...........................................................................................................

    @Override
    public String toString() {
        return "https://www.pagetable.com/c64ref/c64mem/";
    }
}
