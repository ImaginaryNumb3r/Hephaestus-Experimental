package xml;

import lib.Strings;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrick Plieschnegger
 * A token which requires a strict sequence of ordered tokens.
 */
public class SequenceNode extends AbstractToken {
    private final List<AbstractToken> _sequence;
    private int _tokenIndex;
    private String _result;

    public SequenceNode(List<AbstractToken> sequence) {
        _sequence = new ArrayList<>(sequence);
        _tokenIndex = 0;
        _result = null;
    }

    @Override
    public Status accept(char character) {
        assertParsing();
        AbstractToken currentToken = _sequence.get(_tokenIndex);

        Status status = currentToken.accept(character);
        switch (status) {
            case PARSING:
                break;
            case INVALID:
                _parseStatus = Status.INVALID;
                break;
            case DONE:
                ++_tokenIndex;
                break;
        }

        if (_tokenIndex == _sequence.size()) {
            _parseStatus = Status.DONE;
            _result = Strings.joinToString(_sequence, " ");
        }

        return _parseStatus;
    }

    @Override
    protected void partialReset() {
        _sequence.forEach(TokenBuilder::reset);
        _tokenIndex = 0;
        _result = null;
    }

    @Override
    public String toString() {
        return _result;
    }
}
