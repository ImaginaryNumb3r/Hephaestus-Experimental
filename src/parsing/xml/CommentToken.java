package parsing.xml;

import parsing.model.ContentNode;
import parsing.model.CopyNode;

/**
 * Creator: Patrick
 * Created: 21.03.2019
 * '<!--' "Text" '--/>'
 */
public class CommentToken extends ContentNode implements CopyNode<CommentToken> {
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
}
