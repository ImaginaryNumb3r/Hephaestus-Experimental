package xml;

import essentials.contract.NoImplementationException;

import java.util.Arrays;
import java.util.Collection;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * '<' Text Blank Attributes Blank '>' ( String ) '<' Text Blank '>'
 * // TODO: Assert that the closing brackets are the same as the opening brackets.
 */
public class OpenedTag extends SequenceNode implements CopyNode<OpenedTag> {

    public OpenedTag() {
        super(Arrays.asList(
                new CharTerminal('<'),
                new TextToken(), // Node name
                new SpaceToken(),
                new AttributesNode(), // Attributes
                new WhitespaceToken(),
                new CharTerminal('>'),
                new StringToken(), // Node Value -> Needs to be either a sub-node or an (empty) string.
                new CharTerminal('<'),
                new TextToken(), // Node name
                new WhitespaceToken(),
                new CharTerminal('>')
        ));
    }


    public OpenedTag(Collection<? extends ParseNode> sequence) {
        super(sequence);
    }

    @Override
    public OpenedTag deepCopy() {
        throw new NoImplementationException();
    }
}
