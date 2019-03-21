package parsing.xml;

import essentials.contract.NoImplementationException;
import parsing.model.ContentToken;
import parsing.model.CopyNode;
import parsing.model.EitherNode;

import java.util.Optional;

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

    public Optional<XMLTag> tag() {
        return first();
    }

    public Optional<CommentToken> getComment() {
        return second();
    }

    public Optional<String> getCommentContent() {
        return second().map(ContentToken::getContent);
    }

    @Override
    public XMLNode deepCopy() {
        XMLNode copy = new XMLNode();
        copy.getComment().setContent(getCommentContent());
        copy.tag(); // TODO: Copy
        throw new NoImplementationException();

        // return copy;
    }
}
