package lib.arguments;

import java.util.Objects;

/**
 * @author Patrick Plieschnegger
 */
/*package*/ class Argument<T> {
    protected final String _name;
    protected final boolean _mandatory;
    protected T _value;

    public Argument(String name, T value, boolean required) {
        _name = name;
        _value = value;
        _mandatory = required;
    }

    /**
     * Checks whether the argument is in a valid state.
     * @return false if this is a mandatory argument and has no value. Otherwise true.
     */
    public boolean isValid() {
        return !_mandatory || _value != null;
    }

    public String getName() {
        return _name;
    }

    public boolean isMandatory() {
        return _mandatory;
    }

    public T getValue() {
        return _value;
    }

    public void setValue(T value) {
        _value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Argument)) return false;

        Argument argument = (Argument) o;

        if (_mandatory != argument._mandatory) return false;
        if (!Objects.equals(_name, argument._name)) return false;
        return Objects.equals(_value, argument._value);
    }

    @Override
    public int hashCode() {
        int result = _name != null ? _name.hashCode() : 0;
        result = 31 * result + (_mandatory ? 1 : 0);
        result = 31 * result + (_value != null ? _value.hashCode() : 0);
        return result;
    }
}
