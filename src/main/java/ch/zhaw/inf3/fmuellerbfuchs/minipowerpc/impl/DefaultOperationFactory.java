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
    private Map<String, Class<? extends AbstractOperation>> ops;

    public DefaultOperationFactory() {
        ops = new HashMap<String, Class<? extends AbstractOperation>>();
        ops.put("clr", Clear.class);
        ops.put("add", Add.class);
        ops.put("addd", AddDirect.class);
        ops.put("inc", Increment.class);
        ops.put("bz", JumpZero.class);
        ops.put("bzn", JumpNonZero.class);
        ops.put("bc", JumpCarry.class);
        ops.put("b", Jump.class);
        ops.put("bzd", JumpZeroDirect.class);
        ops.put("bnzd", JumpNonZeroDirect.class);
        ops.put("bcd", JumpCarryDirect.class);
        ops.put("bd", JumpDirect.class);
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
        Class<? extends AbstractOperation> cls = ops.get(opCode);

        if (cls == null) {
            throw new NoSuchOperationException(opCode);
        }

        try {
            return cls.getConstructor(String[].class).newInstance(arguments);
        } catch (InstantiationException e) {
            throw new NoSuchOperationException(opCode, e);
        } catch (IllegalAccessException e) {
            throw new NoSuchOperationException(opCode, e);
        } catch (InvocationTargetException e) {
            throw new NoSuchOperationException(opCode, e);
        } catch (NoSuchMethodException e) {
            throw new NoSuchOperationException(opCode, e);
        }
    }
}
