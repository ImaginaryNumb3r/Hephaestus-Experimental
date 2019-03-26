package parsing.xml;

import parsing.model.AbstractParseNode;
import parsing.model.CopyNode;
import parsing.model.ParseResult;
import parsing.model.WhitespaceToken;

import java.util.Objects;

/**
 * Creator: Patrick
 * Created: 25.03.2019
 * Purpose:
 */
public class XMLDocument extends AbstractParseNode implements CopyNode<XMLDocument> {
    private final XMLProlog _prolog;
    private final XMLTag _root;
    private WhitespaceToken _whitespace;

    public XMLDocument() {
        _prolog = new XMLProlog();
        _root = new XMLTag();
        _whitespace = new WhitespaceToken();
    }

    public static XMLDocument of(String rawXML) {
        XMLDocument document = new XMLDocument();
        var result = document.parse(rawXML, 0);

        return result.isValid() ? document : null;
    }

    public void visit(XMLVisitor visitor) {
        visit(_root, visitor);
    }

    public void visit(XMLTag root, XMLVisitor visitor) {
        visitor.visitTag(root);

        for (AttributeToken attribute : root.attributes()) {
            visitor.visitAttribute(attribute, root);
        }

        root.children().forEach(child -> visit(child, visitor));
    }

    @Override
    protected ParseResult parseImpl(String chars, int index) {
        ParseResult prolog = _prolog.parse(chars, index);
        index = prolog.isValid() ? prolog.cursorPosition() : index;

        ParseResult whitespace = _whitespace.parse(chars, index);
        if (whitespace.isInvalid()) return whitespace;

        index = whitespace.cursorPosition();
        return _root.parse(chars, index);
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

    public XMLProlog getProlog() {
        return _prolog;
    }

    public XMLTag getRoot() {
        return _root;
    }

    public String getWhitespace() {
        return _whitespace.toString();
    }

    public void setWhitespace(CharSequence whitespace) {
        _whitespace.setWhitespace(whitespace);
    }

    @Override
    public void setData(XMLDocument other) {
        XMLTag rootCopy = other._root.deepCopy();
        XMLProlog prologCopy = other._prolog.deepCopy();

        _root.setData(rootCopy);
        _prolog.setData(prologCopy);
        _whitespace.setData(_whitespace);
    }

    @Override
    public void reset() {
        _prolog.reset();
        _root.reset();
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
