package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.operations;

import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Memory;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Processor;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Register;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.util.Util;

/**
 */
public class Clear extends AbstractOperation {
    private int reg;

    public Clear(String[] arguments) {
        super(arguments);
        reg = Util.getRegister(arguments[0]);
    }

    @Override
    public void execute(Processor processor, Memory memory) {
        Register r = processor.getRegister(reg);
        r.set(0);
        processor.setCarry(false);
    }

    @Override
    public int asBinary() {
        int base = 0b0000_0010_1000_0000;
        return base | (reg << 10);
    }

    @Override
    public String asString() {
        return createStringRepr("CLR");
    }
}
