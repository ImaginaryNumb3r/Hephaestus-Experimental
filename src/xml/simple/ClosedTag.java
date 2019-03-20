package xml.simple;

import java.util.Arrays;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * '<' Text Blank Attributes Blank '/>'
 */
public class ClosedTag extends SequenceNode {

    public ClosedTag() {
        super(Arrays.asList(
            // Tag + name
            new CharTerminal('<'),
            new TextToken(),
            // Attributes

            new MultiNode<>(AttributeToken::new),
            // Tag Close
            new WhitespaceToken(),
            new StringTerminal("/>")
        ));
    }
}
