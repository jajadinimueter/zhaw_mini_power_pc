package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc;

/**
 */
public interface Register {
    public void set(int value);

    public int get();

    public int getMax();

    public int getMin();
}
