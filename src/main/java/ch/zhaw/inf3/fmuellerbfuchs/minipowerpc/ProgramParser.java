package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc;

import java.util.Map;

/**
 */
public interface ProgramParser {
    public Map<Integer, Operation> parse(String program);
}
