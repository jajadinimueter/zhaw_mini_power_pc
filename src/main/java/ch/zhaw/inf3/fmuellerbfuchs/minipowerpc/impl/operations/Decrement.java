package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.operations;

import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Memory;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Processor;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Register;

/**
 */
public class Decrement extends AbstractOperation {
    public Decrement(String[] arguments) {
        super(arguments);
    }

    @Override
    public void execute(Processor processor, Memory memory) {
        Register accu = processor.getAccu();
        int val = accu.get() - 1;
        accu.set(val);
        processor.setCarry(isMaxedOut(accu, val));
    }

    @Override
    public int asBinary() {
        return 0b0000_0100_0000_0000;
    }

    @Override
    public String asString() {
        return createStringRepr("DEC");
    }
}