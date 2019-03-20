package xml;

import essentials.contract.NoImplementationException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * '<' Text Blank Attributes Blank '>' ( String ) '<' Text Blank '>'
 */
public class OpenedTag extends SequenceNode implements CopyNode<OpenedTag> {

    public OpenedTag() {
        super(Arrays.asList(
                new CharTerminal('<'),
                new TextToken()
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
