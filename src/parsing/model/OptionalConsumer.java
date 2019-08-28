package parsing.model;

import essentials.annotations.Package;

import java.util.Objects;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Accepts all input as long as the condition evaluates to true.
 */
public class OptionalConsumer extends AbstractParseNode implements CharSequence {
    @Package final StringBuilder _buffer;
    @Package final CharPredicate _acceptCondition;

    @Package OptionalConsumer(CharPredicate acceptCondition) {
        _buffer = new StringBuilder();
        _acceptCondition = acceptCondition;
    }

    @Override
    protected ParseResult parseImpl(String chars, final int start) {
        int offset = 0;

        char ch = chars.charAt(start);
        while (_acceptCondition.test(ch)) {

            // TODO: Add range of characters after loop.
            ++offset;
            ch = chars.charAt(start + offset);
        }

        String slice = chars.substring(start, start + offset);
        _buffer.append(slice);

        return ParseResult.at(start + offset);
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

    @Override
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
