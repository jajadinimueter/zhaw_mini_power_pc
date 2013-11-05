package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc;

import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.DefaultOperationFactory;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.HashMapMemory;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.MiniPowerPcProcessor;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.memory.Value;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.mnemonics.MnemonicsParser;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.util.Util;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 */
@SuppressWarnings("FieldCanBeLocal")
public class ProgramTest {
    private static String PROGRAM = "100 CLR R0 // clear accu\n" +
            "102 SWDD R0, #504 // clear 304\n" +
            "104 SWDD R0, #506 // clear 506\n" +
            "106 SWDD R0, #514 // clear 514\n" +
            "108 LWDD R0, #502 // load second into accu\n" +
            "110 BZD #316 // end if second is 0\n" +
            "112 LWDD R0, #500 // load first into accu\n" +
            "114 BZD #316 // end if first is 0\n" +
            "116 SLL // shift accu to left\n" +
            "118 BCD #126 // if carry set -> jump\n" +
            "120 CLR R0 // clear accu\n" +
            "122 SWDD R0, #510 // init 510\n" +
            "124 BD #138 // jump to 138\n" +
            "\n" +
            "// jump if first is maxed\n" +
            "126 LWDD R0, #500\n" +
            "128 NOT\n" +
            "130 INC\n" +
            "132 SWDD R0, #500\n" +
            "134 CLR R0\n" +
            "136 INC\n" +
            "// end jump if first is maxed\n" +
            "\n" +
            "// jump here after init\n" +
            "138 SWDD R0, #510 \n" +
            "140 CLR R0\n" +
            "142 ADDD #15 \n" +
            "144 SWDD R0, #508\n" +
            "146 LWDD R0, #502\n" +
            "148 SLL\n" +
            "150 BCD #158\n" +
            "152 CLR R0\n" +
            "154 SWDD R0, #512\n" +
            "156 BD #172\n" +
            "158 LWDD R0, #502\n" +
            "160 NOT\n" +
            "162 INC\n" +
            "164 SWDD R0, #502\n" +
            "166 CLR R0\n" +
            "168 INC\n" +
            "170 SWDD R0, #512\n" +
            "172 LWDD R0, #510\n" +
            "174 LWDD R1, #512\n" +
            "176 ADD R1\n" +
            "178 DEC\n" +
            "180 BNZD #188\n" +
            "182 CLR R0\n" +
            "184 INC\n" +
            "186 SWDD R0, #514\n" +
            "188 LWDD R0, #500\n" +
            "190 SWDD R0, #504\n" +
            "192 LWDD R0, #508\n" +
            "194 DEC\n" +
            "196 SWDD R0, #508\n" +
            "198 LWDD R0, #502\n" +
            "200 SLA\n" +
            "202 SWDD R0, #502\n" +
            "204 BCD #208\n" +
            "206 BD #192\n" +
            "208 LWDD R0, #506\n" +
            "210 SLA\n" +
            "212 SWDD R0, #506\n" +
            "214 LWDD R0, #504\n" +
            "216 SLA\n" +
            "218 SWDD R0, #504\n" +
            "220 BCD #224\n" +
            "222 BD #230\n" +
            "224 LWDD R0, #506\n" +
            "226 INC\n" +
            "228 SWDD R0, #506\n" +
            "230 LWDD R0, #502\n" +
            "232 SLA\n" +
            "234 SWDD R0, #502\n" +
            "236 BCD #240\n" +
            "238 BD #258\n" +
            "240 LWDD R0, #504\n" +
            "242 LWDD R1, #500\n" +
            "244 ADD R1\n" +
            "246 SWDD R0, #504\n" +
            "248 BCD #252\n" +
            "250 BD #258\n" +
            "252 LWDD R0, #506\n" +
            "254 INC\n" +
            "256 SWDD R0, #506\n" +
            "258 LWDD R0, #508\n" +
            "260 DEC\n" +
            "262 SWDD R0, #508\n" +
            "264 BNZD #208\n" +
            "266 LWDD R0, #506\n" +
            "268 SRA\n" +
            "270 BCD #274\n" +
            "272 BD #286\n" +
            "274 CLR R1\n" +
            "276 ADDD #16384\n" +
            "278 SLL\n" +
            "280 LWDD R1, #504\n" +
            "282 OR R1\n" +
            "284 SWDD R0, #504\n" +
            "286 LWDD R0, #514\n" +
            "288 BZD #316\n" +
            "290 LWDD R0, #506\n" +
            "292 NOT\n" +
            "294 INC\n" +
            "296 SWDD R0, #506\n" +
            "298 LWDD R0, #504\n" +
            "300 NOT\n" +
            "302 INC\n" +
            "304 SWDD R0, #504\n" +
            "306 BCD #310\n" +
            "308 BD #316\n" +
            "310 LWDD R0, #506\n" +
            "312 DEC\n" +
            "314 SWDD R0, #506\n" +
            "316 END";

    private Memory memory;
    private Processor processor;
    private Register accu;
    private Register r1;
    private Register r2;
    private Register r3;

    @Before
    public void setup() {
        memory = new HashMapMemory();
        OperationFactory op = new DefaultOperationFactory();
        ProgramParser p = new MnemonicsParser(op);
        Map<Integer, Operation> ops = p.parse (PROGRAM);
        for (Map.Entry<Integer, Operation> o : ops.entrySet()) {
            memory.set(o.getKey(), o.getValue());
        }
        processor = new MiniPowerPcProcessor(memory, 100);
        accu = processor.getAccu();
        r1 = processor.getRegister(1);
        r2 = processor.getRegister(2);
        r3 = processor.getRegister(3);
    }

    private int calculateResult(int x, int y) {
        memory.clear(500);
        memory.set(500, new Value(x));
        memory.set(502, new Value(y));
        processor.setAddress(100);
        //noinspection StatementWithEmptyBody
        while (processor.cycle()) {}
        Value v1 = memory.get(504, Value.class);
        Value v2 = memory.get(506, Value.class);
        return Util.asInt32(v2, v1);
    }

    @Test
    public void testProgram() {
        /**
         * 1) 15 * 27
         2) 0 * 23’456
         3) -1’234 * 4’321
         4) -222 * -333
         */
        assert calculateResult(15, 27) == 15 * 27;
        assert calculateResult(0, 23456) == 0;
//        assert calculateResult(-1234, 4321) == 0;
        assert calculateResult(-2, -2) == 4;
        assert calculateResult(-222, -2) == 444;
        assert calculateResult(-2, 16384) == -32768;
        assert calculateResult(-2, 32768) == -65536;
//        assert calculateResult(-222, -333) == -222 * -333;
    }
}
