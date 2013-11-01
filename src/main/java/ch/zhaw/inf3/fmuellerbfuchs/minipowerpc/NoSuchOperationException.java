package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc;

/**
 */
public class NoSuchOperationException extends RuntimeException {
    public NoSuchOperationException(String operation) {
        super("No such operation " + operation);
    }

    public NoSuchOperationException(String operation, Throwable cause) {
        super("No such operation " + operation, cause);
    }
}
