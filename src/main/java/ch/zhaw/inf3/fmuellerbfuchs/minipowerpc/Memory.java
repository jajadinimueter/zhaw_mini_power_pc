package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc;

/**
 */
public interface Memory {
    public void clear();

    public void clear(int from);

    public void set(int address, MemoryItem item);

    public <T extends MemoryItem> T get(int address, Class<T> cls);

    public int getWordLength();
}
