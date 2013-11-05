package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.operations;

import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Memory;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Processor;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Register;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.util.Util;

/**
 * 100 ADDD #-100
 * 102 ADDD #100
 */
public class AddDirect extends AbstractOperation {
    private int num;

    public AddDirect(String[] arguments) {
        super(arguments);
        num = Util.getDirectNumber(arguments[0]);
    }

    @Override
    public void execute(Processor processor, Memory memory) {
        Register accu = processor.getAccu();
        int acc = accu.get();
        acc = acc + num;
        processor.getAccu().set(acc);
        processor.setCarry(isMaxedOut(accu, acc));
    }

    @Override
    public int asBinary() {
        return 0b1000_0000_0000_0000 | num;
    }

    @Override
    public String asString() {
        return createStringRepr("ADDD");
    }
}
