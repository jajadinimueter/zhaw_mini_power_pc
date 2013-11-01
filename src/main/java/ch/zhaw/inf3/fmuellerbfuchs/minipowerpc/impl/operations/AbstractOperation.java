package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.operations;

import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Operation;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.util.Util;

/**
 */
public abstract class AbstractOperation implements Operation {
    protected String[] arguments;

    public AbstractOperation(String[] arguments) {
        this.arguments = arguments;
    }

    @Override
    public String asBinaryString() {
        return Integer.toBinaryString(this.asBinary());
    }

    protected String createStringRepr(String opCode) {
        return opCode + " " + Util.formatArguments(arguments);
    }
}
