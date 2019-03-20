package xml;

import java.util.Arrays;
import java.util.List;

/**
 * @author Patrick Plieschnegger
 *
 * Attribute: Sequence
 * Text Blank '=' Blank Text
 */
public class AttributeToken extends SequenceNode {

    public AttributeToken() {
        super(getAttributeTokens());
    }

    private static List<AbstractToken> getAttributeTokens() {
        return Arrays.asList(
            new TextToken(),
            new WhitespaceToken(),
            new CharToken('='),
            new WhitespaceToken(),
            new ContentToken("\">")
        );
    }
}
