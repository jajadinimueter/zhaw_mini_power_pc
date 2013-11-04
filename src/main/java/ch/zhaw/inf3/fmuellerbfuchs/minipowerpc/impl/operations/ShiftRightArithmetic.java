package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.operations;

import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Memory;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Processor;

/**
 */
public class ShiftRightArithmetic extends AbstractOperation {
    public ShiftRightArithmetic(String[] arguments) {
        super(arguments);
    }

    @Override
    public void execute(Processor processor, Memory memory) {
        int val = processor.getAccu().get();
        int lastBit = val & 1;
        processor.setCarry(lastBit == 1);
        processor.getAccu().set(val >> 1);
    }

    @Override
    public int asBinary() {
        return 0b0000_0101_0000_0000;

    }

    @Override
    public String asString() {
        return createStringRepr("SRA");
    }
}
