package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.operations;

import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Memory;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.MemoryItem;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Processor;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.util.Util;

/**
 */
public class LoadWord extends AbstractOperation {
    private int reg;
    private int adr;

    public LoadWord(String[] arguments) {
        super(arguments);
        reg = Util.getRegister(arguments[0]);
        adr = Util.getDirectNumber(arguments[1]);
    }

    @Override
    public void execute(Processor processor, Memory memory) {
        int val = 0;
        MemoryItem i = memory.get(adr, MemoryItem.class);
        if (i != null) {
            val = i.asBinary();
        }
        processor.getRegister(reg).set(val);
    }

    @Override
    public int asBinary() {
        return 0b1000_0000_0000_0000
                | (reg << 10) | ((adr << 16) >> 16);
    }

    @Override
    public String asString() {
        return createStringRepr("LWDD");
    }
}
