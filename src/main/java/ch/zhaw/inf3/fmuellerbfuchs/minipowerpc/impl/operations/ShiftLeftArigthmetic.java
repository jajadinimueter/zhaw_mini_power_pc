package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.operations;

import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Memory;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Processor;

/**
 */
public class ShiftLeftArigthmetic extends AbstractOperation {
    public ShiftLeftArigthmetic(String[] arguments) {
        super(arguments);
    }

    @Override
    public void execute(Processor processor, Memory memory) {
        int val = processor.getAccu().get();
        int mask = 0b0100_0000_0000_0000;
        int mod = 0;
        if (val < 0) {
            mod = 0b1000_0000_0000_0000;
        }
        int msb = val & mask;
        processor.setCarry(msb == 1);
        val <<= 1;
        val |= mod;
        processor.getAccu().set(val);
    }

    @Override
    public int asBinary() {
        return 0b0000_1000_0000_0000;
    }

    @Override
    public String asString() {
        return createStringRepr("SLA");
    }
}
