package parsing.model;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Series of whitespaces that is at least of size 1.
 */
public class SpaceToken extends OptionalConsumer {

    public SpaceToken() {
        super(Character::isWhitespace);
    }
}
