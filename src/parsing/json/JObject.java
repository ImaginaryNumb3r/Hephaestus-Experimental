package parsing.json;

import parsing.model.CharTerminal;
import parsing.model.CopyNode;
import parsing.model.SequenceNode;
import parsing.model.WhitespaceToken;

import java.util.Arrays;

/**
 * Creator: Patrick
 * Created: 27.10.2019
 * An object contains a list of members. A member is a key value pair.
 * Each Member is separated by commas.
 * Grammar: ( '{' Whitespace  '}' ) |
 *          ( '{' JAttributes '}' )
 */
public final class JObject extends SequenceNode implements CopyNode<JObject> {
    private final JAttributes _values;

    public JObject() {
        _values = new JAttributes();

        var tokens = Arrays.asList(
                new WhitespaceToken(), new CharTerminal('{'),
                new WhitespaceToken(), _values,
                new WhitespaceToken(), new CharTerminal('}')
        );
        _sequence.addAll(tokens);
    }

    @Override
    public void setData(JObject other) {
        super.setData(other);
    }

    @Override
    public void reset() {
        super.reset();
    }

    @Override
    public JObject deepCopy() {
        JObject other = new JObject();
        other.setData(this);

        return other;
    }
}
