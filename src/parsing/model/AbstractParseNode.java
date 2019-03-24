package parsing.model;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Purpose:
 */
public abstract class AbstractParseNode implements ParseNode {

    @Override
    public int parse(String chars, int index) {
        // TODO: Reset function that sets everything to the initial state. This is important for consecutive calls.

        if (index >= chars.length() || index < 0) {
            if (index >= chars.length()) return INVALID;
            if (index < 0) throw new IndexOutOfBoundsException(index);
        }

        return parseImpl(chars, index);
    }

    protected abstract int parseImpl(String chars, int index);

    /**
     * @return String representation of the token if parsing is finished. Otherwise null.
     */
    public abstract String toString();

    @Override
    public String asString() {
        return toString();
    }

    /* Make abstract and force custom implementation.
    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract int hashCode(); */

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract int hashCode();
}
