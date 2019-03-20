package xml.simple;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Purpose:
 */
public class ContentToken extends AbstractParseNode {
    private final StringBuilder _buffer;
    private final String _prefix;
    private final String _postfix;

    public ContentToken(String suffix) {
        this(suffix, suffix);
    }

    public ContentToken(String prefix, String postfix) {
        _buffer = new StringBuilder();
        _prefix = prefix;
        _postfix = postfix;
    }

    @Override
    protected int parseImpl(char[] chars, int index) {
        // Ensure that prefix is correct.
        index = parseString(_prefix, chars, index);
        if (index == INVALID) return INVALID;

        // Parse text until postfix is encountered.
        int leadingIndex;
        while ((leadingIndex = parseString(_postfix, chars, index)) == INVALID ) {

            while (index != leadingIndex) {
                _buffer.append(chars[index]);
                ++index;
            }
        }

        return index;
    }

    /**
     * @param string string for matching.
     * @param chars character array for parsing
     * @param index within character array
     * @return offset to the given index
     */
    private int parseString(String string, char[] chars, final int index) {

        int offset;
        for (offset = 0; offset != string.length(); ++offset) {
            char expected = string.charAt(offset);
            char actual = chars[index + offset];

            if (expected != actual) return INVALID;
        }

        return index + offset;
    }

    @Override
    public String toString() {
        return _buffer.toString();
    }
}
