package parsing.model;

import lib.ListIterable;
import lib.Strings;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Purpose:
 */
public class SequenceNode extends AbstractParseNode implements ListIterable<ParseNode> {
    protected final List<ParseNode> _sequence;

    public SequenceNode(ParseNode... nodes) {
        this(Arrays.asList(nodes));
    }

    public SequenceNode(Collection<? extends ParseNode> sequence) {
        _sequence = new ArrayList<>(sequence);
    }

    @Override
    protected int parseImpl(String chars, final int index) {

        int nextIndex = index;
        for (ParseNode element : _sequence) {
            nextIndex = element.parse(chars, nextIndex);

            if (nextIndex == INVALID) {
                return INVALID;
            }
        }

        return nextIndex;
    }

    @Override
    public String toString() {
        return Strings.concat(_sequence);
    }

    @Override
    public SequenceNode deepCopy() {
        List<ParseNode> copy = _sequence.stream()
            .map(ParseNode::deepCopy)
            .collect(Collectors.toList());

        return new SequenceNode(copy);
    }

    protected void reset() {
        _sequence.clear();
    }

    protected void setData(SequenceNode other) {
        reset();
        var sequence = other._sequence.stream()
                .map(ParseNode::deepCopy)
                .collect(Collectors.toList());

        _sequence.addAll(sequence);
    }

    @Override
    public @NotNull ListIterator<ParseNode> listIterator(int index) {
        return _sequence.listIterator(index);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SequenceNode)) return false;
        SequenceNode that = (SequenceNode) o;
        return Objects.equals(_sequence, that._sequence);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_sequence);
    }
}
