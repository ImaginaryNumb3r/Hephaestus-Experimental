package xml;

/**
 * @author Patrick Plieschnegger
 */
public class TextToken extends ConsumerNode {

    /**
     * Accepts all characters until a whitespace is encountered.
     * @param character input
     * @return true for non-whitespace characters.
     */
    @Override
    public boolean test(char character) {
        return !Character.isWhitespace(character);
    }
}
