package xml;

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
    private final TextToken _name;
    private final MultiNode<AttributeToken> _attributes;
    private final WhitespaceToken _trailingWhitespace;

    public ClosedTag() {
        super(new ArrayList<>());
        _name = new TextToken();
        _attributes = new MultiNode<>(AttributeToken::new);

        _trailingWhitespace = new WhitespaceToken();
        _sequence.addAll(Arrays.asList(
            new CharTerminal('<'), _name, _attributes, _trailingWhitespace, new StringTerminal("/>")
        ));
    }

    public String getName() {
        return _name.toString();
    }

    public void setName(String name) {
        _name.setText(name);
    }

    public List<AttributeToken> getAttributes() {
        return _attributes.getElements();
    }

    public String getTrailingWhitespace() {
        return _trailingWhitespace.toString();
    }

    public void setTrailingWhitespace(CharSequence whitespace) {
        _trailingWhitespace.setWhitespace(whitespace);
    }

    @Override
    public ClosedTag deepCopy() {
        ClosedTag copy = new ClosedTag();
        copy.setName(getName());
        copy.setTrailingWhitespace(getTrailingWhitespace());

        List<AttributeToken> attributes = copy._attributes.getElements();
        attributes.addAll(_attributes.getElements());

        return copy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClosedTag)) return false;
        ClosedTag that = (ClosedTag) o;
        return Objects.equals(_name, that._name) &&
                Objects.equals(_attributes, that._attributes) &&
                Objects.equals(_trailingWhitespace, that._trailingWhitespace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_name, _attributes, _trailingWhitespace);
    }
}
