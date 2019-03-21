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
    private final XMLBody _tail;

    public XMLTag() {
        super(new ArrayList<>());

        _head = new TagHeader();
        _tail = new XMLBody();

        _sequence.add(_head);
        _sequence.add(_tail);
        _sequence.add((ConstraintNode) (chars, index) -> {
            _tail.node().map(node -> {
                node.
            });

            return index;
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
}
