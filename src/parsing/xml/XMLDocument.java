package parsing.xml;

import parsing.model.AbstractParseNode;
import parsing.model.CopyNode;
import parsing.model.ParseResult;
import parsing.model.WhitespaceToken;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Creator: Patrick
 * Created: 25.03.2019
 * Purpose: Prolog? XMLTag Whitespace
 */
public class XMLDocument extends AbstractParseNode implements CopyNode<XMLDocument> {
    private final XMLProlog _prolog;
    private WhitespaceToken _prologWhitespace;
    private final XMLTag _root;
    private WhitespaceToken _trailingWhitespace;

    public XMLDocument() {
        _prolog = new XMLProlog();
        _prologWhitespace = new WhitespaceToken();
        _root = new XMLTag();
        _trailingWhitespace = new WhitespaceToken();
    }

    public static XMLDocument ofFile(Path path) throws IOException {
        String rawDocument = Files.readString(path);

        return XMLDocument.of(rawDocument);
    }

    public static XMLDocument ofFile(String filePath) throws IOException {
        return ofFile(Path.of(filePath));
    }

    public static XMLDocument ofFile(File file) throws IOException {
        return ofFile(file.getAbsolutePath());
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
    protected ParseResult parseImpl(String chars, final int index) {
        ParseResult prolog = _prolog.parse(chars, index);
        int nextIndex = prolog.isValid() ? prolog.index() : index;

        ParseResult whitespace = _prologWhitespace.parse(chars, nextIndex);
        if (whitespace.isInvalid()) return whitespace;

        nextIndex = whitespace.index();
        ParseResult body = _root.parse(chars, nextIndex);

        if (body.isInvalid()) return body;
        nextIndex = body.index();

        return _trailingWhitespace.parse(chars, nextIndex);
    }

    @Override
    public String toString() {
        return _prolog.toString() + _prologWhitespace + _root.toString() + _trailingWhitespace;
    }

    @Override
    public XMLDocument deepCopy() {
        XMLDocument copy = new XMLDocument();
        XMLTag rootCopy = _root.deepCopy();
        XMLProlog prologCopy = _prolog.deepCopy();

        copy._root.setData(rootCopy);
        copy._prolog.setData(prologCopy);
        copy._prologWhitespace.setData(_prologWhitespace);
        copy._trailingWhitespace.setData(_trailingWhitespace);

        return copy;
    }

    public XMLProlog getProlog() {
        return _prolog;
    }

    public XMLTag getRoot() {
        return _root;
    }

    public String getPrologWhitespace() {
        return _prologWhitespace.toString();
    }

    public void setPrologWhitespace(CharSequence prologWhitespace) {
        _prologWhitespace.setWhitespace(prologWhitespace);
    }

    public String getTrailingWhitespace() {
        return _trailingWhitespace.toString();
    }

    public void setTrailingWhitespace(CharSequence trailingWhitespace) {
        _trailingWhitespace.setWhitespace(trailingWhitespace);
    }

    @Override
    public void setData(XMLDocument other) {
        XMLTag rootCopy = other._root.deepCopy();
        XMLProlog prologCopy = other._prolog.deepCopy();

        _root.setData(rootCopy);
        _prolog.setData(prologCopy);
        _prologWhitespace.setData(other._prologWhitespace);
        _trailingWhitespace.setData(other._trailingWhitespace);
    }

    @Override
    public void reset() {
        _prologWhitespace.reset();
        _prolog.reset();
        _root.reset();
        _trailingWhitespace.reset();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XMLDocument)) return false;
        XMLDocument that = (XMLDocument) o;
        return Objects.equals(_prolog, that._prolog) &&
                Objects.equals(_root, that._root) &&
                Objects.equals(_prologWhitespace, that._prologWhitespace) &&
                Objects.equals(_trailingWhitespace, that._trailingWhitespace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_prolog, _prologWhitespace, _root, _trailingWhitespace);
    }
}
