package parsing.model;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Non-empty string token that is used for identifying names of tags.
 * May not contain whitespace.
 *
 * Minor design flaw: Can be set to empty.
 */
public final class TextToken extends ConsumerNode implements CopyNode<TextToken> {
    private static final Set<Character> ALLOWED_CHARS = Set.of('_', '.', ':', '-');

    public TextToken() { // TODO: Change to "not whitespace" -> character '!' could be making problems
        super(ch -> Character.isAlphabetic(ch) || Character.isDigit(ch) || ALLOWED_CHARS.contains(ch));
    }

    public void setText(CharSequence text) {
        _buffer.setLength(0);
        _buffer.append(text);
    }

    @Override
    public TextToken deepCopy() {
        TextToken copy = new TextToken();
        copy.setText(_buffer.toString());

        return copy;
    }

    @Override
    public void reset() {
        super.reset();
    }

    @Override
    public void setData(TextToken other) {
        super.setData(other);
    }

    /**
     * @return the parsed text as string.
     */
    @NotNull
    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
