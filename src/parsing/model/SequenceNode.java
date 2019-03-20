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

    public SequenceNode(Collection<? extends ParseNode> sequence) {
        _sequence = new ArrayList<>(sequence);
    }

    @Override
    protected int parseImpl(String chars, int index) {

        for (ParseNode element : _sequence) {
            index = element.parse(chars, index);

            if (index == INVALID) {
                return INVALID;
            }
        }

        return index;
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

    @Override
    public @NotNull ListIterator<ParseNode> listIterator(int index) {
        return _sequence.listIterator(index);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SequenceNode)) return false;
        SequenceNode other = (SequenceNode) o;

        Iterator<ParseNode> iter = _sequence.iterator();
        Iterator<ParseNode> otherIter = other._sequence.iterator();

        while (iter.hasNext()) {
            ParseNode node = iter.next();

            // Return false if other iterator has less elements.
            if (!otherIter.hasNext()) return false;
            ParseNode otherNode = otherIter.next();

            boolean equals = node.equals(otherNode);
            if (!equals) return false;
        }

        // Return false if other iterator has excessive elements.
        if (otherIter.hasNext()) return false;
        return true;
        // return Objects.equals(_sequence, other._sequence);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_sequence);
    }
}
