package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc;

import java.util.List;

/**
 */
public interface ProgramRunner {
    public boolean cycle();

    public int getAddress();

    public void setAddress(int adr);

    public int getCarry();

    public List<Register> getRegisters();

    public Register getRegister(int index);

    public Register getAccu();
}
