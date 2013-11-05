package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.impl;

import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.Memory;
import ch.zhaw.inf3.fmuellerbfuchs.minipowerpc.MemoryItem;

import java.util.*;

/**
 */
public class HashMapMemory implements Memory {
    private final HashMap<Integer, MemoryItem> items;

    public HashMapMemory() {
        this.items = new HashMap<>();
    }

    @Override
    public synchronized void clear() {
        items.clear();
    }

    @Override
    public synchronized void clear(int from) {
        Iterator<Integer> iter = items.keySet().iterator();
        while( iter.hasNext()) {
            Integer n = iter.next();
            if (n >= from) {
                iter.remove();
            }
        }
    }

    @Override
    public synchronized void set(int address, MemoryItem item) {
        this.items.put(address, item);
    }

    @Override
    public synchronized <T extends MemoryItem> T get(int address, Class<T> cls) {
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
