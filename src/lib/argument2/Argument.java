package lib.argument2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrick Plieschnegger
 */
/*package*/ class Argument {
    private final String _name;
    private final ArgumentType _type;
    private final List<String> _values;

    public Argument(ArgumentType type, String name) {
        _values = new ArrayList<>();
        _type = type;
        _name = name;
    }

    public Argument(ArgumentType type, String name, Iterable<String> defaultValues) {
        this(type, name);
        defaultValues.forEach(_values::add);
    }

    public Argument(ArgumentType type, String name, String defaultValue) {
        this(type, name);
        _values.add(defaultValue);
    }

    public ArgumentType getType() {
        return _type;
    }

    public List<String> getValues() {
        return _values;
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

    public void addValue(String value) {
        _values.add(value);
    }

    public void addValues(String[] values) {
        for (String value : values) {
            _values.add(value);
        }
    }
}
