package parsing.model;

import java.util.Objects;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Accepts all input as long as the condition evaluates to true (even the empty string).
 */
public class OptionalConsumer implements ParseNode, CharSequence {
    /*package*/ final StringBuilder _buffer;
    /*package*/ final CharPredicate _acceptCondition;

    /*package*/ OptionalConsumer(CharPredicate acceptCondition) {
        _buffer = new StringBuilder();
        _acceptCondition = acceptCondition;
    }

    public ParseResult parse(String chars, int index) {
        if (chars.length() == index) return ParseResult.at(index);

        char ch = chars.charAt(index);
        while (_acceptCondition.test(ch)) {

            // TODO: Add range of characters after loop.
            _buffer.append(ch);
            if (chars.length() == ++index) return ParseResult.at(index);

            ch = chars.charAt(index);
        }

        return ParseResult.at(index);
    }

    @Override
    public String asString() {
        return toString();
    }

    @Override
    public int length() {
        return _buffer.length();
    }

    @Override
    public char charAt(int index) {
        return _buffer.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return _buffer.subSequence(start, end);
    }

    @Override
    public String toString() {
        return _buffer.toString();
    }

    public OptionalConsumer deepCopy() {
        var copy = new OptionalConsumer(_acceptCondition);
        copy._buffer.append(_buffer);

        return copy;
    }

    /**
     * The accepting condition is not copied because this method only sets the data.
     * It does not change the behavior of the object.
     * @param other
     */
    protected void setData(OptionalConsumer other) {
        reset();
        _buffer.append(other);
    }

    protected void reset() {
        _buffer.setLength(0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OptionalConsumer)) return false;
        OptionalConsumer that = (OptionalConsumer) o;
        return Objects.equals(_buffer.toString(), that._buffer.toString()) &&
                Objects.equals(_acceptCondition, that._acceptCondition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_buffer.toString(), _acceptCondition);
    }

    protected boolean isBlank(CharSequence sequence) {
        for (int i = 0; i != sequence.length(); ++i) {
            char ch = sequence.charAt(i);

            boolean isWhitespace = Character.isWhitespace(ch);
            if (!isWhitespace) {
                return false;
            }
        }

        return true;
    }

}
