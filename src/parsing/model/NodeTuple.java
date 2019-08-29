package parsing.model;

/**
 * A node tuple is a more tightly coupled node sequence
 */
public class NodeTuple<First extends CopyNode<First>, Second extends CopyNode<Second>>
        extends AbstractParseNode implements CopyNode<NodeTuple<First, Second>> {
    private final First _first;
    private final Second _second;

    public NodeTuple(First first, Second second) {
        _first = first;
        _second = second;
    }

    @Override
    protected ParseResult parseImpl(String chars, final int index) {
        ParseResult parse = _first.parse(chars, index);
        if (parse.isInvalid()) return parse;
        int nextIndex = parse.index();

        parse = _second.parse(chars, nextIndex);
        if (parse.isInvalid()) return parse;
        nextIndex = parse.index();

        return ParseResult.at(nextIndex);
    }

    @Override
    public String toString() {
        return _first.toString() + _second.toString();
    }

    @Override
    public NodeTuple<First, Second> deepCopy() {
        First firstCopy = _first.deepCopy();
        Second secondCopy = _second.deepCopy();

        return new NodeTuple<>(firstCopy, secondCopy);
    }

    @Override
    public void setData(NodeTuple<First, Second> other) {
        reset();
        _first.setData(other._first);
        _second.setData(other._second);
    }

    @Override
    public void reset() {
        _first.reset();
        _second.reset();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NodeTuple)) return false;

        NodeTuple<?, ?> nodeTuple = (NodeTuple<?, ?>) o;

        if (_first != null ? !_first.equals(nodeTuple._first) : nodeTuple._first != null) return false;
        return _second != null ? _second.equals(nodeTuple._second) : nodeTuple._second == null;
    }

    @Override
    public int hashCode() {
        int result = _first != null ? _first.hashCode() : 0;
        result = 31 * result + (_second != null ? _second.hashCode() : 0);
        return result;
    }
}
