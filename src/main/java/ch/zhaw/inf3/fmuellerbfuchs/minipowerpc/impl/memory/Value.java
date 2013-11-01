package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl.memory;

import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.MemoryItem;

/**
 */
public class Value implements MemoryItem {
    private int value = 0;

    public Value(int value) {
        this.value = value;
    }

    @Override
    public int asBinary() {
        return value;
    }

    @Override
    public String asBinaryString() {
        return Integer.toBinaryString(value);
    }

    @Override
    public String asString() {
        return Integer.toString(value);
    }
}
