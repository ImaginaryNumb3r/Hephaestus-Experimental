package parsing.xml;

import parsing.model.ContentToken;
import parsing.model.CopyNode;

/**
 * Creator: Patrick
 * Created: 21.03.2019
 * Purpose:
 */
public class CommentToken extends ContentToken implements CopyNode<CommentToken> {

    public CommentToken() {
        super("<!--", "-->");
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
