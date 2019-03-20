package xml.simple;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Purpose:
 */
public class OptionalTextToken extends OptionalConsumer {

    // Potential problem: This also accepts empty string texts.
    public OptionalTextToken() {
        super(Character::isAlphabetic);
    }
}
