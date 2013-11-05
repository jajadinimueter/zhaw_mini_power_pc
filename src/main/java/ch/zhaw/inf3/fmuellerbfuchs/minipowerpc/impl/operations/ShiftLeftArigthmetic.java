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

        int mod = 0;
        if (val < 0) {
            mod = 0b1000_0000_0000_0000;
        }
        int msb = (val & (1 << 14)) >> 14;
        val <<= 1; // msb is 2nd
        val |= mod; // reset negative

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
