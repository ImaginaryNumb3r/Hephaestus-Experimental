package parsing.model;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Accepts all input as long as the condition evaluates to true. However, the <b>empty string is not a valid state</b>.
 *
 * In other words, this node will build a buffer of all characters which are accepted in the predicate.
 * Characters are added to the buffer as long as the predicate is satisfied.
 * Parsing will finish as soon as a character does not satisfy the predicate.
 * The only way for invalid parsing is if the first character is not accepted by the predicate.
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
