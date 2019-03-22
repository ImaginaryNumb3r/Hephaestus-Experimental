package parsing.xml;

import parsing.model.ContentToken;

import java.util.Objects;

/**
 * Creator: Patrick
 * Created: 22.03.2019
 * The text that can be between nodes
 */
public class XMLText extends ContentToken {
    private static final String POSTFIX = "</";

    public XMLText() {
        super("", POSTFIX);
    }

    @Override
    protected int parseImpl(String chars, int index) {
        return super.parseImpl(chars, index) - 1;
    }

    @Override
    public String toString() {
        return _buffer.toString();
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
}
