package xml.simple;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Purpose:
 */
public class TextToken extends ConsumerNode {

    public TextToken() {
        super(codePoint -> Character.isAlphabetic(codePoint));
    }
}
