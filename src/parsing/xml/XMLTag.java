package parsing.xml;

import essentials.contract.NoImplementationException;
import parsing.model.*;

import java.util.ArrayList;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Purpose:
 */
public class XMLTag extends SequenceNode implements CopyNode<XMLTag> {
    private final TagHeader _head;
    private final EitherNode<StringTerminal, TagOpening> _tail;

    public XMLTag() {
        super(new ArrayList<>());

        _head = new TagHeader();
        _tail = new EitherNode<>(new StringTerminal("/>"), new TagOpening());

        _sequence.add(_head);
        _sequence.add(_tail);
    }



    @Override
    public XMLTag deepCopy() {
        throw new NoImplementationException();
    }
}
