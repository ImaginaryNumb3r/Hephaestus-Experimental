package xml;

import lib.Strings;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author Patrick Plieschnegger
 * Node with N-Associativity. This includes an acceptable range of zero to arbitrary nodes.
 * Continues parsing as long as the input matches with the provided input.
 */
public class MultiNode extends AbstractToken {
    private final List<TokenBuilder> _parsedTokens;
    private final Supplier<TokenBuilder> _tokenConstructor;
    private TokenBuilder _currentToken;

    public MultiNode(Supplier<TokenBuilder> tokenConstructor) {
        _parsedTokens = new ArrayList<>();
        _tokenConstructor = tokenConstructor;
        _currentToken = null;
    }

    @Override
    public Status accept(char character) {
        assertParsing();

        if (_currentToken == null) {
            _currentToken = _tokenConstructor.get();
        }

        // New status? CONTINUE

        Status status = _currentToken.accept(character);
        switch (status) {
            case PARSING:
                break;
            case INVALID:
                if (_currentToken.getStatus() != Status.DONE) {
                    _parseStatus = Status.INVALID;
                    break;
                }
                _parseStatus = Status.DONE;
                // Fall-through
            case DONE:
                _parsedTokens.add(_currentToken);
                _currentToken = null;

                break;
                default: throw new EnumConstantNotPresentException(Status.class, Strings.toString(status, "null"));
        }

        return null;
    }

    @Override
    protected void partialReset() {
        _parsedTokens.clear();
        _currentToken = null;
    }

    @Override
    public String toString() {
        return _parseStatus == Status.DONE
                ? Strings.concat(_parsedTokens)
                : null;
    }
}
