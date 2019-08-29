package parsing.xml;

import parsing.model.ContentToken;
import parsing.model.CopyNode;

import javax.xml.stream.events.EndDocument;
import java.util.Objects;

/**
 * Creator: Patrick
 * Created: 21.03.2019
 * Grammar: '<!--' content '-->'
 */
public class CommentToken extends ContentToken implements CopyNode<CommentToken> {
    public static String COMMENT_START = "<!--";
    public static String COMMENT_END = "-->";

    public CommentToken() {
        super(COMMENT_START, COMMENT_END);
    }

    @Override
    public CommentToken deepCopy() {
        CommentToken copy = new CommentToken();
        copy._buffer.append(_buffer);

        return copy;
    }

    @Override
    public void reset() {
        super.reset();
    }

    public void setData(CommentToken other) {
        reset();
        _buffer.append(other.getContent());
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof CommentToken && super.equals(o);
    }

    @Override
    public int hashCode() {
        // Permutate
        return Objects.hash(super.hashCode());
    }

    @Override
    public String toString() {
        return COMMENT_START + _buffer + COMMENT_END;
    }
}
