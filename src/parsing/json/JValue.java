package parsing.json;

import essentials.contract.NoImplementationException;
import parsing.model.*;

import java.util.HashMap;
import java.util.Objects;

/**
 * Creator: Patrick
 * Created: 27.10.2019
 * Grammar: JObject | JArray | ( '"' "Text '"' ) | JNumber | JBool | null
 */
public class JValue extends AbstractParseNode implements CopyNode<JValue> {
    private JValueType _type;
    private CopyNode<?> _value;
    // TODO: there should be concrete subclasses: JObject, JArray, JString, JNumber, JBool, JNull

    public JValue() {
        // Only approach I can think of: try to parse all of the different types and remember the according type as field.
        // Then always cast the object to its correct type
    }

    @Override
    public ParseResult parseImpl(String chars, int index) {
        var map = new HashMap<CopyNode<?>, JValueType>();
        map.put(new JObject(), JValueType.OBJECT);
        map.put(new JArray(), JValueType.ARRAY);
        map.put(new ContentToken("\""), JValueType.STRING);
        map.put(new JNumber(), JValueType.NUMBER);
        map.put(new JBool(), JValueType.BOOL);
        map.put(new StringTerminal("null"), JValueType.NULL);

        for (var entry : map.entrySet()) {
            CopyNode<?> node = entry.getKey();
            ParseResult result = tryParse(node, chars, index);

            if (result.isValid()) {
                _value = node;
                _type = entry.getValue();
                return result;
            }
        }

        return ParseResult.invalid(index, "Cannot parse value");
    }

    private ParseResult tryParse(ParseNode node, String chars, int index) {
        return node.parse(chars, index);
    }

    @Override
    public String toString() {
        return _value == null ? "unparsed" : _value.toString();
    }

    @Override
    public String asString() {
        return _value == null ? null : _value.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof JValue)) return false;
        JValue other = (JValue) obj;

        return _type == other._type
            && _value == other._value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(_type, _value);
    }

    @Override
    public JValue deepCopy() {
        JValue other = new JValue();
        other.setData(this);

        return other;
    }

    @Override
    public void setData(JValue other) {
        throw new NoImplementationException();
    }

    @Override
    public void reset() {
        _type = null;
        _value.reset();
    }
}
