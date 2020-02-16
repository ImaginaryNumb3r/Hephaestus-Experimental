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
 * Grammar: Whitespace JAttribute ( Whitespace ',' JAttribute )*
 */
/*package*/ final class JAttributes extends SequenceNode implements CopyNode<JAttributes> {
    private final JAttribute _first;
    private final MultiNode<MultiValue<JAttribute>> _values;

    public JAttributes() {
        _first = new JAttribute();
        _values = new MultiNode<>(() -> new MultiValue<>(JAttribute::new));

        _sequence.addAll(asList(new WhitespaceToken(), _first, _values));
    }

    @Override
    public void setData(JAttributes other) {
        super.setData(other);
    }

    @Override
    public void reset() {
        super.reset();
    }

    @Override
    public JAttributes deepCopy() {
        JAttributes other = new JAttributes();
        other.setData(this);

        return other;
    }

    public List<JAttribute> getAttributes() {
        List<JAttribute> values = new ArrayList<>();
        values.add(_first);

        _values.stream()
                .map(MultiValue::getValue)
                .forEach(values::add);

        return values;
    }

    public Stream<JAttribute> stream() {
        return getAttributes().stream();
    }
}
