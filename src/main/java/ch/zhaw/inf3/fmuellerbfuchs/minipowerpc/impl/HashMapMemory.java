package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl;

import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Memory;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.MemoryItem;

import java.util.HashMap;
import java.util.Map;

/**
 */
public class HashMapMemory implements Memory {
    private HashMap<Integer, MemoryItem> items;

    public HashMapMemory() {
        this.items = new HashMap<>();
    }

    @Override
    public void set(int address, MemoryItem item) {
        this.items.put(address, item);
    }

    @Override
    public <T extends MemoryItem> T get(int address, Class<T> cls) {
        Object x = this.items.get(address);
        if (x == null) {
            return null;
        } else {
            return cls.cast(x);
        }
    }

    @Override
    public int getWordLength() {
        return 16;
    }
}
