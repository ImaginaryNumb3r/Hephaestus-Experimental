package parsing.xml;

import essentials.contract.NoImplementationException;
import parsing.model.CopyNode;
import parsing.model.EitherNode;
import parsing.model.StringTerminal;

import java.util.Optional;

/**
 * Creator: Patrick
 * Created: 21.03.2019
 * Purpose:
 */
public class XMLBody extends EitherNode<XMLNode, StringTerminal> implements CopyNode<XMLBody> {

    public XMLBody() {
        super(new XMLNode(), new StringTerminal("/>"));
    }

    public boolean isNode() {
        return super.hasFirst();
    }

    public Optional<XMLNode> node() {
        return super.first();
    }

    public boolean isString() {
        return super.hasSecond();
    }

    public Optional<StringTerminal> string() {
        return super.second();
    }

    @Override
    public XMLBody deepCopy() {
        throw new NoImplementationException();
    }

    @Override
    public void reset() {
        super.reset();
    }

    @Override
    public void setData(XMLBody other) {
        throw new NoImplementationException();
    }
}