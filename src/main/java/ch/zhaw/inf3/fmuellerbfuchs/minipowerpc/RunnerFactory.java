package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc;

/**
 */
public interface RunnerFactory {
    public ProgramRunner create(Memory memory, int startAddress);
}
