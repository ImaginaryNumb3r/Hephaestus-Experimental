package parsing.json;

import parsing.model.*;

import java.util.Arrays;

/**
 * Creator: Patrick
 * Created: 27.10.2019
 * Grammar: Whitespace "Key" Whitespace ":" JValue
 */
public final class JAttribute extends SequenceNode implements CopyNode<JAttribute> {
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

    public String getKey() {
        return _key.toString();
    }

    public JValue getValue() {
        return _value;
    }

    public JValueType getType() {
        return _value.getType();
    }

    public boolean isBoolean() {
        return _value.isBoolean();
    }

    public boolean isObject() {
        return _value.isObject();
    }

    public boolean isArray() {
        return _value.isArray();
    }

    public boolean isString() {
        return _value.isString();
    }

    public boolean isNumber() {
        return _value.isNumber();
    }

    public boolean isNull() {
        return _value.isNull();
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
