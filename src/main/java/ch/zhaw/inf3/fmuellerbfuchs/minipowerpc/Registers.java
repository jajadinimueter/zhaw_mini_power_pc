package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc;

/**
 * Holds the registers accessible through an index or a
 * name.
 */
public interface Registers {

    /**
     * Returns the register with number `index`
     * @param index The register number. eg. 2
     * @return The register a position `index`
     */
    public Register get(int index);

    /**
     * Returns the register with name `name`. This
     * is for convenience. You may pass "R{0|1|2|3}".
     * @param name The registers name
     * @return The register
     */
    public Register get(String name);

    /**
     *
     * @return
     */
    public Register getAkku();
}
