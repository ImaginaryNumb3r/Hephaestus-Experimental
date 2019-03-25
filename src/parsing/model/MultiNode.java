package parsing.model;

import lib.ListIterable;
import lib.Strings;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * This node never returns invalid.
 */
public class MultiNode<T extends CopyNode<T>> extends AbstractParseNode implements ListIterable<T> {
    protected final List<T> _elements;
    private final Supplier<T> _tokenConstructor;

    public MultiNode(Supplier<T> tokenConstructor) {
        _elements = new ArrayList<>();
        _tokenConstructor = tokenConstructor;
    }

    /**
     * @return Never returns INVALID. If nothing could be parsed, the return value is the same as the index parameter.
     */
    @Override
    protected int parseImpl(String chars, int index) {
        T token = _tokenConstructor.get();

        if (index < 1900 && index > 1741) {
            System.out.println();
        }

        int nextIndex;

        // TODO: test multi node
        // Parse whole tokens until the one where it fails.
        while ((nextIndex = token.parse(chars, index)) != INVALID) {
            _elements.add(token);
            index = nextIndex;

            token = _tokenConstructor.get();
        }

        return index;
    }

    protected void reset() {
        _elements.clear();
    }

    public List<T> getElements() {
        return _elements;
    }

    @Override
    public String toString() {
        return Strings.concat(_elements);
    }

    @Override
    public MultiNode<T> deepCopy() {
        List<T> elements = _elements.stream()
            .map(CopyNode::deepCopy)
            .collect(Collectors.toList());

        MultiNode<T> copy = new MultiNode<>(_tokenConstructor);
        copy._elements.addAll(elements);

        return copy;
    }

    @NotNull
    @Override
    public ListIterator<T> listIterator(int index) {
        return _elements.listIterator(index);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MultiNode)) return false;
        MultiNode<?> multiNode = (MultiNode<?>) o;

        Iterator<? extends ParseNode> iter = iterator();
        Iterator<? extends ParseNode> otherIter = multiNode.iterator();

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
    }

    @Override
    public int hashCode() {
        return Objects.hash(_elements);
    }
}
