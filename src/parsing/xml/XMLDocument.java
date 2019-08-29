package parsing.xml;

import parsing.model.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Creator: Patrick
 * Created: 25.03.2019
 * Purpose:
 */
public class XMLDocument extends AbstractParseNode implements CopyNode<XMLDocument> {
    private final XMLProlog _prologue;
    private WhitespaceToken _prologueWhitespace;
    private final XMLComments _comments;
    private final XMLTag _root;

    public XMLDocument() {
        _prologue = new XMLProlog();
        _prologueWhitespace = new WhitespaceToken();
        _comments = new XMLComments();
        _root = new XMLTag();
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
    @SuppressWarnings("Duplicates")
    protected ParseResult parseImpl(String chars, int index) {
        // Replace BOM
        chars = chars.replace("\uFEFF", "");

        ParseResult prolog = _prologue.parse(chars, index);
        int nextIndex = prolog.isValid() ? prolog.index() : index;

        ParseResult whitespace = _prologueWhitespace.parse(chars, nextIndex);
        if (whitespace.isInvalid()) return whitespace;
        nextIndex = whitespace.index();

        // There could be comments between prologue and xml body.
        ParseResult comments = _comments.parse(chars, nextIndex);
        nextIndex = comments.isValid() ? comments.index() : nextIndex;

        return _root.parse(chars, nextIndex);
    }

    @Override
    public String toString() {
        return _prologue.toString() + _prologueWhitespace.toString() + _root.toString();
    }

    @Override
    public XMLDocument deepCopy() {
        XMLDocument copy = new XMLDocument();
        XMLTag rootCopy = _root.deepCopy();
        XMLProlog prologCopy = _prologue.deepCopy();

        copy._root.setData(rootCopy);
        copy._prologue.setData(prologCopy);
        copy._prologueWhitespace.setData(_prologueWhitespace);

        return copy;
    }

    public XMLProlog getProlog() {
        return _prologue;
    }

    public XMLTag getRoot() {
        return _root;
    }

    public String getWhitespace() {
        return _prologueWhitespace.toString();
    }

    public void setWhitespace(CharSequence whitespace) {
        _prologueWhitespace.setWhitespace(whitespace);
    }

    @Override
    public void setData(XMLDocument other) {
        XMLTag rootCopy = other._root.deepCopy();
        XMLProlog prologCopy = other._prologue.deepCopy();

        _root.setData(rootCopy);
        _prologue.setData(prologCopy);
        _prologueWhitespace.setData(_prologueWhitespace);
    }

    @Override
    public void reset() {
        _prologue.reset();
        _root.reset();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XMLDocument)) return false;
        XMLDocument that = (XMLDocument) o;
        return Objects.equals(_prologue, that._prologue) &&
                Objects.equals(_root, that._root) &&
                Objects.equals(_prologueWhitespace, that._prologueWhitespace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_prologue, _root, _prologueWhitespace);
    }
}
