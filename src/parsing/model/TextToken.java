package parsing.model;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Non-empty string token.
 */
public class TextToken extends ConsumerNode implements CopyNode<TextToken> {

    public TextToken() { // TODO: Change to "not whitespace" -> character '!' could be making problems
        super(Character::isAlphabetic);
    }

    public void setText(CharSequence text) {
        /* if (text.isBlank()) {
            throw new IllegalArgumentException("Cannot set value of Text to blank strings.");
        }*/

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
    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
