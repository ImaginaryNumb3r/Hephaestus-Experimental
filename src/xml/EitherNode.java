package xml;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author Patrick Plieschnegger
 * Node with an "OR" Associativity.
 * Continues parsing as long as the input matches with the provided input.
 */
public class EitherNode extends AbstractToken {
    private final List<TokenBuilder> _tokenBuilders;
    private final List<TokenBuilder> _validTokens;
    private TokenBuilder _result;

    public EitherNode(TokenBuilder... tokens) {
        this(Arrays.asList(tokens));
    }

    public EitherNode(Collection<TokenBuilder> tokens) {
        _tokenBuilders = new ArrayList<>(tokens);
        _validTokens = new ArrayList<>(_tokenBuilders);
        _parseStatus = null;
    }

    @Override
    public Status accept(char character) {
        if (_parseStatus != Status.PARSING) {
            throw new IllegalStateException("Cannot accept further characters for status: " + _parseStatus);
        }

        var validTokens = new ArrayList<TokenBuilder>();
        TokenBuilder done = null;

        for (TokenBuilder validToken : _validTokens) {
            Status status = validToken.accept(character);

            switch (status) {
                case PARSING:
                    validTokens.add(validToken);
                    break;
                case INVALID:
                    break;
                case DONE:
                    done = validToken;
                    break;
            }
        }

        _validTokens.clear();
        _validTokens.addAll(validTokens);

        if (validTokens.isEmpty()) {
            _parseStatus = done != null ? Status.DONE : Status.INVALID;

            if (_parseStatus == Status.DONE) {
                _result = done;
            }

        } else {
            _parseStatus = Status.PARSING;
        }

        return _parseStatus;
    }

    @Override
    protected void partialReset() {
        _tokenBuilders.forEach(TokenBuilder::reset);
        _validTokens.forEach(TokenBuilder::reset);
        _result = null;
    }

    @Override
    public String toString() {
        return _result != null ? _result.toString() : null;
    }
}
