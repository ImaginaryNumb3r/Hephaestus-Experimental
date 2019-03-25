package parsing.xml;

import parsing.model.CopyNode;
import parsing.model.SequenceNode;
import parsing.model.StringTerminal;
import parsing.model.WhitespaceToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * Creator: Patrick
 * Created: 25.03.2019
 * Grammar: <?xml Attributes >
 */
public class XMLProlog extends SequenceNode implements CopyNode<XMLProlog> {
    private static final String START_TERMINAL = "<?xml";
    private static final String END_TERMINAL = "?>";
    private final AttributesNode _attributes;
    private final WhitespaceToken _trailingWhitespace;

    public XMLProlog() {
        super(new ArrayList<>());

        _attributes = new AttributesNode();
        _trailingWhitespace = new WhitespaceToken();
        _sequence.addAll(Arrays.asList(
                new StringTerminal(START_TERMINAL),
                _attributes,
                _trailingWhitespace,
                new StringTerminal(END_TERMINAL)
        ));
    }

    @Override
    public XMLProlog deepCopy() {
        XMLProlog copy = new XMLProlog();
        AttributesNode attributesCopy = _attributes.deepCopy();
        WhitespaceToken whitespaceCopy = _trailingWhitespace.deepCopy();

        copy._attributes.setData(attributesCopy);
        copy._trailingWhitespace.setData(whitespaceCopy);

        return copy;
    }

    @Override
    public void reset() {
        super.reset();
        _attributes.reset();
        _trailingWhitespace.reset();
    }

    @Override
    public void setData(XMLProlog other) {
        reset();
        super.setData(other);
        _attributes.setData(other._attributes);
        _trailingWhitespace.setData(other._trailingWhitespace);


        if (!equals(other)) {
            throw new IllegalStateException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XMLProlog)) return false;
        if (!super.equals(o)) return false;
        XMLProlog that = (XMLProlog) o;
        return Objects.equals(_attributes, that._attributes) &&
                Objects.equals(_trailingWhitespace, that._trailingWhitespace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), _attributes, _trailingWhitespace);
    }

    @Override
    public String toString() {
        return START_TERMINAL + _attributes.toString() + _trailingWhitespace.toString() + END_TERMINAL;
    }
}
