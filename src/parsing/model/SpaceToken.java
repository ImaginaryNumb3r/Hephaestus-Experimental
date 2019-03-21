package parsing.model;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Series of whitespaces that is at least of size 1.
 */
public class SpaceToken extends OptionalConsumer implements CopyNode<SpaceToken> {

    public SpaceToken() {
        super(Character::isWhitespace);
    }

    @Override
    public void setData(SpaceToken other) {
        reset();
        _buffer.append(other);
    }

    @Override
    public void reset() {
        _buffer.setLength(0);
    }

    @Override
    public SpaceToken deepCopy() {
        var copy = new SpaceToken();
        copy._buffer.append(_buffer);

        return copy;
    }
}
