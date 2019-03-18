package xml;

/**
 * Creator: Patrick
 * Created: 17.03.2019
 */
public class WhitespaceToken extends ConsumerNode {

    /**
     * Accepts whitespaces until a non-whitespace is encountered.
     * @param character input
     * @return true for whitespace characters.
     */
    @Override
    public boolean test(char character) {
        return Character.isWhitespace(character);
    }
}
