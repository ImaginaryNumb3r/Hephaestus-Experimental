package parsing.xml;

import parsing.xml.model.CharTerminal;
import parsing.xml.model.CopyNode;
import parsing.xml.model.SequenceNode;
import parsing.xml.model.TextToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Purpose:
 */
public class TagHeader extends SequenceNode implements CopyNode<TagHeader> {
    private final TextToken _name;
    private final AttributesNode _attributes;

    public TagHeader() {
        super(new ArrayList<>());
        _name = new TextToken();
        _attributes = new AttributesNode();

        _sequence.addAll(Arrays.asList(
                new CharTerminal('<'), _name, _attributes
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

    @Override
    public TagHeader deepCopy() {
        TagHeader copy = new TagHeader();
        copy.setName(getName());

        var attributes = copy.getAttributes();
        var attributeCopies = _attributes.getElements().stream()
                .map(AttributeToken::deepCopy)
                .collect(Collectors.toList());

        attributes.addAll(attributeCopies);

        return copy;
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
