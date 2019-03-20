package xml;

import java.util.Objects;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Accepts all input as long as the condition evaluates to true.
 */
public class OptionalConsumer extends AbstractParseNode {
    protected final StringBuilder _buffer;
    protected final CharPredicate _acceptCondition;

    public OptionalConsumer(CharPredicate acceptCondition) {
        _buffer = new StringBuilder();
        _acceptCondition = acceptCondition;
    }

    @Override
    protected int parseImpl(String chars, int index) {

        char ch = chars.charAt(index);
        while (_acceptCondition.test(ch)) {

            // TODO: Add range of characters after loop.
            _buffer.append(ch);
            ch = chars.charAt(++index);
        }

        return index;
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
        return Objects.hash(_buffer, _acceptCondition);
    }
}
