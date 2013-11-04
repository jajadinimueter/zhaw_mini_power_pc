package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.operations;

import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Memory;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Processor;

/**
 */
public class ShiftRightLogic extends AbstractOperation {
    public ShiftRightLogic(String[] arguments) {
        super(arguments);
    }

    @Override
    public void execute(Processor processor, Memory memory) {
        int val = processor.getAccu().get();
        int carry = val & 1;
        val >>= 1;
        val = val & ~(1 << 16);
        processor.setCarry(carry == 1);
        processor.getAccu().set(val);
    }

    @Override
    public int asBinary() {
        return 0b0000_1001_0000_0000;
    }

    @Override
    public String asString() {
        return createStringRepr("SRL");
    }
}
