package xml.simple;

import essentials.annotations.Package;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Purpose:
 */
@Package abstract class AbstractParseNode implements ParseNode {

    @Override
    public int parse(String chars, int index) {
        if (index >= chars.length()) {
            return INVALID;
        }

        return parseImpl(chars, index);
    }

    protected abstract int parseImpl(String chars, int index);

    /**
     * @return String representation of the token if parsing is finished. Otherwise null.
     */
    public abstract String toString();

}
