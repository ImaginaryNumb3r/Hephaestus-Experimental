package parsing.xml;

import parsing.model.AbstractParseNode;
import parsing.model.ParseNode;
import parsing.model.WhitespaceToken;

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
        return null;
    }

    @Override
    public ParseNode deepCopy() {
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
