package parsing.xml;

import parsing.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Grammar: '<' Name Attributes Whitespace
 */
public class TagHeader extends SequenceNode implements CopyNode<TagHeader> {
    private final TextToken _name;
    private final XMLAttributes _attributes;
    private final WhitespaceToken _whitespace;

    public TagHeader() {
        super(new ArrayList<>());
        _name = new TextToken();
        _attributes = new XMLAttributes();

        _whitespace= new WhitespaceToken();
        _sequence.addAll(Arrays.asList(
                new CharTerminal('<'), _name, _attributes, _whitespace

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

    public String getWhitespace() {
        return _whitespace.toString();
    }

    public void setWhitespace(CharSequence whitespace) {
        _whitespace.setWhitespace(whitespace);
    }

    @Override
    public TagHeader deepCopy() {
        TagHeader copy = new TagHeader();
        copy.setData(this);

        return copy;
    }

    @Override
    public void reset() {
        // Clear sequence of elements.
        super.reset();

        // Clear elements themselves within the sequence.
        _name.reset();
        _attributes.getElements().clear();
        _whitespace.setWhitespace("");
    }

    @Override
    public void setData(TagHeader other) {
        reset();

        setName(other.getName());
        _whitespace.setWhitespace(other._whitespace.toString());

        var attributes = getAttributes();
        var attributeCopies = other._attributes.getElements().stream()
                .map(AttributeToken::deepCopy)
                .collect(Collectors.toList());

        attributes.addAll(attributeCopies);

        var sequenceCopy = other._sequence.stream()
                .map(ParseNode::deepCopy)
                .collect(Collectors.toList());
        _sequence.addAll(sequenceCopy);

        if (!equals(other)) {
            throw new IllegalStateException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TagHeader)) return false;
        if (!super.equals(o)) return false;
        TagHeader that = (TagHeader) o;
        return Objects.equals(_name, that._name) &&
                Objects.equals(_attributes, that._attributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), _name, _attributes);
    }
}
