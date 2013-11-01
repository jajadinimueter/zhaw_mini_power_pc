package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc;

/**
 */
public interface Program{
    /**
     * Returns the next command
     * @return The next command
     */
    public Operation next();
}
