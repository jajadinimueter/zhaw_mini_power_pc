package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.operations;

import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Memory;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Processor;
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
        // make 2 compl

        num = (num << 16) >> 16;
        if (num > 0) {
            num >>= 1;
        }
    }

    @Override
    public void execute(Processor processor, Memory memory) {
        int acc = processor.getAccu().get();
        acc = acc + num;
        processor.getAccu().set(acc);
        processor.setCarry(acc > processor.getAccu().getMax());
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
