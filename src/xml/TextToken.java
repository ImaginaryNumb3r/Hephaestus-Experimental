package xml;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Non-empty string token.
 */
public class TextToken extends ConsumerNode {

    public TextToken() {
        super(Character::isAlphabetic);
    }

    public void setText(String text) {
        if (text.isBlank()) {
            throw new IllegalArgumentException("Cannot set value of Text to blank strings.");
        }

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
    public String toString() {
        return super.toString();
    }
}
