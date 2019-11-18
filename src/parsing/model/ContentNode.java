package parsing.model;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Grammar: Prefix String Postfix
 * Where Prefix and Postfix are user defined terminals.
 *
 * @apiNote  This class is not meant to be used directly but to be inherited for a specific purpose like {@link ContentToken}.
 * @implNote Ideally, this class would be abstract.<br>
 *           However, to create a copy we need to access the constructor of this class.
 */
public class ContentNode extends AbstractParseNode {
    protected final StringBuilder _buffer;
    protected final String _prefix;
    protected final String _postfix;

    public ContentNode(String suffix) {
        this(suffix, suffix);
    }

    /**
     * @param prefix optional prefix which serves as accepting condition.
     * @param postfix that must not be empty and finishes the parsing for this node.
     */
    public ContentNode(@NotNull String prefix, @NotNull String postfix) {
        _buffer = new StringBuilder();
        _prefix = prefix;
        _postfix = postfix;

        if (postfix.isEmpty()) {
            throw new IllegalStateException("Content postfix must not be empty.");
        }
    }

    @Override
    protected ParseResult parseImpl(String chars, int index) {
        // Ensure that prefix is correct.
        ParseResult result = parseString(_prefix, chars, index);
        if (result.isInvalid()) return result;

        index = result.index();

        // Parse text until postfix is encountered.
        int start = index;
        result = null;

        while (result == null || !result.isValid()) {
            result = parseString(_postfix, chars, index);

            if (index == chars.length()) {
                String message = "Exhausted the string in the look for the postfix";
                return ParseResult.invalid(index, message);
            }
            ++index;
        }

        _buffer.append(chars, start, index - 1);

        return result;
    }

    /**
     * @param expected string for matching.
     * @param chars character array for parsing
     * @param index within character array
     * @return offset to the given index.
     * @throws IndexOutOfBoundsException if the index is bigger than the string length.
     */
    private ParseResult parseString(String expected, String chars, final int index) {
        if (index >= chars.length()) {
            throw new IndexOutOfBoundsException("Parsing content token with an index bigger than the input. Expected: \"" + expected + "\"");
        }

        int offset;
        for (offset = 0; offset != expected.length(); ++offset) {
            int nextIndex = index + offset;
            char expectedChar = expected.charAt(offset);

            if (nextIndex >= chars.length()) {
                return ParseResult.invalid(nextIndex, "Matching failed for pattern \"" + expected +"\". End of document.");
            }
            char actualChar = chars.charAt(nextIndex);

            if (expectedChar != actualChar) return ParseResult.invalid(nextIndex, "Matching failed for pattern \"" + expected +"\".");
        }

        return ParseResult.at(index + offset);
    }

    public String getContent() {
        return _buffer.toString();
    }

    @Override
    public String toString() {
        return _prefix + _buffer.toString() + _postfix;
    }

    @Override
    public ContentNode deepCopy() {
        ContentNode copy = new ContentNode(_prefix, _postfix);
        copy.setContent(_buffer);

        return copy;
    }

    public void reset() {
        _buffer.setLength(0);
    }

    public void setContent(CharSequence content) {
        _buffer.setLength(0);
        _buffer.append(content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContentNode)) return false;
        ContentNode that = (ContentNode) o;
        return Objects.equals(_buffer.toString(), that._buffer.toString()) &&
                Objects.equals(_prefix, that._prefix) &&
                Objects.equals(_postfix, that._postfix);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_buffer.toString(), _prefix, _postfix);
    }

    public void setData(ContentNode other) {
        reset();
        _buffer.append(other._buffer);
    }
}
