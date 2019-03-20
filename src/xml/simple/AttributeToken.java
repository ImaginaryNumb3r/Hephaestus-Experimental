package xml.simple;

import java.util.Arrays;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Space Text Whitespace '=' Whitespace "Text"
 */
public class AttributeToken extends SequenceNode {

    public AttributeToken() {
        super(Arrays.asList(
                new SpaceToken(),
                new TextToken(),
                new WhitespaceToken(),
                new CharTerminal('='),
                new WhitespaceToken(),
                new ContentToken("\"")
        ));
    }
}
