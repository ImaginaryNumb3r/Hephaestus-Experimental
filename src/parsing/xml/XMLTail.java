package parsing.xml;

import essentials.contract.NoImplementationException;
import parsing.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Creator: Patrick
 * Created: 21.03.2019
 * Grammar: ( > InnerNodes </Name> ) | />
 */
public class XMLTail extends SequenceNode implements CopyNode<XMLTail> {
    private final InnerNodes _nodes;
    private final TextToken _name;
    private final StringTerminal _fallback;
    private final WhitespaceToken _trailingWhitespace;
    private boolean _closedTag;

    public XMLTail() {
        super(new ArrayList<>());

        _nodes = new InnerNodes();
        _name = new TextToken();
        _trailingWhitespace = new WhitespaceToken();
        _sequence.addAll(Arrays.asList(
                new CharTerminal('>'),
                _nodes,
                _trailingWhitespace,
                new StringTerminal("</"), _name, new CharTerminal('>')
        ));

        _fallback = new StringTerminal("/>");
        _closedTag = false;
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

            if (status != INVALID) {
                _closedTag = true;
            }
        }

        return status;
    }

    @Override
    public void setData(XMLTail other) {
        reset();
        super.setData(other);
        /* var nodesCopy = other._nodes.getElements().stream()
                .map(XMLNode::deepCopy)
                .collect(Collectors.toList()); */
        // _nodes.getElements().addAll(nodesCopy);

        _nodes.setData(other._nodes);
        _name.setData(other._name);
        _closedTag = other._closedTag;
        _trailingWhitespace.setData(other._trailingWhitespace);

        if (!equals(other)) {
            throw new IllegalStateException();
        }
    }

    @Override
    public void reset() {
        super.reset();
        _nodes.getElements().clear();
        _name.reset();
        _closedTag = false;
    }

    @Override
    public XMLTail deepCopy() {
        XMLTail copy = new XMLTail();
        copy.setData(this);

        return copy;
    }

    @Override
    public String toString() {
        return _closedTag ? _fallback.toString() : super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XMLTail)) return false;
        if (!super.equals(o)) return false;
        XMLTail that = (XMLTail) o;
        return _closedTag == that._closedTag &&
                Objects.equals(_nodes, that._nodes) &&
                Objects.equals(_name, that._name) &&
                Objects.equals(_fallback, that._fallback) &&
                Objects.equals(_trailingWhitespace, that._trailingWhitespace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), _nodes, _name, _fallback, _trailingWhitespace, _closedTag);
    }
}
