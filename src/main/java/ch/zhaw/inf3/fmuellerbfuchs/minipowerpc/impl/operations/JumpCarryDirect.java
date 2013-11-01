package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.operations;

import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Processor;

/**
 */
public class JumpCarryDirect extends AbstractDirectJump {
    public JumpCarryDirect(String[] arguments) {
        super(arguments);
    }

    @Override
    protected boolean mustJump(Processor processor) {
        return processor.getCarry() == 1;
    }

    @Override
    protected int getBase() {
        return 0b0011_1000_0000_0000;  // FIXME
    }

    @Override
    public String asString() {
        return createStringRepr("BCD");
    }
}
