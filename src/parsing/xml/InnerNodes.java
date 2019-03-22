package parsing.xml;

import parsing.model.MultiNode;

/**
 * Creator: Patrick
 * Created: 22.03.2019
 * Grammar: XMLText | ( XMLNode )*
 */
public class InnerNodes extends MultiNode<XMLNode> {
    private final XMLText _text;
    private Status _status;

    public InnerNodes() {
        super(XMLNode::new);
        _text = new XMLText();
        _status = Status.NONE;
    }

    @Override
    protected int parseImpl(String chars, int index) {
        int nextIndex = super.parseImpl(chars, index);

        // If the next index is the same as the input index, we know nothing could be parsed.
        if (nextIndex != index) {
            _status = Status.NODE;
        }
        // Fallback to parsing the node value as a string.
        else {
            nextIndex = _text.parse(chars, index);

            if (nextIndex != INVALID) {
                _status = Status.TEXT;
            }
        }

        return nextIndex;
    }

    private enum Status {
        TEXT, NODE, NONE
    }
}
