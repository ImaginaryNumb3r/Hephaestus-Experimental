package parsing.xml;

import parsing.model.ContentToken;
import parsing.model.CopyNode;

/**
 * Is of limited use alone since a comment may appear arbitrary times in a row.
 */
public class XMLComment extends ContentToken implements CopyNode<XMLComment> {
    public static final String COMMENT_START = "<!--";
    public static final String COMMENT_END = "-->";

    public XMLComment() {
        super(COMMENT_START, COMMENT_END);
    }

    @Override
    public void setData(XMLComment other) {
        super.setData(other);
    }

    @Override
    public void reset() {
        super.reset();
    }

    @Override
    public XMLComment deepCopy() {
        XMLComment copy = new XMLComment();
        copy.setData(this);

        return copy;
    }
}
