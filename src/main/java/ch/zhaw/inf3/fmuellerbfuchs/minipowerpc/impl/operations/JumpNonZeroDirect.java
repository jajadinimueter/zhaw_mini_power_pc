package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.operations;

import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Processor;

/**
 */
public class JumpNonZeroDirect extends AbstractDirectJump {
    public JumpNonZeroDirect(String[] arguments) {
        super(arguments);
    }

    @Override
    protected boolean mustJump(Processor processor) {
        return processor.getAccu().get() != 0;
    }

    @Override
    protected int getBase() {
        return 0b0010_1000_0000_0000;  // FIXME
    }

    @Override
    public String asString() {
        return createStringRepr("BNZD");
    }
}
