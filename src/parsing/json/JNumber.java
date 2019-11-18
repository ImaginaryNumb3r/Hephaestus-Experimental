package parsing.json;

import parsing.model.AbstractParseNode;
import parsing.model.CopyNode;
import parsing.model.ParseResult;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static java.util.Arrays.asList;

/**
 * Creator: Patrick
 * Created: 28.10.2019
 * Integer Grammar:     '-'? (0..9)*
 * Real Number Grammar: '-'? '0'? '.' (0..9)+ ( ( 'e' | 'E' ) ( '+' | '-' )? (0..9)+ )?
 */
public final class JNumber extends AbstractParseNode implements CopyNode<JNumber> {
    private boolean _isRealNumber;
    private String _string;
    private double _value = Double.NaN;
    private static final Set<Character> ALLOWED_CHARS;

    static {
        ALLOWED_CHARS = new HashSet<>();
        ALLOWED_CHARS.addAll(asList(
                'e', 'E', '.', '+', '-'
        ));
    }

    public JNumber() { }

    public JNumber(double value) {
        _value = value;
    }

    public ParseResult parseValue(long value) {
        return parseValue(Long.toString(value));
    }

    public ParseResult parseValue(double value) {
        return parseValue(Double.toString(value));
    }

    public ParseResult parseValue(String string) {
        return parseImpl(string, 0);
    }

    @Override
    protected ParseResult parseImpl(String chars, final int index) {
        char ch = chars.charAt(index);
        if (!canParse(ch)) {
            return ParseResult.invalid(index, "Cannot parse as value at index: " + index);
        }

        var buffer = new StringBuilder();
        int nextIndex = index;

        while (canParse(ch)) {
            buffer.append(ch);
            ++nextIndex;
            if (chars.length() == nextIndex)
                ParseResult.invalid(index, "Index is larger than document length.");

            ch = chars.charAt(nextIndex);
        }

        String numberStr = buffer.toString();
        try {
            _value = Double.parseDouble(numberStr);
            _string = numberStr;
            _isRealNumber = isARealNumber();
        } catch (NumberFormatException ex) {
            return ParseResult.invalid(index, "cannot parse as number: " + numberStr);
        }

        return ParseResult.at(nextIndex);
    }

    private boolean canParse(char ch) {
        return Character.isDigit(ch) || ALLOWED_CHARS.contains(ch);
        }

    /*
    // TODO: Doesn't work yet.
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
    } */

    @Override
    public String toString() {
        return _string;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof JNumber)) return false;
        JNumber other = (JNumber) obj;

        return _string.equals(other._string);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_value);
    }

    @Override
    public JNumber deepCopy() {
        JNumber copy = new JNumber();
        copy.setData(this);

        return copy;
    }

    @Override
    public void setData(JNumber other) {
        _string = other._string;
        _value = other._value;
    }

    @Override
    public void reset() {
        _string = null;
        _value = Double.NaN;
    }

    public String getAsString() {
        return _string;
    }

    /**
     * Returns the parsed number as a double even if it originally was an integer.
     */
    public double getAsDouble() {
        return _value;
    }

    /**
     * Returns the parsed number as an integer.
     * This can result in a loss of information if the parsed number was actually a real number.
     */
    public int getAsInteger() {
        return (int) _value;
    }

    /**
     * Returns the parsed number as a long.
     * This can result in a loss of information if the parsed number was actually a real number.
     */
    public long getAsLong() {
        return (long) _value;
    }

    public boolean isRealNumber() {
        return _isRealNumber;
    }

    /**
     * True if the original number was an integer and can be represented as an int.
     */
    public boolean isInteger() {
        if (_isRealNumber) return false;

        int intVal = (int) _value;
        long longVal = (long) _value;

        return intVal == (long) longVal;
    }

    public boolean isLong() {
        if (_isRealNumber) return false;

        // Cast to long to cut-off potential digits.
        long longVal = (long) _value;

        return _value == (double) longVal;
    }

    private boolean isARealNumber() {
        for (char ch : _string.toCharArray()) {
            if (ch == '.') {
                return true;
            }
        }
        return false;
    }
}
