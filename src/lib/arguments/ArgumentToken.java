package lib.arguments;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author Patrick Plieschnegger
 */
/*package*/ class Argument {
    protected final String _name;
    protected final List<String> _values;
    protected final Function<List<String>, String> _validator;

    public Argument(String name, Function<List<String>, String> validator) {
        _name = name;
        _values = new ArrayList<>();
        _validator = validator;
    }

    /**
     * Checks whether the argument is in a valid state.
     */
    public boolean isValid() {
        String isInvalid = _validator.apply(_values);
        return isInvalid != null;
    }

    public String invalidReason() {
        return _validator.apply(_values);
    }

    public String getName() {
        return _name;
    }

    public List<String> getValue() {
        return _values;
    }

    public void addValue(String value) {
        _values.add(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Argument)) return false;

        Argument argument = (Argument) o;

        if (_name != null ? !_name.equals(argument._name) : argument._name != null) return false;
        return Objects.equals(_values, argument._values);
    }

    @Override
    public int hashCode() {
        int result = _name != null ? _name.hashCode() : 0;
        result = 31 * result + _values.hashCode();
        return result;
    }
}
