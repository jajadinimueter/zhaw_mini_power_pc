package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl;

import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.*;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class MiniPowerPcProcessor implements Processor {
    private int carryBit = 0;
    private List<Register> regs;
    private boolean isJump;
    private int nextAddress;
    private Memory memory;

    public MiniPowerPcProcessor(Memory memory, int start) {
        regs = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            regs.add(new RegisterImpl());
        }
        this.memory = memory;
        this.nextAddress = start;
    }

    @Override
    public int getAddress() {
        return nextAddress;
    }

    @Override
    public void next(int address) {
        isJump = true;
        nextAddress = address;
    }

    @Override
    public void cycle() {
        isJump = false;
        Operation op = memory.get(nextAddress, Operation.class);
        op.execute(this, memory);
        if (!isJump) {
            // have to set next address by ourseleves
            nextAddress = nextAddress + 2;
        }
    }

    public Register getRegister(int index) {
        return regs.get(index);
    }

    public Register getAccu() {
        return getRegister(0);
    }

    @Override
    public void setCarry(boolean carry) {
        carryBit = carry ? 1 : 0;
    }

    @Override
    public int getCarry() {
        return carryBit;
    }
}
