package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc;

import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.HashMapMemory;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.MiniPowerPcProcessor;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.operations.Add;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.util.Util;
import org.junit.Test;

/**
 */
public class OperationsTest {
    @Test
    public void testAdd() {
        Add a = new Add(new String[]{"r1"});
        Memory m = new HashMapMemory();
        Processor p = new MiniPowerPcProcessor();
        p.getAccu().set(0b0000_1111_1111_1111);
        p.getRegister(1).set(0b1111_0000_0000_0001);
        a.execute(p, m);
        assert a.asBinary() == 0b0000_0111_1000_0000;
        assert Util.leftPadNulls(a.asBinaryString(), 16).equals("0000011110000000");
        assert a.asString().equals("ADD r1");
        assert p.getAccu().get() == 0;
        assert p.getCarry() == 1;
    }
}
