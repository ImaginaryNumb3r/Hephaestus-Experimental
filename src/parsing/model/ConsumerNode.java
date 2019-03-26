package parsing.model;

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
    public ParseResult parse(String chars, int index) {
        if (index >= chars.length()) {
            return ParseResult.invalid(index, "Index is larger than document length.");
        } else if (!_acceptCondition.test(chars.charAt(index))) {
            return ParseResult.invalid(index, "Violated accept condition with char: " + chars.charAt(index));
        }

        return parseImpl(chars, index);
    }
}
