package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.operations;

import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Memory;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Processor;

/**
 */
public class ShiftLeftLogic extends AbstractOperation {
    public ShiftLeftLogic(String[] arguments) {
        super(arguments);
    }

    @Override
    public void execute(Processor processor, Memory memory) {
        int val = processor.getAccu().get();
        int msb = (val & ~(1 << 16)) >> 16;
        val <<= 16;
        processor.setCarry(msb == 1);
        processor.getAccu().set(val);
    }

    @Override
    public int asBinary() {
        return 0b0000_1100_0000_0000;
    }

    @Override
    public String asString() {
        return createStringRepr("SLL");
    }
}
