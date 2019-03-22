package parsing.xml;

import essentials.contract.NoImplementationException;
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
//    private final WhitespaceToken _leadingWhitespace;
    private final TagHeader _head;
    private final XMLTail _tail;

    public XMLTag() {
//        _leadingWhitespace = new WhitespaceToken();
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

/*    public String getLeadingWhitespace() {
        return _leadingWhitespace.toString();
    }

    public void setLeadingWhitespace(CharSequence whitespace) {
        _leadingWhitespace.setWhitespace(whitespace);
    }*/

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
    }

    @Override
    public void reset() {
        _head.reset();
        _tail.reset();
    }

    @Override
    protected int parseImpl(String chars, int index) {
        int finalIndex;
        /* finalIndex = _leadingWhitespace.parse(chars, index);
        if (finalIndex != INVALID) index = finalIndex; */

        finalIndex = _head.parse(chars, index);

        if (finalIndex != INVALID) {
            finalIndex = _tail.parse(chars, finalIndex);
        }

        if (finalIndex == INVALID || !isCorrect()) {
            return INVALID;
        }

        return finalIndex;
    }

    private boolean isCorrect() {
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
