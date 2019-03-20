package xml.simple;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Purpose:
 */
public class CharToken extends AbstractParseNode {
    private final char _character;

    public CharToken(char character) {
        _character = character;
    }

    @Override
    protected int parseImpl(char[] chars, int index) {
        char character = chars[index];

        return character == _character ? index + 1 : INVALID;
    }

    @Override
    public String toString() {
        return Character.toString(_character);
    }
}
