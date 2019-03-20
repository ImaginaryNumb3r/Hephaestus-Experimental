package parsing.model;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Purpose:
 */
public class StringToken extends OptionalConsumer implements CopyNode<StringToken> {

    // Potential problem: This also accepts empty string texts.
    public StringToken() {
        super(Character::isAlphabetic);
    }

    @Override
    public StringToken deepCopy() {
        StringToken copy = new StringToken();
        copy._buffer.append(_buffer);

        return copy;
    }
}
