package parsing.xml;

import parsing.model.CopyNode;
import parsing.model.MultiNode;

import java.util.stream.Collectors;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Purpose:
 */
public class AttributesNode extends MultiNode<AttributeToken> {

    protected AttributesNode() {
        super(AttributeToken::new);
    }

    @Override
    public AttributesNode deepCopy() {
        var elements = _elements.stream()
                .map(CopyNode::deepCopy)
                .collect(Collectors.toList());

        AttributesNode copy = new AttributesNode();
        copy._elements.addAll(elements);

        return copy;
    }
}
