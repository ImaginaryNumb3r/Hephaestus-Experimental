package parsing.model;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 */
public interface ParseNode {
    int INVALID = -1;

    /**
     *
     * @param chars
     * @param index
     * @return the new index after a successful consume. Or -1 if parsing failed.
     */
    ParseResult parse(String chars, int index);

    /**
     * @return String representation of the token if parsing is finished. Otherwise null.
     */
    String asString();

    /**
     * @return String representation of the token if parsing is finished. Otherwise null.
     */
    String toString();

    /**
     * @return a copy of the current node and all of its containing children.
     */
    ParseNode deepCopy();

}
