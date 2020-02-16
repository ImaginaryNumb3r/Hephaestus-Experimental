package parsing.model;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Purpose:
 */
public abstract class AbstractParseNode implements ParseNode {

    @Override
    public ParseResult parse(String chars, int index) {

        if (index >= chars.length() || index < 0) {
            if (index >= chars.length()) return ParseResult.invalid(index, "Index is larger than document length.");
            if (index < 0) throw new IndexOutOfBoundsException(index);
        }

        return parseImpl(chars, index);
    }

    protected abstract ParseResult parseImpl(String chars, int index);

    @Override
    public String asString() {
        return toString();
    }

    /**
     * @return String representation of the token if parsing is finished. Otherwise null.
     */
    public abstract String toString();

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract int hashCode();
}
