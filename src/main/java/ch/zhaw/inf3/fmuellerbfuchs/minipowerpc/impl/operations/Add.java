package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.operations;

import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Memory;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Processor;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Register;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.util.Util;

/**
 * Is 0b0000RR1110000000
 */
public class Add extends AbstractOperation {
    private int reg;

    public Add(String[] arguments) {
        super(arguments);
        reg = Util.getRegister(arguments[0]);
    }

    public void execute(Processor processor, Memory memory) {
        Register r = processor.getRegister(reg);
        Register accu = processor.getAccu();
        int val = r.get() + accu.get();
        if (val > accu.getMax()) {
            processor.setCarry(true);
        } else {
            processor.setCarry(false);
        }
        accu.set(val & accu.getMax());
        processor.next();
    }

    @Override
    public int asBinary() {
        int base = 0b0000001110000000;
        return base | (reg << 10);
    }

    @Override
    public String asString() {
        return createStringRepr("ADD");
    }
}
