package xml.simple;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Purpose:
 */
public class CharTerminal extends AbstractParseNode {
    private final char _character;

    public CharTerminal(char character) {
        _character = character;
    }

    @Override
    protected int parseImpl(String chars, int index) {
        char character = chars.charAt(index);

        return character == _character ? index + 1 : INVALID;
    }

    @Override
    public String toString() {
        return Character.toString(_character);
    }
}
