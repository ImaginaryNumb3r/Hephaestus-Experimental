package parsing.xml;

import parsing.model.ContentToken;
import parsing.model.CopyNode;
import parsing.model.EitherNode;

import java.util.Optional;

/**
 * Creator: Patrick
 * Created: 21.03.2019
 * May be a getTag or a comment.
 */
public class XMLNode extends EitherNode<XMLTag, CommentToken> implements CopyNode<XMLNode> {

    public XMLNode() {
        // Comment Token as fallback.
        super(new XMLTag(), new CommentToken());
    }

    public Optional<XMLTag> getTag() {
        return first();
    }

    public Optional<CommentToken> getComment() {
        return second();
    }

    public Optional<String> getCommentContent() {
        return second().map(ContentToken::getContent);
    }

    @Override
    public Optional<XMLTag> first() {
        return super.first();
    }

    public boolean isTag() {
        return super.hasSecond();
    }

    public boolean isComment() {
        return super.hasSecond();
    }

    @Override
    public XMLNode deepCopy() {
        XMLNode copy = new XMLNode();
        copy.setData(this); /*
        Nulls.ifPresent(_mandatory, copy._mandatory::setData);
        Nulls.ifPresent(_optional, copy._optional::setData);
        copy._status = _status; */

        return copy;
    }

    @Override
    public void reset() {
        super.reset();
    }

    @Override
    public void setData(XMLNode other) {
        super.setData(other);
    }
}
