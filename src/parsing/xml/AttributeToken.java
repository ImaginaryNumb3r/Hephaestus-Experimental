package parsing.xml;

import parsing.xml.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Space Text Whitespace '=' Whitespace "Text"
 */
public class AttributeToken extends SequenceNode implements CopyNode<AttributeToken> {
    private final TextToken _name;
    private final ContentToken _value;

    public AttributeToken() {
        super(new ArrayList<>());

        _name = new TextToken();
        _value = new ContentToken("\"");

        _sequence.addAll(
            Arrays.asList(
                new SpaceToken(), _name, new WhitespaceToken(),
                new CharTerminal('='), new WhitespaceToken(), _value
            )
        );
    }

    public String getName() {
        return _name.toString();
    }

    public String getValue() {
        return _value.getContent();
    }

    public void setName(String name) {
        _name.setText(name);
    }

    public void setValue(String value) {
        _value.setContent(value);
    }

    @Override
    public AttributeToken deepCopy() {
        var sequence = _sequence.stream()
            .map(ParseNode::deepCopy)
            .collect(Collectors.toList());

        AttributeToken copy = new AttributeToken();
        copy._sequence.clear();
        copy._sequence.addAll(sequence);
        copy.setName(getName());
        copy.setValue(getValue());

        return copy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AttributeToken)) return false;
        AttributeToken that = (AttributeToken) o;
        return  Objects.equals(_name, that._name)
                && Objects.equals(_value, that._value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_name, _value);
    }
}
