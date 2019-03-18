package xml;

import static xml.TokenBuilder.Status.*;

/**
 * Creator: Patrick
 * Created: 17.03.2019
 * Purpose:
 */
public class ContentToken extends AbstractToken {
    protected final StringBuilder _buffer;
    private final String _prefix;
    private final String _postfix;
    private boolean _parsingPrefix;

    public ContentToken(String suffix) {
        this(suffix, suffix);
    }

    public ContentToken(String prefix, String postfix) {
        if (prefix.isEmpty() || postfix.isEmpty()) {
            throw new IllegalArgumentException("Postfix and Prefix must not be empty");
        }
        _buffer = new StringBuilder();

        _prefix = prefix;
        _postfix = postfix;
        _parsingPrefix = true;
    }

    @Override
    public Status accept(char character) {
        if (_parseStatus != PARSING) {
            throw new IllegalStateException("Cannot append to token with invalid or finished status.");
        }

        _buffer.append(character);
        return _parsingPrefix ? parsePrefix() : parsePostfix();
    }

    private Status parsePrefix() {
        if (!checkPrefix()) {
            _parseStatus = INVALID;
            return INVALID;
        }

        // Buffer has parsed prefix correctly and real parsing begins.
        if (_buffer.length() == _prefix.length()) {
            _buffer.setLength(0);
            _parsingPrefix = false;
        }

        return PARSING;
    }

    private boolean checkPrefix() {
        boolean equals = true;

        for (int i = 0; i != _buffer.length() && equals; ++i) {
            equals = _buffer.charAt(i) == _prefix.charAt(i);
        }

        return equals;
    }

    private Status parsePostfix() {
        Status parsing = PARSING;

        if (_postfix.length() >= _buffer.length()) {
            for (int i = 0; i != _postfix.length(); ++i) {
                char bufferChar = _buffer.charAt(_buffer.length() - i);
                char postfixChar = _postfix.charAt(_postfix.length() - i);

                // Continue parsing if the buffer end does not match the postfix.
                if (bufferChar != postfixChar) {
                    return PARSING;
                }
            }

            // If postfix matches end of buffer the token is completely parsed.
            parsing = DONE;
        }

        return parsing;
    }

    @Override
    protected void partialReset() {
        _buffer.setLength(0);
        _parsingPrefix = true;
    }

    @Override
    public String toString() {
        return _buffer.toString();
    }
}
