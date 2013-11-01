package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.operations;

import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Processor;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.util.Util;

/**
 */
public abstract class AbstractRegisterJump extends AbstractJump {
    protected int reg;

    public AbstractRegisterJump(String[] arguments) {
        super(arguments);
        reg = Util.getRegister(arguments[0]);
    }

    protected int getJumpAddress(Processor processor) {
        return processor.getRegister(reg).get();
    }

    @Override
    public int asBinary() {
        int base = getBase();
        return base | (reg << 10);
    }
}
