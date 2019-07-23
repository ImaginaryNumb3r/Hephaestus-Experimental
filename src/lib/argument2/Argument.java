package lib.argument2;

import lib.Strings;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * @author Patrick Plieschnegger
 */
/*package*/ class Argument {
    private final String _name;
    private final ArgumentType _type;
    private final List<String> _values;
    private boolean _hasDefault;

    public Argument(ArgumentType type, String name) {
        _values = new ArrayList<>();
        _type = type;
        _name = name;
        _hasDefault = false;
    }

    public Argument(ArgumentType type, String name, Iterable<String> defaultValues) {
        this(type, name);
        defaultValues.forEach(_values::add);
        _hasDefault = !_values.isEmpty();
    }

    public Argument(ArgumentType type, String name, String defaultValue) {
        this(type, name);

        if (defaultValue != null) {
            _values.add(defaultValue);
            _hasDefault = true;
        }
    }

    public Argument(@NotNull Argument arg) {
        this(arg._type, arg._name, arg._values);
        _hasDefault = arg._hasDefault;
    }

    public ArgumentType getType() {
        return _type;
    }

    /**
     * Do not use this reference to modify the list!
     */
    public List<String> getValues() {
        return _values;
    }

    public boolean isValid() {
        return _type == ArgumentType.OPTIONAL
           || (_type == ArgumentType.MANDATORY && !_values.isEmpty());
    }

    public String getName() {
        return _name;
    }

    public static Argument makeOption(String name) {
        return new Argument(ArgumentType.OPTIONAL, name);
    }

    public void clearValues() {
        _values.clear();
    }

    public void putValue(String value) {
        if (_hasDefault) {
            _values.clear();
            _hasDefault = false;
        }

        if (!_values.contains(value)) {
            _values.add(value);
        }
    }

    protected Argument mergeWith(Argument other) {
        var argument = new Argument(_type, _name);
        var list = new ArrayList<>(_values);
        argument._hasDefault = _hasDefault;

        if (!other._hasDefault) {
            list.addAll(other._values);
            argument._hasDefault = true;
        }

        argument.putValues(list);

        // TODO: Invariant validation that a value Argument must never have more than one value.

        return argument;
    }

    public void putValues(Iterable<String> values) {
        for (String value : values) {
            putValue(value);
        }
    }

    public void putValues(String[] values) {
        for (String value : values) {
            putValue(value);
        }
    }

    public Argument copy() {
        return new Argument(this);
    }

    @Override
    public String toString() {
        return _name + " { " + Strings.join(_values, ", ")  + " }";
    }
}
