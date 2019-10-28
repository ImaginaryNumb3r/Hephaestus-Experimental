package parsing.model;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Series of zero to arbitrary whitespace characters.
 * TODO: Optimize by making the CharPredicate a static field for this class
 */
public class WhitespaceToken extends OptionalConsumer implements CopyNode<WhitespaceToken> {

    public WhitespaceToken() {
        super(Character::isWhitespace);
    }

    public void setWhitespace(CharSequence whitespace){
        if (!isBlank(whitespace)) {
            throw new IllegalArgumentException("Provided string must be a whitespace for whitespace tokens");
        }

        reset();
        _buffer.append(whitespace);
    }

    @Override
    public WhitespaceToken deepCopy() {
        WhitespaceToken copy = new WhitespaceToken();
        copy.setWhitespace(this);

        return copy;
    }

    @Override
    public void setData(WhitespaceToken other) {
        setWhitespace(other);
    }

    @Override
    public void reset() {
        _buffer.setLength(0);
    }
}
