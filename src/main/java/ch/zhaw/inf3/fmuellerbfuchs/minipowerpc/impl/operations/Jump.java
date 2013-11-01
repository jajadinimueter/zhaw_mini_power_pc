package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.operations;

import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Processor;

/**
 */
public class Jump extends AbstractRegisterJump {

    public Jump(String[] arguments) {
        super(arguments);
    }

    @Override
    protected boolean mustJump(Processor processor) {
        return true;
    }

    @Override
    protected int getBase() {
        return 0b0001_0000_0000_0000;
    }

    @Override
    public String asString() {
        return createStringRepr("B");
    }
}
