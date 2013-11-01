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
        // FIXME
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
