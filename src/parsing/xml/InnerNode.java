package parsing.xml;

import parsing.model.EitherNode;
import parsing.model.StringToken;
import parsing.model.WhitespaceToken;

/**
 * Creator: Patrick
 * Created: 22.03.2019
 * Grammar: StringToken | (Whitespace XMLNode)
 */
public class InnerNode extends EitherNode<StringToken, XMLNode> {

    public InnerNode() {
        super(new StringToken(), new XMLNode());
    }

    @Override
    protected int parseImpl(String chars, int index) {
        // Parse input until a non-whitespace token is encountered.
        int nextIndex = new WhitespaceToken().parse(chars, index);
        int nextChar = chars.charAt(nextIndex);



        return super.parseImpl(chars, index);
    }
}
