package parsing.json;

import parsing.model.*;

import java.util.Arrays;

/**
 * Creator: Patrick
 * Created: 27.10.2019
 * Purpose:
 */
public class JAttribute extends SequenceNode implements CopyNode<JAttribute> {
    private ContentNode _key;
    private JValue _value;

    public JAttribute() {
        _key = new ContentNode("\"");
        _value = new JValue();

        _sequence.addAll(
                Arrays.asList(
                        new WhitespaceToken(), _key,
                        new WhitespaceToken(), new CharTerminal(':'),
                        new WhitespaceToken(), _value
                )
        );
    }

    @Override
    public void setData(JAttribute other) {
        super.setData(other);
    }

    @Override
    public void reset() {
        super.reset();
    }

    @Override
    public JAttribute deepCopy() {
        JAttribute other = new JAttribute();
        other.setData(this);

        return other;
    }
}
