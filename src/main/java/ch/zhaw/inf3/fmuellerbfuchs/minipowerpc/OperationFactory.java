package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc;

/**
 */
public interface OperationFactory {
    public Operation create(String opName, String[] arguments);

    public Operation create(String input);
}
