package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl;

import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Register;

/**
 */
public class RegisterImpl implements Register {
    private int value = 0;

    @Override
    public void set(int value) {
        this.value = value;
    }

    @Override
    public int get() {
        return this.value;
    }

    @Override
    public int getMax() {
        return 0b1111_1111_1111_1111;
    }
}
