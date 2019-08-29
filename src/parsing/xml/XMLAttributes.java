package parsing.xml;

import parsing.model.CopyNode;
import parsing.model.MultiNode;

import java.util.stream.Collectors;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * ZODO: Rename to XML Attributes?
 */
public class XMLAttributes extends MultiNode<AttributeToken> implements CopyNode<XMLAttributes> {

    protected XMLAttributes() {
        super(AttributeToken::new);
    }

    @Override
    public XMLAttributes deepCopy() {
        var elements = _elements.stream()
                .map(CopyNode::deepCopy)
                .collect(Collectors.toList());

        XMLAttributes copy = new XMLAttributes();
        copy._elements.addAll(elements);

        return copy;
    }

    @Override
    public void setData(XMLAttributes other) {
        _elements.clear();

        var elementsCopy = other._elements.stream()
                .map(AttributeToken::deepCopy)
                .collect(Collectors.toList());
        _elements.addAll(elementsCopy);
    }

    @Override
    public void reset() {
        super.reset();
    }


}
