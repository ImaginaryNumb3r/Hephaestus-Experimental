package parsing.json;

import parsing.model.CopyNode;
import parsing.model.MultiNode;
import parsing.model.SequenceNode;
import parsing.model.WhitespaceToken;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

/**
 * Creator: Patrick
 * Created: 27.10.2019
 * Grammar: JValue (',' JValue)*
 * // TODO: Consider moving this to the normal model package.
 * // TODO: Change implementation from a MultiValue node to a MultiNode with intermediate values that are saved in a separate list.
 */
/*package*/ final class JValues extends SequenceNode implements CopyNode<JValues> {
    private final JValue _first;
    private final MultiNode<MultiValue<JValue>> _values;

    public JValues() {
        _first = new JValue();
        _values = new MultiNode<>(() -> new MultiValue<>(JValue::new));

        _sequence.addAll(asList(new WhitespaceToken(), _first, _values));
    }

    @Override
    public void setData(JValues other) {
        super.setData(other);
    }

    @Override
    public void reset() {
        super.reset();
    }

    @Override
    public JValues deepCopy() {
        JValues other = new JValues();
        other.setData(this);

        return other;
    }

    public ArrayList<JValue> getValues() {
        ArrayList<JValue> values = new ArrayList<>();
        values.add(_first);

        _values.stream()
                .map(MultiValue::getValue)
                .forEach(values::add);

        return values;
    }

    public Stream<JValue> stream() {
        return getValues().stream();
    }
}
