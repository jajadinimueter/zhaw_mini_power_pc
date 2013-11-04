package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.operations;

import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Memory;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Processor;

/**
 */
public class End extends AbstractOperation {
    public End(String[] arguments) {
        super(arguments);
    }

    @Override
    public void execute(Processor processor, Memory memory) {
    }

    @Override
    public int asBinary() {
        return 0;
    }

    @Override
    public String asString() {
        return createStringRepr("END");
    }
}
