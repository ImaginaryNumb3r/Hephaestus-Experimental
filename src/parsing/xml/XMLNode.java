package parsing.xml;

import essentials.contract.NoImplementationException;
import parsing.model.CopyNode;
import parsing.model.EitherNode;

/**
 * Creator: Patrick
 * Created: 21.03.2019
 * May be a tag or a comment.
 */
public class XMLNode extends EitherNode implements CopyNode<XMLNode> {

    public XMLNode() {
        // Comment Token as fallback.
        super(new XMLTag(), new CommentToken());
    }

    @Override
    public XMLNode deepCopy() {
        throw new NoImplementationException();
    }
}
