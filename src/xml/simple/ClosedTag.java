package xml.simple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * '<' Text Blank Attributes Blank '/>'
 */
public class ClosedTag extends SequenceNode {
    private final TextToken _name;
    private final MultiNode<AttributeToken> _attributes;

    public ClosedTag() {
        super(new ArrayList<>());
        _name = new TextToken();
        _attributes = new MultiNode<>(AttributeToken::new);

        _sequence.addAll(Arrays.asList(
            new CharTerminal('<'), _name, _attributes, new WhitespaceToken(), new StringTerminal("/>")
        ));
    }

    public TextToken getName() {
        return _name;
    }

    public List<AttributeToken> getAttributes() {
        return _attributes.getElements();
    }
}
