package parsing.xml;

import parsing.model.CopyNode;
import parsing.model.EitherNode;

/**
 * Creator: Patrick
 * Created: 21.03.2019
 * May be a tag or a comment.
 */
public class XMLNode extends EitherNode<XMLTag, CommentToken> implements CopyNode<XMLNode> {

    public XMLNode() {
        // Comment Token as fallback.
        super(new XMLTag(), new CommentToken());
    }

    public XMLTag getXMLTag() {
        return _optional;
    }

    public CommentToken getComment() {
        return _mandatory;
    }

    public String getCommentContent() {
        return _mandatory.getContent();
    }

    @Override
    public XMLNode deepCopy() {
        XMLNode copy = new XMLNode();
        copy.getComment().setContent(getCommentContent());
        copy.getXMLTag(); // TODO: Copy

        return copy;
    }
}
