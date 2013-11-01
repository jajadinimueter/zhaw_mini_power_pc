package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc;

/**
 * Represents the public processor interface. The {@link Operation} has
 * to operate solely on a processor and a {@link Memory}.
 */
public interface Processor {
    public Registers getRegisters();

    public void setCarry();

    public void clearCarry();
}
