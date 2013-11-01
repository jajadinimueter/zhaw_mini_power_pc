package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.operations;

import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Processor;

/**
 */
public class JumpCarry extends AbstractRegisterJump {
    public JumpCarry(String[] arguments) {
        super(arguments);
    }

    @Override
    protected boolean mustJump(Processor processor) {
        return processor.getCarry() == 1;
    }

    @Override
    protected int getBase() {
        return 0b0001_0011_0000_0000;
    }

    @Override
    public String asString() {
        return createStringRepr("BC");
    }
}
