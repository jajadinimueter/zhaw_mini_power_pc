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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 */
public class DefaultOperationFactory implements OperationFactory {
    private Map<String, Creator> ops;
    private Map<String, String> pats;

    interface Creator {
        public Operation create(String[] arguments);
    }

    private String[] patternToArguments(String pattern, String input) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(input);
        if (m.matches()) {
            String[] args = new String[m.groupCount()];
            for (int i = 0; i < m.groupCount(); i++) {
                args[i] = m.group(i);
            }
            return args;
        } else {
            return null;
        }
    }

    public DefaultOperationFactory() {
        pats = new HashMap<>();

        pats.put("0000001010000000", "clr");
        pats.put("0000(\\d\\d)1110000000", "add");
        pats.put("1(\\d{15})", "addd");
        pats.put("0000(\\d\\d)1110000000", "add");
        pats.put("00000001\\d{8}", "inc");
        pats.put("00000100\\d{8}", "dec");
        pats.put("010\\d(\\d{2})(\\d{9})", "lwdd");
        pats.put("011\\d(\\d{2})(\\d{9})", "swdd");
        pats.put("00000101\\d{8}", "sra");
        pats.put("00001000\\d{8}", "sla");
        pats.put("00001001\\d{8}", "srl");
        pats.put("00001100\\d{8}", "sll");
        pats.put("0000(\\d{2})100\\d{7}", "and");
        pats.put("0000(\\d{2})110\\d{7}", "or");
        pats.put("000000001(\\d{7})", "not");
        pats.put("0001(\\d{2})10(\\d{7})", "bz");
        pats.put("0001(\\d{2})01(\\d{7})", "bnz");
        pats.put("0001(\\d{2})11(\\d{7})", "bc");
        pats.put("0001(\\d{2})00(\\d{7})", "b");
        pats.put("00110\\d(\\d{9})", "bzd");
        pats.put("00101\\d(\\d{9})", "bnzd");
        pats.put("00111\\d(\\d{9})", "bcd");
        pats.put("00100\\d(\\d{9})", "bd");
        pats.put("0000000000000000", "end");

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
                return new JumpDirect(arguments);
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

    public Operation create(String input) {
        for (String pat : pats.keySet()) {
            String[] args = patternToArguments(pat, input);
            if (args != null) {
                return create(pats.get(pat), args);
            }
        }
        return null;
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
