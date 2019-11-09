package parsing.json;

import parsing.model.AbstractParseNode;
import parsing.model.CopyNode;
import parsing.model.ParseResult;

/**
 * Creator: Patrick
 * Created: 28.10.2019
 * Purpose:
 */
public class JBool extends AbstractParseNode implements CopyNode<JBool> {
    private static final int INVALID = -1;
    private boolean _value;
    private boolean _parsed = false;
    private static final String FALSE = "false";
    private static final String TRUE = "true";

    public JBool() { }

    public JBool(boolean value) {
        _value = value;
    }

    @Override
    protected ParseResult parseImpl(String chars, final int index) {

        int nextIndex = tryParse(chars, FALSE, index);
        if (nextIndex == INVALID) {
            nextIndex = tryParse(chars, TRUE, index);
            _value = true;

            if (nextIndex == INVALID) return ParseResult.invalid(index, "Cannot parse bool value");
        }

        _parsed = true;
        return ParseResult.at(nextIndex);
    }

    private int tryParse(String text, String pattern, int index) {
        int tokenEnd = index + pattern.length();
        if (tokenEnd > text.length()) return INVALID;

        String token = text.substring(index, tokenEnd);
        return token.equalsIgnoreCase(pattern)
                ? tokenEnd
                : INVALID;
    }

    @Override
    public String toString() {
        return _parsed ? Boolean.toString(_value) : "unparsed bool";
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public JBool deepCopy() {
        return null;
    }

    @Override
    public void setData(JBool other) {

    }

    @Override
    public void reset() {

    }
}
