package parsing.xml;

import parsing.model.AbstractParseNode;
import parsing.model.CopyNode;

import java.util.List;
import java.util.Objects;

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

    public List<AttributeToken> getAttributes() {
        return _head.getAttributes();
    }

    public boolean isClosed() {
        return _tail.isClosedTag();
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
    protected int parseImpl(String chars, int index) {
        int nextIndex;
        /* nextIndex = _leadingWhitespace.parse(chars, index);
        if (nextIndex != INVALID) index = nextIndex; */

        nextIndex = _head.parse(chars, index);

        if (nextIndex != INVALID) {
            nextIndex = _tail.parse(chars, nextIndex);
        }

        if (nextIndex == INVALID || !isCorrect()) {
            return INVALID;
        }

        return nextIndex;
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
