package parsing.model;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * TODO: Deal with the problem that some tokens have state.
 *
 * Wording:
 *  - Token
 *  - Node
 *  - Tag
 *  - Terminal -> Elemental string for direct parsing
 *
 */
public interface ParseNode {
    int INVALID = -1;

    /**
     *
     * @param chars
     * @param index
     * @return the new index after a successful parse. Or -1 if parsing failed.
     */
    int parse(String chars, int index);

    /**
     * @return String representation of the token if parsing is finished. Otherwise null.
     */
    String toString();

    ParseNode deepCopy();

}
