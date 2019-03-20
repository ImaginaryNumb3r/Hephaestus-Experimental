package xml.simple;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Series of zero to arbitrary whitespace characters.
 */
public class WhitespaceToken extends OptionalConsumer {

    public WhitespaceToken() {
        super(Character::isWhitespace);
    }
}
