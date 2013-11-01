package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.operations;

import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Memory;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Processor;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.util.Util;

/**
 */
public abstract class AbstractJump extends AbstractOperation{
    public AbstractJump(String[] arguments) {
        super(arguments);
    }

    @Override
    public void execute(Processor processor, Memory memory) {
        if (mustJump(processor)) {
            processor.next(getJumpAddress(processor));
        } else {
            processor.next();
        }
    }

    protected abstract int getJumpAddress(Processor processor);

    protected abstract boolean mustJump(Processor processor);

    protected abstract int getBase();
}
