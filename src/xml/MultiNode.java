package xml;

import lib.ListIterable;
import lib.Strings;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * This node never returns invalid.
 */
public class MultiNode<T extends CopyNode<T>> extends AbstractParseNode implements ListIterable<T> {
    private final List<T> _elements;
    private final Supplier<T> _tokenConstructor;

    protected MultiNode(Supplier<T> tokenConstructor) {
        _elements = new ArrayList<>();
        _tokenConstructor = tokenConstructor;
    }

    @Override
    protected int parseImpl(String chars, int index) {
        T token = _tokenConstructor.get();

        int nextIndex;

        // Parse whole tokens until the one where it fails.
        while ((nextIndex = token.parse(chars, index)) != INVALID) {
            _elements.add(token);
            index = nextIndex;

            token = _tokenConstructor.get();
        }

        return index;
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
        return Objects.equals(_elements, multiNode._elements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_elements);
    }
}
