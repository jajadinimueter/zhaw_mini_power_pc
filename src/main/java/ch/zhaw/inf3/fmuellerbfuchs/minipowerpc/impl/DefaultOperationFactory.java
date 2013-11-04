package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl;

import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.NoSuchOperationException;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Operation;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.OperationFactory;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.operations.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 */
public class DefaultOperationFactory implements OperationFactory {
    private Map<String, Creator> ops;

    interface Creator {
        public Operation create(String[] arguments);
    }

    public DefaultOperationFactory() {
        ops = new HashMap<>();
        ops.put("clr", new Creator() {
            @Override
            public Operation create(String[] arguments) {
                return new Clear(arguments);
            }
        });
        ops.put("add", new Creator() {
            @Override
            public Operation create(String[] arguments) {
                return new Add(arguments);
            }
        });
        ops.put("addd", new Creator() {
            @Override
            public Operation create(String[] arguments) {
                return new AddDirect(arguments);
            }
        });
        ops.put("inc", new Creator() {
            @Override
            public Operation create(String[] arguments) {
                return new Increment(arguments);
            }
        });
        ops.put("dec", new Creator() {
            @Override
            public Operation create(String[] arguments) {
                return new Decrement(arguments);
            }
        });
        ops.put("lwdd", new Creator() {
            @Override
            public Operation create(String[] arguments) {
                return new LoadWord(arguments);
            }
        });
        ops.put("bz", new Creator() {
            @Override
            public Operation create(String[] arguments) {
                return new JumpZero(arguments);
            }
        });
        ops.put("bzn", new Creator() {
            @Override
            public Operation create(String[] arguments) {
                return new JumpNonZero(arguments);
            }
        });
        ops.put("bc", new Creator() {
            @Override
            public Operation create(String[] arguments) {
                return new JumpCarry(arguments);
            }
        });
        ops.put("b", new Creator() {
            @Override
            public Operation create(String[] arguments) {
                return new Jump(arguments);
            }
        });
        ops.put("bzd", new Creator() {
            @Override
            public Operation create(String[] arguments) {
                return new JumpZeroDirect(arguments);
            }
        });
        ops.put("bnzd", new Creator() {
            @Override
            public Operation create(String[] arguments) {
                return new JumpNonZeroDirect(arguments);
            }
        });
        ops.put("bcd", new Creator() {
            @Override
            public Operation create(String[] arguments) {
                return new JumpCarryDirect(arguments);
            }
        });
        ops.put("bd", new Creator() {
            @Override
            public Operation create(String[] arguments) {
                return new JumpCarryDirect(arguments);
            }
        });
        ops.put("swdd", new Creator() {
            @Override
            public Operation create(String[] arguments) {
                return new StoreWord(arguments);
            }
        });
        ops.put("sra", new Creator() {
            @Override
            public Operation create(String[] arguments) {
                return new ShiftRightArithmetic(arguments);
            }
        });
        ops.put("sla", new Creator() {
            @Override
            public Operation create(String[] arguments) {
                return new ShiftLeftArigthmetic(arguments);
            }
        });
        ops.put("srl", new Creator() {
            @Override
            public Operation create(String[] arguments) {
                return new ShiftRightLogic(arguments);
            }
        });
        ops.put("sll", new Creator() {
            @Override
            public Operation create(String[] arguments) {
                return new ShiftLeftLogic(arguments);
            }
        });
        ops.put("and", new Creator() {
            @Override
            public Operation create(String[] arguments) {
                return new And(arguments);
            }
        });
        ops.put("or", new Creator() {
            @Override
            public Operation create(String[] arguments) {
                return new Or(arguments);
            }
        });
        ops.put("not", new Creator() {
            @Override
            public Operation create(String[] arguments) {
                return new Not(arguments);
            }
        });
        ops.put("end", new Creator() {
            @Override
            public Operation create(String[] arguments) {
                return new End(arguments);
            }
        });
    }

    private String[] paramsToLower(String[] arguments) {
        List<String> newArguments = new ArrayList<String>();
        for (String x : arguments) {
            newArguments.add(x.toLowerCase());
        }
        return newArguments.toArray(new String[newArguments.size()]);
    }

    public Operation create(String opCode, String[] arguments) {
        if (opCode == null) {
            throw new NullPointerException("`opCode` must not be null");
        }

        if (arguments == null) {
            throw new NullPointerException("`arguments` must not be null");
        }

        opCode = opCode.toLowerCase();
        arguments = this.paramsToLower(arguments);
        Creator c = ops.get(opCode);

        if (c == null) {
            throw new NoSuchOperationException(opCode);
        }

        return c.create(arguments);
    }
}
