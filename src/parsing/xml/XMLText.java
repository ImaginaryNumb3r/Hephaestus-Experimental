package parsing.xml;

import essentials.annotations.Package;
import parsing.model.ContentToken;
import parsing.model.CopyNode;

import java.util.Objects;

import static parsing.xml.CommentToken.COMMENT_START;

/**
 * Creator: Patrick
 * Created: 22.03.2019
 * The text that can be between nodes
 */
public class XMLText extends ContentToken implements CopyNode<XMLText> {
    private static final String POSTFIX = "<";
    private static final String POSTFIX2 = "<!--";

    public XMLText() {
        super("", POSTFIX);
    }

    @Override
    protected int parseImpl(String chars, int index) {
        index = super.parseImpl(chars, index);
        if (index == INVALID) return INVALID;

        // Revert lookahead.
        index = index - POSTFIX.length();
        int nextIndex = index;
        // index -= POSTFIX.length();

        // Hack: Also append comments. The grammar does not support it yet.
        while (nextIndex != INVALID) {
            CommentToken comment = new CommentToken();
            nextIndex = comment.parse(chars, nextIndex);

            if (nextIndex != INVALID) {
                _buffer.append(comment);
                index = nextIndex;

                XMLText text = new XMLText();
                nextIndex = text.parse(chars, nextIndex);

                if (nextIndex != INVALID) {
                    _buffer.append(text);
                    index = nextIndex;
                }
            }
        }

        /*
        // TODO: Remove and turn into a general rule for content token at parse-time.
        // Ensure that no invalid character is contained in the xml text.
        for (int i = 0; i != _buffer.length(); ++i) {
            if (_buffer.charAt(i) == '<') {
                return INVALID;
            }
        } */

        return index;
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
