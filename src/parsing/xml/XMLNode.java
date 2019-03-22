package parsing.xml;

import parsing.model.ContentToken;
import parsing.model.CopyNode;
import parsing.model.EitherNode;
import parsing.model.WhitespaceToken;

import java.util.Optional;

/**
 * Creator: Patrick
 * Created: 21.03.2019
 * Grammar: Whitespace [ CommentTag | XMLTag ]
 *          Whitespace [ CommentTag | <Name Attributes Whitespace ( ( > InnerNodes </Name> ) | /> ) ]
 * May be a getTag or a comment.
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
        return super.hasSecond();
    }

    public boolean isComment() {
        return super.hasSecond();
    }

    @Override
    protected int parseImpl(String chars, int index) {
        index = _leadingWhitespace.parse(chars, index);

        if (index == INVALID) {
            throw new IllegalStateException("Whitespace tokens must not return INVALID");
        }

        return super.parseImpl(chars, index);
    }

    @Override
    public XMLNode deepCopy() {
        XMLNode copy = new XMLNode();
        copy.setData(this); /*
        Nulls.ifPresent(_mandatory, copy._mandatory::setData);
        Nulls.ifPresent(_optional, copy._optional::setData);
        copy._status = _status; */

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
    }

    @Override
    public String toString() {
        return _leadingWhitespace.toString() + super.toString();
    }
}
