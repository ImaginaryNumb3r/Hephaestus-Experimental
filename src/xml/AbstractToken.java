package xml;

import static xml.TokenBuilder.Status.DONE;
import static xml.TokenBuilder.Status.PARSING;

/**
 * Creator: Patrick
 * Created: 17.03.2019
 * A token is a value that is constructed a character at a time.
 * Its construction must adhere the implemented rules or it receives an invalid status.
 * Tokens with invalid status cannot be operated upon.
 */
public abstract class AbstractToken implements TokenBuilder {
    protected TokenBuilder.Status _parseStatus;


    protected AbstractToken() {
        _parseStatus = TokenBuilder.Status.PARSING;
    }

    @Override
    public TokenBuilder.Status getStatus() {
        return _parseStatus;
    }

    protected void assertParsing() {
        if (_parseStatus != PARSING) {
            throw new IllegalStateException("Cannot append to token with invalid or finished status.");
        }
    }

    @Override
    public String asString() {
        if (_parseStatus != DONE) {
            throw new IllegalStateException("Cannot get string representation of unfinished token.");
        }

        String result = toString();
        if (result == null) {
            throw new IllegalStateException("Token has illegal state: toString returned null but parse status is: " + _parseStatus);
        }

        return result;
    }

    @Override
    public abstract String toString();
}
