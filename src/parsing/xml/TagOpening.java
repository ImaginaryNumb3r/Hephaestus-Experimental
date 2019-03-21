package parsing.xml;

import essentials.contract.NoImplementationException;
import parsing.model.*;

import java.util.Arrays;
import java.util.Collection;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * '<' Text Blank Attributes Blank '>' ( String ) '<' Text Blank '>'
 * // TODO: Assert that the closing brackets are the same as the opening brackets.
 */
public class TagOpening extends SequenceNode implements CopyNode<TagOpening> {

    public TagOpening() {
        super(Arrays.asList(
                new CharTerminal('>'),

                // Value / Inner nodes
                new EitherNode<>(new XMLNode(), new StringToken()),
                // new StringToken(), // Node Value -> Needs to be either a sub-node or an (empty) string.

                // XML Node
                new CharTerminal('<'),
                new TextToken(), // Node name
                new WhitespaceToken(),
                new CharTerminal('>')
        ));
    }

    public TagOpening(Collection<? extends ParseNode> sequence) {
        super(sequence);
    }

    @Override
    public TagOpening deepCopy() {
        throw new NoImplementationException();
    }
}
