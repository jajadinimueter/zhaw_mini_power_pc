package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc;

import java.util.List;

/**
 */
public interface ProgramRunner {
    public void cycle();

    public int getAddress();

    public int getCarry();

    public List<Register> getRegisters();

    public Register getRegister(int index);

    public Register getAccu();
}
