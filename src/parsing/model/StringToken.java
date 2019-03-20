package parsing.model;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Purpose:
 */
public class StringToken extends OptionalConsumer {

    // Potential problem: This also accepts empty string texts.
    public StringToken() {
        super(Character::isAlphabetic);
    }
}
