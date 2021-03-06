package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.operations;

import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Memory;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Processor;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.memory.Value;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.util.Util;

/**
 */
public class StoreWord extends AbstractOperation {
    private int reg;
    private int adr;

    public StoreWord(String[] arguments) {
        super(arguments);
        reg = Util.getRegister(arguments[0]);
        adr = Util.getDirectNumber(arguments[1]);
    }

    @Override
    public void execute(Processor processor, Memory memory) {
        int val = processor.getRegister(reg).get();
        memory.set(adr, new Value(val));
    }

    @Override
    public int asBinary() {
        return 0b1100_0000_0000_0000
                | (reg << 11) | adr;

    }

    @Override
    public String asString() {
        return createStringRepr("SWDD");
    }
}
