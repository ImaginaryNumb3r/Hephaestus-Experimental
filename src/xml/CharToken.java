package xml;

import static xml.TokenBuilder.Status.DONE;
import static xml.TokenBuilder.Status.INVALID;

/**
 * @author Patrick Plieschnegger
 */
public class CharToken extends AbstractToken {
    private final char _character;

    public CharToken(char character) {_character = character;}

    @Override
    public Status accept(char character) {
        assertParsing();

        _parseStatus = _character == character ? DONE : INVALID;
        return _parseStatus;
    }

    @Override
    protected void partialReset() { /* No partial status to reset. */}

    @Override
    public String toString() {
        return _parseStatus == DONE ? String.valueOf(_character) : null;
    }
}
