package parsing.model;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * A token that keeps parsing all characters until a condition is met.
 */
public final class StringToken extends OptionalConsumer implements CopyNode<StringToken> {

    public StringToken() {
        super(Character::isAlphabetic);
    }

    @Override
    public StringToken deepCopy() {
        StringToken copy = new StringToken();
        copy._buffer.append(_buffer);

        return copy;
    }

    @Override
    public void reset() {
        super.reset();
    }

    @Override
    public void setData(StringToken other) {
        super.setData(other);
    }
}
