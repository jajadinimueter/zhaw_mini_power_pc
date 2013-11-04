package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.operations;

import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Memory;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Processor;

/**
 */
public class Not extends AbstractOperation {

    public Not(String[] arguments) {
        super(arguments);
    }

    @Override
    public void execute(Processor processor, Memory memory) {
        int val = processor.getAccu().get();
        processor.getAccu().set(~val);
    }

    @Override
    public int asBinary() {
        return 0b0000_0000_1000_0000;
    }

    @Override
    public String asString() {
        return createStringRepr("NOT");
    }
}
