package parsing.model;

import java.util.Objects;
import java.util.Optional;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Purpose:
 */
public class EitherNode<O extends CopyNode<O>, M extends CopyNode<M>> extends AbstractParseNode {
    protected final O _optional;
    protected final M _mandatory;
    private Status _status;

    public EitherNode(O optional, M mandatory) {
        _optional = optional;
        _mandatory = mandatory;
        _status = Status.NONE;
    }

    @Override
    protected int parseImpl(String chars, int index) {
        int nextIndex = _optional.parse(chars, index);

        if (nextIndex != INVALID) {
            index = nextIndex;
            _status = Status.OPTIONAL;
        } else {
            nextIndex = _mandatory.parse(chars, index);

            if (nextIndex != INVALID) {
                index = nextIndex;
                _status = Status.MANDATORY;
            }
        }

        return index;
    }

    public boolean hasFirst() {
        return _status == Status.OPTIONAL;
    }

    public Optional<O> ifFirst() {
        return _status == Status.OPTIONAL
                ? Optional.of(_optional)
                : Optional.empty();
    }

    public boolean hasSecond() {
        return _status == Status.MANDATORY;
    }

    public Optional<M> ifSecond() {
        return _status == Status.OPTIONAL
                ? Optional.of(_mandatory)
                : Optional.empty();
    }

    public CopyNode<?> getValue() {
        return _status == Status.OPTIONAL ? _optional : _mandatory;
    }

    @Override
    public String toString() {
        return _status == Status.OPTIONAL  ? _optional.toString()
             : _status == Status.MANDATORY ? _mandatory.toString()
             : "";
    }

    @Override
    public EitherNode<O, M> deepCopy() {
        O optionalCopy = _optional.deepCopy();
        M mandatoryCopy = _mandatory.deepCopy();

        return new EitherNode<>(optionalCopy, mandatoryCopy);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EitherNode)) return false;
        EitherNode<?, ?> that = (EitherNode<?, ?>) o;
        return Objects.equals(_optional, that._optional) &&
                Objects.equals(_mandatory, that._mandatory) &&
                _status == that._status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(_optional, _mandatory, _status);
    }

    private enum Status {
        OPTIONAL, MANDATORY, NONE
    }
}
