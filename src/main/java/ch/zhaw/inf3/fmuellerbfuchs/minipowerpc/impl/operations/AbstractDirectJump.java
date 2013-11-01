package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.operations;

import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Processor;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.util.Util;

/**
 */
public abstract class AbstractDirectJump extends AbstractJump {
    protected int adr;

    public AbstractDirectJump(String[] arguments) {
        super(arguments);
        adr = Util.getDirectNumber(arguments[0]);
    }

    @Override
    protected int getJumpAddress(Processor processor) {
        // return direct jump address
        return adr;
    }

    @Override
    public int asBinary() {
        return getBase();
    }
}
