package parsing.xml;

import parsing.model.CopyNode;
import parsing.model.MultiNode;
import parsing.model.NodeTuple;
import parsing.model.WhitespaceToken;

import java.util.stream.Collectors;

/**
 * @author Patrick Plieschnegger
 */
public class XMLComments extends MultiNode<NodeTuple<CommentToken, WhitespaceToken>> implements CopyNode<XMLComments> {

    public XMLComments() {
        super( () -> new NodeTuple<>(new CommentToken(), new WhitespaceToken()) );
    }

    @Override
    public void reset() {
        super.reset();
    }

    @Override
    public XMLComments deepCopy() {
        var elementsCopy = super.deepCopy().getElements();
        XMLComments comments = new XMLComments();
        comments._elements.addAll(elementsCopy);

        return comments;
    }

    @Override
    public void setData(XMLComments other) {
        reset();

        var elementsCopy = other._elements.stream()
            .map(CopyNode::deepCopy)
            .collect(Collectors.toList());

        _elements.addAll(elementsCopy);
    }
}
