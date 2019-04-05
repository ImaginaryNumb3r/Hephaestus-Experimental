package parsing.xml;

import essentials.contract.NoImplementationException;
import parsing.model.*;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

/**
 * Creator: Patrick
 * Created: 21.03.2019
 * Grammar: ( > InnerNodes </Name> ) | />
 */
public class XMLTail extends SequenceNode implements CopyNode<XMLTail> {
    private static final StringTerminal FALLBACK = new StringTerminal("/>");
    private final InnerNodes _nodes;
    private final TextToken _name;
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
                new StringTerminal("</"),
                new WhitespaceToken(), _name, new WhitespaceToken(),
                new CharTerminal('>')
        ));

        _closedTag = false;
    }

    /**
     * @return a list of all child tags and comments.
     */
    public List<XMLNode> nodes() {
        List<XMLNode> xmlNodes = _nodes.innerNodes().orElse(emptyList());

        return xmlNodes;
    }

    /**
     * @return the list of child tags. This does not include comments.
     */
    public List<XMLTag> childTags() {
        return nodes().stream()
                .filter(XMLNode::isTag)
                .map(XMLNode::toTag)
                .collect(Collectors.toList());
    }

    public boolean isText() {
        return _nodes.isText();
    }

    public boolean hasInnerNodes() {
        return _nodes.hasInnerNodes();
    }

    public Optional<String> getData() {
        return _nodes.getData();
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
    public ParseResult parse(String chars, int index) {
        ParseResult result = super.parse(chars, index);

        if (result.isInvalid()) {
            result = FALLBACK.parse(chars, index);

            if (result.isValid()) {
                _closedTag = true;
            }
        }

        return result;
    }

    //<editor-fold desc="Data Methods">
    @Override
    public void setData(XMLTail other) {
        reset();
        super.setData(other);

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
        return _closedTag ? FALLBACK.toString() : super.toString();
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
                Objects.equals(_trailingWhitespace, that._trailingWhitespace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), _nodes, _name, FALLBACK, _trailingWhitespace, _closedTag);
    }
    //</editor-fold>
}
