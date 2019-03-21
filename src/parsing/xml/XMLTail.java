package parsing.xml;

import essentials.contract.NoImplementationException;
import parsing.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Creator: Patrick
 * Created: 21.03.2019
 * Purpose:
 */
public class XMLTail extends SequenceNode implements CopyNode<XMLTail> {
    private final MultiNode<XMLNode> _nodes;
    private final TextToken _name;
    private final StringTerminal _fallback;
    private boolean _closedTag;

    public XMLTail() {
        super(new ArrayList<>());

        _nodes = new MultiNode<>(XMLNode::new);
        _name = new TextToken();
        _sequence.addAll(Arrays.asList(
                new CharTerminal('>'),
                _nodes,
                new StringTerminal("</"), _name, new CharTerminal('>')
        ));

        _fallback = new StringTerminal("/>");
    }

    public List<XMLNode> nodes() {
        return _nodes.getElements();
    }

    public List<XMLNode> children() {
        return _nodes.getElements().stream()
                .filter(XMLNode::isTag)
                .collect(Collectors.toList());
    }

    /**
     * @return name of closing tag.
     * @throws IllegalStateException if the tag is closed.
     */
    public String getName() {
        if (_closedTag) {
            throw new IllegalStateException("Cannot reference name from closed tag");
        }

        return _name.toString();
    }

    /**
     * @param name of closing tag.
     * @throws IllegalStateException if the tag is closed.
     */
    public void setName(String name) {
        if (_closedTag) {
            throw new IllegalStateException("Cannot set name of a closed tag");
        }

        _name.setText(name);
    }

    public boolean isClosedTag() {
        return _closedTag;
    }

    @Override
    public int parse(String chars, int index) {
        int status = super.parse(chars, index);

        // Fallback to closed token.
        if (status == INVALID) {
            status = _fallback.parse(chars, index);
            _closedTag = true;
        }

        return status;
    }

    @Override
    public void setData(XMLTail other) {
        reset();
        var nodesCopy = other._nodes.getElements().stream()
                .map(XMLNode::deepCopy)
                .collect(Collectors.toList());

        _nodes.getElements().addAll(nodesCopy);
        _name.setData(other._name);
    }

    @Override
    public void reset() {
        _nodes.getElements().clear();
        _name.reset();
    }

    @Override
    public XMLTail deepCopy() {
        throw new NoImplementationException();
    }
}
