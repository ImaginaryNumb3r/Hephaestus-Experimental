package lib.arguments;

import collections.Sets;
import essentials.util.Strings;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * @author Patrick Plieschnegger
 */
/*package*/ class ArgumentImpl implements Argument<List<String>> {
    private final String _name;
    private final ArgumentType _type;
    private final List<String> _values;
    private boolean _hasDefault;

    public ArgumentImpl(ArgumentType type, String name) {
        _values = new ArrayList<>();
        _type = type;
        _name = name;
        _hasDefault = false;
    }

    public ArgumentImpl(ArgumentType type, String name, Iterable<String> defaultValues) {
        this(type, name);
        defaultValues.forEach(_values::add);
        _hasDefault = !_values.isEmpty();
    }

    public ArgumentImpl(ArgumentType type, String name, String defaultValue) {
        this(type, name);

        if (defaultValue != null) {
            _values.add(defaultValue);
            _hasDefault = true;
        }
    }

    private ArgumentImpl(@NotNull ArgumentImpl arg) {
        this(arg._type, arg._name, arg._values);
        _hasDefault = arg._hasDefault;
    }

    public ArgumentType getType() {
        return _type;
    }

    @Override
    public Set<String> names() {
        return Sets.of(_name);
    }

    @Override
    public void setValue(List<String> value) {
        throw new UnsupportedOperationException();
    }

    /**
     * Do not use this reference to modify the list!
     */
    public List<String> getValue() {
        return _values;
    }

    @Override
    public String getDescription() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setDescription(String description) {
        throw new UnsupportedOperationException();
    }

    public boolean isValid() {
        return _type == ArgumentType.OPTIONAL
           || (_type == ArgumentType.MANDATORY && !_values.isEmpty());
    }

    public String getName() {
        return _name;
    }

    public static ArgumentImpl makeOption(String name) {
        return new ArgumentImpl(ArgumentType.OPTIONAL, name);
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

    protected ArgumentImpl mergeWith(ArgumentImpl other) {
        var argument = new ArgumentImpl(_type, _name);
        var list = new ArrayList<>(_values);
        argument._hasDefault = _hasDefault;

        if (!other._hasDefault) {
            list.addAll(other._values);
            argument._hasDefault = true;
        }

        argument.putValues(list);

        // TODO: Invariant validation that a value ArgumentImpl must never have more than one value.

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

    public ArgumentImpl copy() {
        return new ArgumentImpl(this);
    }

    @Override
    public String toString() {
        return _name + " { " + Strings.join(_values, ", ")  + " }";
    }
}
