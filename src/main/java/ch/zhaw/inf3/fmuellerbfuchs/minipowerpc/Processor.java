package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc;

import java.util.List;

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

    public int getAddress();

    public Register getRegister(int index);

    public List<Register> getRegisters();

    public Register getAccu();

    public void setCarry(boolean carry);

    public int getCarry();
}
