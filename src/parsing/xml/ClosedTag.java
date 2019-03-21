package parsing.xml;

import parsing.model.CopyNode;
import parsing.model.SequenceNode;
import parsing.model.StringTerminal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * '<' Text Blank Attributes Blank '/>'
 */
public class ClosedTag extends SequenceNode implements CopyNode<ClosedTag> {
    private final TagHeader _head;

    public ClosedTag() {
        super(new ArrayList<>());
        _head = new TagHeader();

        _sequence.addAll(Arrays.asList(
            _head, new StringTerminal("/>")
        ));
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

    public String getTrailingWhitespace() {
        return _head.getWhitespace();
    }

    public void setTrailingWhitespace(CharSequence whitespace) {
        _head.setWhitespace(whitespace);
    }

    @Override
    public ClosedTag deepCopy() {
        ClosedTag copy = new ClosedTag();
        TagHeader headCopy = _head.deepCopy();

        var attributesCopy = headCopy.getAttributes();

        copy.setName(getName());
        copy.setTrailingWhitespace(getTrailingWhitespace());
        copy.getAttributes().addAll(attributesCopy);

        return copy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClosedTag)) return false;
        if (!super.equals(o)) return false;
        ClosedTag that = (ClosedTag) o;
        return Objects.equals(_head, that._head);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), _head);
    }
}
