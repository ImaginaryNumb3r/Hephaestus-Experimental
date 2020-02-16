package parsing.json;

import parsing.model.*;

import java.util.Arrays;
import java.util.function.Supplier;

/**
 * Creator: Patrick
 * Created: 27.10.2019
 * A value that is preceded by a comma and according whitespaces. It is to be used in objects and arrays.
 * Grammar: ',' T
 */
/*package*/ final class MultiValue<T extends CopyNode<T>> extends SequenceNode implements CopyNode<MultiValue<T>>  {
    private final Supplier<T> _valueConstructor;
    private final T _value;

    public MultiValue(Supplier<T> valueConstructor) {
        this(valueConstructor, valueConstructor.get());
    }

    public MultiValue(Supplier<T> valueConstructor, T defaultValue) {
        _valueConstructor = valueConstructor;
        _value = defaultValue;

        _sequence.addAll(
                Arrays.asList(
                        new WhitespaceToken(), new CharTerminal(','),
                        new WhitespaceToken(), _value
                )
        );
    }

    @Override
    public MultiValue<T> deepCopy() {
        MultiValue<T> other = new MultiValue<>(_valueConstructor);
        other.setData(this);

        return other;
    }

    public T getValue() {
        return _value;
    }

    @Override
    public void setData(MultiValue other) {
        super.setData(other);
    }

    @Override
    public void reset() {
        super.reset();
    }
}
