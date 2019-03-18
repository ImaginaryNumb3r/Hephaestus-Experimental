package xml;

import java.util.Arrays;
import java.util.List;

/**
 * @author Patrick Plieschnegger
 *
 * ClosedTag: Sequence
 * '<' Text Blank Attributes Blank '/>'
 */
public class ClosedTag extends SequenceNode {

    public ClosedTag() {
        super(getSequence());
    }

    private static List<AbstractToken> getSequence() {
        return Arrays.asList(
            new CharToken('<'),
            new OptionalNode(new WhitespaceToken(), new TextToken()),
            new MultiNode(AttributeToken::new),
            new StringToken("/>")
        );
    }
}
