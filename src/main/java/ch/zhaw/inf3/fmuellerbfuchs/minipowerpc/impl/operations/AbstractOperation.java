package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.operations;

import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Operation;

/**
 */
public abstract class AbstractOperation implements Operation {
    protected String[] arguments;

    public AbstractOperation(String[] arguments) {
        this.arguments = arguments;
    }
}
