package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.operations;

import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Memory;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Operation;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Processor;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Register;

/**
 * Is 0b0000RR1110000000
 */
public class Add extends AbstractOperation {
    public Add(String[] arguments) {
        super(arguments);
    }

    public void execute(Processor processor, Memory memory) {
    }
}
