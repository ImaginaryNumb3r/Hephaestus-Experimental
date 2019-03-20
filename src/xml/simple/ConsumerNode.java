package xml.simple;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Accepts all input as long as the condition evaluates to true.
 * However, the empty string is not a valid state.
 */
public class ConsumerNode extends OptionalConsumer {

    public ConsumerNode(CharPredicate acceptCondition) {
        super(acceptCondition);
    }

    @Override
    public int parse(String chars, int index) {
        if (index >= chars.length()) {
            return INVALID;
        } else if (!_acceptCondition.test(chars.charAt(index))) {
            return INVALID;
        }

        return parseImpl(chars, index);
    }
}
