package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.operations;

import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Operation;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Processor;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Register;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.util.Util;

/**
 */
public abstract class AbstractOperation implements Operation {
    protected String[] arguments;

    public AbstractOperation(String[] arguments) {
        this.arguments = arguments;
    }

    protected int adjustValue(Register reg, int val) {
        int max = reg.getMax();
        int min = reg.getMin();
        if (val > max) {
            return max;
        }
        if (val < min) {
            return min;
        }
        return val;
    }

    protected boolean isMaxedOut(Register reg, int val) {
        int max = reg.getMax();
        int min = reg.getMin();
        return val > max || val < min;
    }

    @Override
    public String asBinaryString() {
        return Integer.toBinaryString(this.asBinary());
    }

    protected String createStringRepr(String opCode) {
        return opCode + " " + Util.formatArguments(arguments);
    }
}
