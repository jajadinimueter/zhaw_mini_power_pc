package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.operations;

import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Memory;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Processor;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.util.Util;

/**
 */
public class Or extends AbstractOperation {
    private int reg;

    public Or(String[] arguments) {
        super(arguments);
        reg = Util.getRegister(arguments[0]);
    }

    @Override
    public void execute(Processor processor, Memory memory) {
        int accu = processor.getAccu().get();
        int val = processor.getRegister(reg).get();
        processor.getAccu().set(accu | val);
    }

    @Override
    public int asBinary() {
        // 0 0 0 0 x x 1 1 0

        int base = 0b0000_0011_0000_0000;
        return base | (reg << 10);
    }

    @Override
    public String asString() {
        return createStringRepr("OR");
    }
}
