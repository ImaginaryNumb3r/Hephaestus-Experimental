package parsing.xml;

import parsing.model.AbstractParseNode;
import parsing.model.ParseNode;
import parsing.model.WhitespaceToken;

import java.util.Objects;

/**
 * Creator: Patrick
 * Created: 25.03.2019
 * Purpose:
 */
public class XMLDocument extends AbstractParseNode {
    private final XMLProlog _prolog;
    private final XMLTag _root;
    private WhitespaceToken _whitespace;

    public XMLDocument() {
        _prolog = new XMLProlog();
        _root = new XMLTag();
        _whitespace = new WhitespaceToken();
    }

    @Override
    protected int parseImpl(String chars, int index) {
        int nextIndex = _prolog.parse(chars, index);
        index = nextIndex != INVALID ? nextIndex : index;

        index = _whitespace.parse(chars, index);
        nextIndex = _root.parse(chars, index);

        return nextIndex;
    }

    @Override
    public String toString() {
        return _prolog.toString() + _whitespace.toString() + _root.toString();
    }

    @Override
    public XMLDocument deepCopy() {
        XMLDocument copy = new XMLDocument();
        XMLTag rootCopy = _root.deepCopy();
        XMLProlog prologCopy = _prolog.deepCopy();

        copy._root.setData(rootCopy);
        copy._prolog.setData(prologCopy);
        copy._whitespace.setData(_whitespace);

        return copy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XMLDocument)) return false;
        XMLDocument that = (XMLDocument) o;
        return Objects.equals(_prolog, that._prolog) &&
                Objects.equals(_root, that._root) &&
                Objects.equals(_whitespace, that._whitespace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_prolog, _root, _whitespace);
    }
}
