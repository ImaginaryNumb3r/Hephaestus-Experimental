package parsing.xml;

import lib.Nulls;
import parsing.model.CopyNode;
import parsing.model.MultiNode;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Creator: Patrick
 * Created: 22.03.2019
 * Grammar: ( XMLNode )* | XMLText
 * TODO: Problem is that a comment can also be part within the text tag
 */
public class InnerNodes extends MultiNode<XMLNode> implements CopyNode<InnerNodes> {
    private final XMLText _text;
    private Status _status;

    public InnerNodes() {
        super(XMLNode::new);
        _text = new XMLText();
        _status = Status.NONE;
    }

    @Override
    protected int parseImpl(String chars, final int index) {
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

    @Override
    public InnerNodes deepCopy() {
        // MultiNode<XMLNode> copy = super.deepCopy();
        InnerNodes copy = new InnerNodes();
        copy.setData(this);

        if (!copy.equals(this)) {
            throw new IllegalStateException();
        }

        return copy;
    }

    @Override
    public void setData(InnerNodes other) {
        reset();
        _text.setData(other._text);

        var elementsCopy = other._elements.stream()
                .map(XMLNode::deepCopy)
                .collect(Collectors.toList());

        _elements.addAll(elementsCopy);
        _status = other._status;
    }

    public boolean isText() {
        return _status == Status.TEXT;
    }

    public boolean hasInnerNodes() {
        return _status == Status.NODE;
    }

    public Optional<String> getData() {
        return Nulls.box(isText(), _text.getContent());
    }

    public Optional<List<XMLNode>> innerNodes() {
        return Nulls.box(hasInnerNodes(), _elements);
    }

    @Override
    public void reset() {
        _text.reset();
        _elements.clear();
    }

    @Override
    public String toString() {
        return _status == Status.TEXT ? _text.toString()
                : _status == Status.NODE ? super.toString()
                : "";
    }

    private enum Status {
        TEXT, NODE, NONE
    }
}
