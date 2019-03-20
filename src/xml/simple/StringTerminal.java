package xml.simple;

/**
 * @author Patrick Plieschnegger
 */
public class StringTerminal extends AbstractParseNode {
    private final String _terminal;

    public StringTerminal(String terminal) {_terminal = terminal;}

    @Override
    protected int parseImpl(String chars, int index) {
        int offset = 0;
        for (char expected : _terminal.toCharArray()) {
            char ch = chars.charAt(index + offset++);

            if (ch != expected) return INVALID;
        }

        return index + offset;
    }

    @Override
    public String toString() {
        return _terminal;
    }
}
