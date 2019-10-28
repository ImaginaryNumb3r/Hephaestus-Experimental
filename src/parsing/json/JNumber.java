package parsing.json;

import parsing.model.AbstractParseNode;
import parsing.model.CopyNode;
import parsing.model.ParseResult;

import java.util.Objects;
import java.util.stream.Collector;

/**
 * Creator: Patrick
 * Created: 28.10.2019
 * ('-') (0 | 1..9) (0..9)* ( '.' (0..9)+ ) ( 'e' | 'E' ) ( '+' | '-' )? (0..9)*
 */
public class JNumber extends AbstractParseNode implements CopyNode<JNumber> {
    private Type _type;
    private String _string;
    private double _value;

    public JNumber() { }

    public JNumber(double value) {
        _value = value;
    }

    @Override
    protected ParseResult parseImpl(String chars, final int index) {
        var buffer = new StringBuilder();
        char ch = chars.charAt(index);
        int nextIndex = index;
        boolean negative = false;

        if (ch == '-') {
            negative = true;
            ++nextIndex;
        }

        ch = chars.charAt(nextIndex);
        if (!Character.isDigit(ch))
            return ParseResult.invalid(nextIndex, "Cannot parse number because of char: " + ch);
        _type = Type.INTEGER;

        buffer.append(ch);
        ++nextIndex;

        // Parse all upcoming numbers.
        for (ch = chars.charAt(nextIndex); Character.isDigit(ch);) {
            buffer.append(ch);
            ++nextIndex;
            ch = chars.charAt(nextIndex);
        }

        if (ch == '.') {
            _type = Type.REAL_NUMBER;
            buffer.append('.');
            ++nextIndex;
        }

        // Parse all upcoming numbers.
        for (ch = chars.charAt(nextIndex); Character.isDigit(ch);) {
            buffer.append(ch);
            ++nextIndex;
            ch = chars.charAt(nextIndex);
        }

        // Parse exponent.
        if (ch == 'e' | ch == 'E') {
            _type = Type.EXPONENT;
            buffer.append(ch);
            ++nextIndex;
            ch = chars.charAt(nextIndex);

            if (ch == '+' | ch == '-') {
                buffer.append(ch);
                ++nextIndex;
                ch = chars.charAt(nextIndex);
            }

            // Parse numbers of exponent.
            for (ch = chars.charAt(nextIndex); Character.isDigit(ch);) {
                buffer.append(ch);
                ++nextIndex;
                ch = chars.charAt(nextIndex);
            }
        }

        _string = buffer.toString();
        if (_type == Type.INTEGER) {
            _value = Integer.parseInt(_string);
        } else {
            _value = Double.parseDouble(_string);
        }

        if (negative) {
            _value = -_value;
        }

        return ParseResult.at(nextIndex);
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(_value);
    }

    @Override
    public JNumber deepCopy() {
        return new JNumber(_value);
    }

    @Override
    public void setData(JNumber other) {

    }

    @Override
    public void reset() {

    }

    private enum Type {
        INTEGER, REAL_NUMBER, EXPONENT
    }

}
