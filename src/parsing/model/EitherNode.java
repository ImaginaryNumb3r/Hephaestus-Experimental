package parsing.model;

import lib.Nulls;

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
    protected Status _status;

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

        return nextIndex;
    }

    public boolean hasFirst() {
        return _status == Status.OPTIONAL;
    }

    public Optional<O> first() {
        return _status == Status.OPTIONAL
                ? Optional.of(_optional)
                : Optional.empty();
    }

    public boolean hasSecond() {
        return _status == Status.MANDATORY;
    }

    public Optional<M> second() {
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

    protected void reset() {
        Nulls.ifPresent(_mandatory, _mandatory::reset);
        Nulls.ifPresent(_optional, _optional::reset);
        _status = Status.NONE;
    }

    protected void setData(EitherNode<O, M> other) {
        if (_mandatory != null) {
            _mandatory.setData(other._mandatory);
        }
        if (_optional != null) {
            _optional.setData(other._optional);
        }

        _status = other._status;
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

    protected enum Status {
        OPTIONAL, MANDATORY, NONE
    }
}
