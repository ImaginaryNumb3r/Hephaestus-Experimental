package xml;

import static xml.TokenBuilder.Status.PARSING;

/**
 * Creator: Patrick
 * Created: 17.03.2019
 * A token is a value that is constructed a character at a time.
 * Its construction must adhere the implemented rules or it receives an invalid status.
 * Tokens with invalid status cannot be operated upon.
 */
public abstract class AbstractToken implements TokenBuilder {
    private static final Status DEFAULT_STATUS = PARSING;
    protected TokenBuilder.Status _parseStatus;

    protected AbstractToken() {
        _parseStatus = DEFAULT_STATUS;
    }

    @Override
    public TokenBuilder.Status getStatus() {
        return _parseStatus;
    }

    public boolean canAccept() {
        return _parseStatus.canAccept();
    }

    public boolean isValid() {
        return _parseStatus.isValid();
    }

    protected void assertParsing() {
        if (_parseStatus.canAccept()) {
            throw new IllegalStateException("Cannot append to token with invalid or finished status.");
        }
    }

    @Override
    public String asString() {
        if (_parseStatus.isValid()) {
            throw new IllegalStateException("Cannot get string representation of unfinished token.");
        }

        String result = toString();
        if (result == null) {
            throw new IllegalStateException("Token has illegal state: toString returned null but parse status is: " + _parseStatus);
        }

        return result;
    }

    public void reset() {
        _parseStatus = DEFAULT_STATUS;
        partialReset();
    }

    /**
     * This is to be implemented by inheriting classes.
     * Each class is responsible to reset the fields it adds.
     */
    protected abstract void partialReset();

    /**
     * @return String representation of the token if parsing is finished. Otherwise null.
     */
    public abstract String toString();

    protected String getString() {
        String string = toString();
        return string != null ? string : "";
    }
}
