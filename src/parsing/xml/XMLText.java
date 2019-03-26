package parsing.xml;

import essentials.annotations.Package;
import parsing.model.ContentToken;
import parsing.model.CopyNode;
import parsing.model.ParseResult;

import java.util.Objects;

/**
 * Creator: Patrick
 * Created: 22.03.2019
 * The text that can be between nodes
 */
public class XMLText extends ContentToken implements CopyNode<XMLText> {
    private static final String POSTFIX = "<";

    public XMLText() {
        super("", POSTFIX);
    }

    @Override
    protected ParseResult parseImpl(String chars, final int index) {
        ParseResult result = super.parseImpl(chars, index);
        if (result.isInvalid()) return result;

        // Revert lookahead.
        int nextIndex = result.index() - POSTFIX.length();
        var next = ParseResult.at(nextIndex);

        // Hack: Also append comments. The grammar does not support it yet.
        while (next.isValid()) {
            CommentToken comment = new CommentToken();
            next = comment.parse(chars, next.index());

            if (next.isValid()) {
                _buffer.append(comment);
                result = next;

                XMLText text = new XMLText();
                next = text.parse(chars, next.index());

                if (next.isValid()) {
                    _buffer.append(text);
                    result = next;
                }
            }
        }

        // TODO: Remove and turn into a general rule for content token at parse-time.

        // if (nextIndex != result.index()) throw new IllegalStateException("TODO");

        return result;
    }

    @Package String getRaw() {
        return _buffer.toString() + POSTFIX;
    }

    @Override
    public String toString() {
        // Do not print postfix because it only serves as a termination marker.
        return _buffer.toString();
    }

    public String rawString() {
        return toString() + POSTFIX;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XMLText)) return false;
        XMLText that = (XMLText) o;
        return Objects.equals(_buffer.toString(), that._buffer.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(_buffer.toString());
    }

    @Override
    public XMLText deepCopy() {
        XMLText copy = new XMLText();
        setContent(_buffer);

        return copy;
    }

    @Override
    public void setData(XMLText other) {
        super.setData(other);
    }

    @Override
    public void reset() {
        super.reset();
    }
}
