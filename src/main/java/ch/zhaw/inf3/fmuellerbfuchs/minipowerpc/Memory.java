package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc;

import java.util.Map;

/**
 */
public interface Memory {
    public void set(int address, MemoryItem item);

    public <T extends MemoryItem> T get(int address, Class<T> cls);

    public int getWordLength();
}
