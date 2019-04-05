package parsing.xml;

import parsing.model.AbstractParseNode;
import parsing.model.CopyNode;
import parsing.model.ParseResult;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Grammar: TagHeader XMLTail
 *          <Name Attributes Whitespace ( ( > InnerNodes </Name> ) | /> )
 */
public class XMLTag extends AbstractParseNode implements CopyNode<XMLTag> {
    private final TagHeader _head;
    private final XMLTail _tail;

    public XMLTag() {
        _head = new TagHeader();
        _tail = new XMLTail();
    }

    public String getName() {
        return _head.getName();
    }

    public void setName(String name) {
        _head.setName(name);
    }

    public List<AttributeToken> attributes() {
        return _head.getAttributes();
    }

    public Optional<AttributeToken> fetchAttribute(String attributeName) {
        return attributes().stream()
                .filter(attribute -> attribute.getName().equals(attributeName))
                .findAny();
    }

    public AttributeToken getAttribute(String attributeName) {
        return attributes().stream()
                .filter(attribute -> attribute.getName().equals(attributeName))
                .findAny()
                .orElse(null);
    }

    public List<XMLTag> getTags(String tagName) {
        return children().stream()
                .filter(tag -> tag.getName().equals(tagName))
                .collect(Collectors.toList());
    }

    public boolean hasAttribute(String attributeName) {
        return attributes().stream()
                .anyMatch(attribute -> attribute.getName().equals(attributeName));
    }

    public boolean isClosed() {
        return _tail.isClosedTag();
    }

    public List<XMLTag> children() {
        return _tail.childTags();
    }

    public List<XMLNode> elements() {
        return _tail.nodes();
    }

    @Override
    public XMLTag deepCopy() {
        XMLTag copy = new XMLTag();
        copy.setData(this);

        return copy;
    }

    @Override
    public void setData(XMLTag other) {
        _head.setData(other._head);
        _tail.setData(other._tail);

        if (!equals(other)) {
            throw new IllegalStateException();
        }
    }

    @Override
    public void reset() {
        _head.reset();
        _tail.reset();
    }

    @Override
    protected ParseResult parseImpl(String chars, int index) {
        int nextIndex;

        ParseResult headResult = _head.parse(chars, index);
        if (headResult.isInvalid()) return headResult;
        nextIndex = headResult.index();

        ParseResult tailResult = _tail.parse(chars, nextIndex);
        if (tailResult.isInvalid()) return headResult;

        if (!isCorrect()) {
            return ParseResult.invalid(index, "Rejected parsing XML Tag because of inconsistent tag names", headResult, tailResult);
        }

        return tailResult;
    }

    /**
     * Validates the integrity of the whole tag.
     * @return true if XML specification is adhered.
     */
    private boolean isCorrect() {
        // Don't compare tail with head name if there is no tail name.
        if (_tail.isClosedTag()) return true;

        String name = _head.getName();
        String tailName = _tail.getName();

        return _tail.isClosedTag() || name.equals(tailName);
    }

    @Override
    public String toString() {
        return _head.toString() + _tail.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XMLTag)) return false;
        XMLTag xmlTag = (XMLTag) o;
        return Objects.equals(_head, xmlTag._head) &&
                Objects.equals(_tail, xmlTag._tail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_head, _tail);
    }
}
