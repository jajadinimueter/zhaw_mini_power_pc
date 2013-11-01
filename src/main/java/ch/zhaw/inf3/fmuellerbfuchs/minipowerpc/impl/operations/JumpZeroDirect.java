package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.operations;

import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Processor;

/**
 */
public class JumpZeroDirect extends AbstractDirectJump {
    public JumpZeroDirect(String[] arguments) {
        super(arguments);
    }

    @Override
    protected boolean mustJump(Processor processor) {
        return processor.getAccu().get() == 0;
    }

    @Override
    protected int getBase() {
        return 0b0011_0000_0000_0000;
    }

    @Override
    public String asString() {
        return createStringRepr("BZD");
    }
}
