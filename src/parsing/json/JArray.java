package parsing.json;

import parsing.model.CharTerminal;
import parsing.model.CopyNode;
import parsing.model.SequenceNode;
import parsing.model.WhitespaceToken;

import java.util.Arrays;

/**
 * Creator: Patrick
 * Created: 27.10.2019
 * An array consists of a list of list of values separated by commas.
 * Grammar: ( '[' Whitespace ']' ) |
 *          ( '[' JValue ( ',' JValue)* ']' )
 */
public final class JArray extends SequenceNode implements CopyNode<JArray> {
    private final JValues _values;

    public JArray() {
        _values = new JValues();

        _sequence.addAll(
                Arrays.asList(
                        new WhitespaceToken(), new CharTerminal('['),
                        new WhitespaceToken(), _values,
                        new WhitespaceToken(), new CharTerminal(']')
                )
        );
    }

    public JValues getValues() {
        return _values;
    }

    @Override
    public JArray deepCopy() {
        JArray other = new JArray();
        other.setData(this);

        return other;
    }

    @Override
    public void reset() {
        super.reset();
    }

    @Override
    public void setData(JArray other) {
        super.setData(other);
    }
}
