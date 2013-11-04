package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.operations;

import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Memory;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Processor;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.util.Util;

/**
 */
public class And extends AbstractOperation {
    private int reg;

    public And(String[] arguments) {
        super(arguments);
        reg = Util.getRegister(arguments[0]);
    }

    @Override
    public void execute(Processor processor, Memory memory) {
        int accu = processor.getAccu().get();
        int regVal = processor.getRegister(reg).get();
        processor.getAccu().set(accu & regVal);
    }

    @Override
    public int asBinary() {
        int base = 0b0000_0010_0000_0000;
        return base | (reg << 10);
    }

    @Override
    public String asString() {
        return createStringRepr("AND");
    }
}
