package parsing.model;

import essentials.util.Strings;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * This is a node which represents multiple subsequent nodes of the same kind.
 * This node never returns invalid.
 * Grammar: T*
 */
public class MultiNode<T extends CopyNode<T>> extends AbstractParseNode implements List<T> {
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
    protected ParseResult parseImpl(String chars, final int index) {
        T token = _tokenConstructor.get();

        ParseResult result = ParseResult.at(index);
        int nextIndex = index;
        boolean isParsing = true;
        while (isParsing) {
            var next = token.parse(chars, nextIndex);
            isParsing = next.isValid();

            if (isParsing) {
                nextIndex = next.index();
                result = next;
                _elements.add(token);
                token = _tokenConstructor.get();
            } else {
                result.innerErrors().addAll(next.innerErrors());
            }
        }

        if (result.isInvalid()) throw new IllegalStateException("MultiNode must not return invalid");
        return result;
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

    @NotNull
    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return _elements.subList(fromIndex, toIndex);
    }

    public Stream<T> stream() {
        return _elements.stream();
    }

    @Override
    public int size() {
        return _elements.size();
    }

    @Override
    public boolean isEmpty() {
        return _elements.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return _elements.contains(o);
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return _elements.iterator();
    }

    @NotNull
    @Override
    public Object[] toArray() {
        return _elements.toArray();
    }

    @NotNull
    @Override
    public <T1> T1[] toArray(@NotNull T1[] a) {
        return _elements.toArray(a);
    }

    @Override
    public boolean add(T t) {
        return _elements.add(t);
    }

    @Override
    public boolean remove(Object o) {
        return _elements.remove(o);
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        return _elements.containsAll(c);
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends T> c) {
        return _elements.addAll(c);
    }

    @Override
    public boolean addAll(int index, @NotNull Collection<? extends T> c) {
        return _elements.addAll(index, c);
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        return _elements.removeAll(c);
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        return _elements.retainAll(c);
    }

    @Override
    public void clear() {
        _elements.clear();
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
        return !otherIter.hasNext();
    }

    @Override
    public int hashCode() {
        return Objects.hash(_elements);
    }

    @Override
    public T get(int index) {
        return _elements.get(index);
    }

    @Override
    public T set(int index, T element) {
        return _elements.set(index, element);
    }

    @Override
    public void add(int index, T element) {
        _elements.add(index, element);
    }

    @Override
    public T remove(int index) {
        return _elements.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return _elements.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return _elements.lastIndexOf(o);
    }

    @NotNull
    @Override
    public ListIterator<T> listIterator() {
        return _elements.listIterator();
    }
}
