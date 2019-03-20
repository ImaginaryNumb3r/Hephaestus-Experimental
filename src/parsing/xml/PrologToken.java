package parsing.xml;

import parsing.model.ContentToken;
import parsing.model.CopyNode;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * TODO: Also parse Attributes (similar to ClosedTag)
 */
public class PrologToken extends ContentToken implements CopyNode<PrologToken> {

    public PrologToken() {
        super("<?", "?>");
    }

    public PrologToken deepCopy() {
        PrologToken copy = new PrologToken();
        copy.setContent(_buffer);

        return copy;
    }
}
