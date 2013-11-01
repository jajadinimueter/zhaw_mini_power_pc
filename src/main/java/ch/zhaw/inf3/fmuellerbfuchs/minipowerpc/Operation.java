package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc;

/**
 * A command represents an executable assembler
 * command convertible from and to mnemonics.
 */
public interface Operation {
    /**
     * Execute the command on the given processor
     * @param processor The processor which can be used
     *                  to store registers
     * @param memory   To store values in memory.
     */
    public void execute(Processor processor, Memory memory);
}
