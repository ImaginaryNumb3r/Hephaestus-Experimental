package xml.simple;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Accepts all input as long as the condition evaluates to true.
 */
public class OptionalConsumer extends AbstractParseNode {
    private final StringBuilder _buffer;
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

/*            if (index + 1 != chars.length) {
                return FINISHED;
            }*/
        }

        return index;
    }

    @Override
    public String toString() {
        return _buffer.toString();
    }
}
