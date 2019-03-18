package xml;

/**
 * @author Patrick Plieschnegger
 * A simple terminal token.
 */
public class StringToken extends AbstractToken {
    private final String _string;
    private int _parseIndex;

    public StringToken(String string) {
        _string = string;
        _parseIndex = 0;
    }

    @Override
    public Status accept(char character) {
        assertParsing();

        if (_string.charAt(_parseIndex++) != character) {
            _parseStatus = Status.INVALID;
        }

        if (_parseIndex == _string.length()) {
            _parseStatus = Status.DONE;
        }

        return _parseStatus;
    }

    @Override
    protected void partialReset() {
        _parseIndex = 0;
    }

    @Override
    public String toString() {
        return _parseStatus == Status.DONE ? _string : null;
    }
}
