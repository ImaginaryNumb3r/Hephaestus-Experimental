package parsing.model;

import java.util.Objects;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Purpose:
 */
public class ContentToken extends AbstractParseNode {
    protected final StringBuilder _buffer;
    private final String _prefix;
    private final String _postfix;

    public ContentToken(String suffix) {
        this(suffix, suffix);
    }

    public ContentToken(String prefix, String postfix) {
        _buffer = new StringBuilder();
        _prefix = prefix;
        _postfix = postfix;

        if (postfix.isEmpty()) {
            throw new IllegalStateException("Content postfix must not be empty. TODO: Turn this into a logging error");
        }
    }

    @Override
    protected int parseImpl(String chars, int index) {
        // Ensure that prefix is correct.
        index = parseString(_prefix, chars, index);
        if (index == INVALID) return INVALID;

        // Parse text until postfix is encountered.
        int start = index;
        int nextIndex = INVALID;

        while (nextIndex == INVALID) {
            nextIndex = parseString(_postfix, chars, index);

            // Exhausted the string in the look for the postfix.
            if (index == chars.length()) return INVALID;
            ++index;
        }

        _buffer.append(chars, start, index - 1);

        return nextIndex;
    }

    /**
     * @param expected string for matching.
     * @param chars character array for parsing
     * @param index within character array
     * @return offset to the given index.
     * @throws IndexOutOfBoundsException if the index is bigger than the string length.
     */
    protected int parseString(String expected, String chars, final int index) {
        if (index >= chars.length()) {
            throw new IndexOutOfBoundsException("Parsing content token with an index bigger than the input. Expected: \"" + expected + "\"");
        }

        int offset;
        for (offset = 0; offset != expected.length(); ++offset) {
            char expectedChar = expected.charAt(offset);

            if (index + offset >= chars.length()) {
                return INVALID;
            }
            char actualChar = chars.charAt(index + offset);

            if (expectedChar != actualChar) return INVALID;
        }

        return index + offset;
    }

    public String getContent() {
        return _buffer.toString();
    }

    @Override
    public String toString() {
        return _prefix + _buffer.toString() + _postfix;
    }

    @Override
    public ParseNode deepCopy() {
        ContentToken copy = new ContentToken(_prefix, _postfix);
        copy.setContent(_buffer);

        return copy;
    }

    protected void reset() {
        _buffer.setLength(0);
    }

    public void setContent(CharSequence content) {
        _buffer.setLength(0);
        _buffer.append(content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContentToken)) return false;
        ContentToken that = (ContentToken) o;
        return Objects.equals(_buffer.toString(), that._buffer.toString()) &&
                Objects.equals(_prefix, that._prefix) &&
                Objects.equals(_postfix, that._postfix);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_buffer.toString(), _prefix, _postfix);
    }

    public void setData(ContentToken other) {
        reset();
        _buffer.append(other._buffer);
    }
}
