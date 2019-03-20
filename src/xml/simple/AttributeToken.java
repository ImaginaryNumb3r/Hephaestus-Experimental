package xml.simple;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Space Text Whitespace '=' Whitespace "Text"
 */
public class AttributeToken extends SequenceNode {
    private final TextToken _name;
    private final ContentToken _value;

    public AttributeToken() {
        super(new ArrayList<>());

        _name = new TextToken();
        _value = new ContentToken("\"");

        _sequence.addAll(
            Arrays.asList(
                new SpaceToken(), _name, new WhitespaceToken(),
                new CharTerminal('='), new WhitespaceToken(), _value
            )
        );
    }

    public TextToken getName() {
        return _name;
    }

    public ContentToken getValue() {
        return _value;
    }
}
