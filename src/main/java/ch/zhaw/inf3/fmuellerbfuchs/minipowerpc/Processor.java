package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc;

/**
 * Represents the public processor interface. The {@link Operation} has
 * to operate solely on a processor and a {@link Memory}.
 */
public interface Processor {
    /**
     * Set operation pointer to next operation
     */
    public void next(int address);

    public void cycle();

    public Register getRegister(int index);

    public Register getAccu();

    public void setCarry(boolean carry);

    public int getCarry();
}
