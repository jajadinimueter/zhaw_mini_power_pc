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
        boolean neg = val < 0;

        int msb = (val & (1 << 14)) >> 14;
        val <<= 1; // msb is 2nd
        if (neg) {
            val |= 1 << 15; // reset negative
        } else {
            val = val & ~(1 << 15);
        }

        processor.setCarry(msb == 1);
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
