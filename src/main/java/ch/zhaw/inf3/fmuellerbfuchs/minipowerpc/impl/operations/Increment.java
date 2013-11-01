package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.operations;

import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Memory;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Processor;

/**
 */
public class Increment extends AbstractOperation {
    public Increment(String[] arguments) {
        super(arguments);
    }

    @Override
    public void execute(Processor processor, Memory memory) {
        int i = processor.getAccu().get() + 1;
        processor.getAccu().set(i);
        processor.setCarry(i > processor.getAccu().getMax());
    }

    @Override
    public int asBinary() {
        return 0b0000_0001_0000_0000;
    }

    @Override
    public String asString() {
        return createStringRepr("INC");
    }
}
