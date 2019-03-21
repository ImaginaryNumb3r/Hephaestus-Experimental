package parsing.xml;

import essentials.contract.NoImplementationException;
import parsing.model.CopyNode;
import parsing.model.SequenceNode;

import java.util.ArrayList;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Purpose:
 */
public class XMLTag extends SequenceNode implements CopyNode<XMLTag> {
    private final TagHeader _head;
    // TODO: Either Close Tag or Opening Tag

    public XMLTag() {
        super(new ArrayList<>());

        _head = new TagHeader();
        // TODO: Initialize sequence
    }

    @Override
    public XMLTag deepCopy() {
        throw new NoImplementationException();
    }
}
