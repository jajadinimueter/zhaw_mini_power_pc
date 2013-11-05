package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc;

import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.HashMapMemory;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.MiniPowerPcProcessor;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.operations.Add;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.util.Util;
import org.junit.Before;
import org.junit.Test;

/**
 */
public class OperationsTest {
    private Memory memory;
    private Processor processor;
    private Register accu;
    private Register r1;
    private Register r2;
    private Register r3;

    @Before
    public void setup() {
        memory = new HashMapMemory();
        processor = new MiniPowerPcProcessor(memory, 100);
        accu = processor.getAccu();
        r1 = processor.getRegister(1);
        r2 = processor.getRegister(2);
        r3 = processor.getRegister(3);
    }

    @Test
    public void testAddBoundary() {
        Add a = new Add(new String[]{"r1"});
        accu.set(-1);
        r1.set(0b1111_1111_1111_1111_1000_0000_0000_0000);
        a.execute(processor, memory);
        assert accu.get() == 0b1111_1111_1111_1111_0111_1111_1111_1111;

        r1.set(0b0111_1111_1111_1111);
        accu.set(1);

        a.execute(processor, memory);
        assert accu.get() == 0b0000_0000_0000_0000_1000_0000_0000_0000;
    }
}
