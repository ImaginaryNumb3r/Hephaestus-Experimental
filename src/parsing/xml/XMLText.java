package parsing.xml;

import parsing.model.ContentToken;
import parsing.model.CopyNode;

import java.util.Objects;

/**
 * Creator: Patrick
 * Created: 22.03.2019
 * The text that can be between nodes
 */
public class XMLText extends ContentToken implements CopyNode<XMLText> {
    private static final String POSTFIX = "</";

    public XMLText() {
        super("", POSTFIX);
    }

    @Override
    protected int parseImpl(String chars, int index) {
        index = super.parseImpl(chars, index);
        return index == INVALID ? INVALID : index - 1;
    }

    @Override
    public String toString() {
        // Do not print postfix because it only serves as a termination marker.
        return _buffer.toString();
    }

    public String rawString() {
        return toString() + POSTFIX;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XMLText)) return false;
        XMLText that = (XMLText) o;
        return Objects.equals(_buffer.toString(), that._buffer.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(_buffer.toString());
    }

    @Override
    public XMLText deepCopy() {
        XMLText copy = new XMLText();
        setContent(_buffer);

        return copy;
    }

    @Override
    public void setData(XMLText other) {
        super.setData(other);
    }

    @Override
    public void reset() {
        super.reset();
    }
}
