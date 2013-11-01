package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.operations;

import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Memory;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Processor;

/**
 */
public class Decrement extends AbstractOperation {
    public Decrement(String[] arguments) {
        super(arguments);
    }

    @Override
    public void execute(Processor processor, Memory memory) {
        int add = ~1 + 1;
        int i = processor.getAddress() + add;
        processor.getAccu().set(i);
        processor.setCarry(i > processor.getAccu().getMax());
    }

    @Override
    public int asBinary() {
        return 0;  // FIXME
    }

    @Override
    public String asString() {
        return null;  // FIXME
    }
}
