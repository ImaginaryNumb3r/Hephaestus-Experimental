package parsing.json;

import parsing.model.AbstractParseNode;
import parsing.model.CopyNode;
import parsing.model.ParseResult;

import java.util.Objects;
import java.util.Optional;

/**
 * Creator: Patrick
 * Created: 28.10.2019
 * Grammar: ( 'True' | 'TRUE' | 'true' | ... ) |
 *          ( 'False' | 'FALSE' | 'false' | ... )
 */
public final class JBool extends AbstractParseNode implements CopyNode<JBool> {
    private static final String FALSE = "false";
    private static final String TRUE = "true";
    private static final int INVALID = -1;
    private boolean _value;
    private boolean _parsed = false;

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

    public Boolean getBool() {
        return _parsed ? _value : null;
    }

    public Optional<Boolean> fetchBool() {
        return _parsed ? Optional.of(_value) : Optional.empty();
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
        return Objects.hash(_value);
    }

    @Override
    public JBool deepCopy() {
        JBool copy = new JBool();
        copy.setData(this);

        return copy;
    }

    @Override
    public void setData(JBool other) {
        _value = other._value;
        _parsed = other._parsed;
    }

    @Override
    public void reset() {
        _value = false;
        _parsed = false;
    }
}
