package xml.simple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
}
