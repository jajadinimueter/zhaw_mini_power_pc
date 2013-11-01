package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc;

import java.util.Map;

/**
 */
public interface Pc {
    public ProgramRunner run(Memory memory, int startAddress);
}
