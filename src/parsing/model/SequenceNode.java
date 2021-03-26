package parsing.model;

import essentials.collections.IterableList;
import essentials.util.Strings;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * This node represents a fixed sequence of nodes of different types.
 * The purpose of this node is to be used for code re-use via inheritance.
 */
public class SequenceNode extends AbstractParseNode implements IterableList<ParseNode> {
    protected final List<ParseNode> _sequence;

    public SequenceNode(ParseNode... nodes) {
        this(Arrays.asList(nodes));
    }

    public SequenceNode(Collection<? extends ParseNode> sequence) {
        _sequence = new ArrayList<>(sequence);
    }

    public SequenceNode() {
        _sequence = new ArrayList<>();
    }

    @Override
    protected ParseResult parseImpl(String chars, final int index) {

        ParseResult result = ParseResult.at(index);
        int nextIndex = index;
        for (ParseNode element : _sequence) {
            result = element.parse(chars, nextIndex);

            if (result.isInvalid()) {
                return result;
            }
            nextIndex = result.index();
        }

        return result;
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
        var sequenceCopy = other._sequence.stream()
                .map(ParseNode::deepCopy)
                .collect(Collectors.toList());

        _sequence.addAll(sequenceCopy);
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

    @Override
    public @NotNull ListIterator<ParseNode> listIterator() {
        return _sequence.listIterator(0);
    }
}
