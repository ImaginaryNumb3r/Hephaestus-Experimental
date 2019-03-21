package parsing.xml;

import essentials.contract.NoImplementationException;
import parsing.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Purpose:
 */
public class XMLTag extends SequenceNode implements CopyNode<XMLTag> {
    private final TagHeader _head;
    private final XMLTail _tail;

    public XMLTag() {
        super(new ArrayList<>());

        _head = new TagHeader();
        _tail = new XMLTail();

        _sequence.add(_head);
        _sequence.add(_tail);
        _sequence.add((ConstraintNode) (chars, index) -> {
            throw new NoImplementationException("Must confirm that head name and tail name is equal.");
        });
    }

    public String getName() {
        return _head.getName();
    }

    public void setName(String name) {
        _head.setName(name);
    }

    public List<AttributeToken> getAttributes() {
        return _head.getAttributes();
    }

    @Override
    public XMLTag deepCopy() {
        throw new NoImplementationException();
    }

    @Override
    public void setData(XMLTag other) {
        throw new NoImplementationException();
    }

    @Override
    public void reset() {
        _head.reset();
        _tail.reset();
    }
}
