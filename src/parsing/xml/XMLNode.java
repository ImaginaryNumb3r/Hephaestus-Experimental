package parsing.xml;

import essentials.annotations.Package;
import parsing.model.*;

import java.util.Objects;
import java.util.Optional;

/**
 * Creator: Patrick
 * Created: 21.03.2019
 * Grammar: Whitespace [ CommentTag | XMLTag ]
 *          Whitespace [ CommentTag | <Name Attributes Whitespace ( ( > InnerNodes </Name> ) | /> ) ]
 * May be a Tag or a comment.
 */
public class XMLNode extends EitherNode<XMLTag, CommentToken> implements CopyNode<XMLNode> {
    private final WhitespaceToken _leadingWhitespace;

    public XMLNode() {
        // Comment Token as fallback.
        super(new XMLTag(), new CommentToken());
        _leadingWhitespace = new WhitespaceToken();
    }

    public Optional<XMLTag> getTag() {
        return first();
    }

    @Package XMLTag toTag() {
        return first().get();
    }

    public Optional<CommentToken> getComment() {
        return second();
    }

    public Optional<String> getCommentContent() {
        return second().map(ContentToken::getContent);
    }

    public String getLeadingWhitespace() {
        return _leadingWhitespace.toString();
    }

    public void setLeadingWhitespace(CharSequence whitespace) {
        _leadingWhitespace.setWhitespace(whitespace);
    }

    @Override
    public Optional<XMLTag> first() {
        return super.first();
    }

    public boolean isTag() {
        return super.hasFirst();
    }

    public boolean isComment() {
        return super.hasSecond();
    }

    @Override
    protected ParseResult parseImpl(String chars, int index) {
        ParseResult result = _leadingWhitespace.parse(chars, index);
        if (result.isInvalid()) throw new IllegalStateException("Whitespace tokens must not return INVALID");

        return super.parseImpl(chars, result.index());
    }

    @Override
    public XMLNode deepCopy() {
        XMLNode copy = new XMLNode();
        copy.setData(this);

        if (!copy.equals(this)) {
            throw new IllegalStateException();
        }

        return copy;
    }

    @Override
    public void reset() {
        super.reset();
    }

    @Override
    public void setData(XMLNode other) {
        reset();
        super.setData(other);
        _leadingWhitespace.setWhitespace(other._leadingWhitespace);
    }

    @Override
    public String toString() {
        return _leadingWhitespace.toString() + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XMLNode)) return false;
        if (!super.equals(o)) return false;
        XMLNode xmlNode = (XMLNode) o;
        return Objects.equals(_leadingWhitespace, xmlNode._leadingWhitespace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), _leadingWhitespace);
    }
}
