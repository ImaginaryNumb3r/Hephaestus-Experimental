package lib.argument2;

import essentials.contract.NoImplementationException;

import java.util.ArrayList;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * @author Patrick Plieschnegger
 */
public class Arguments {
    private final Map<String, Argument> _options;
    private final Map<String, Argument> _values;
    private final Map<String, Argument> _arrays;

    public Arguments(Map<String, Argument> options, Map<String, Argument> values, Map<String, Argument> arrays) {
        _options = options;
        _values = values;
        _arrays = arrays;
    }

    public boolean containsOption(String optionName) {
        return _options.containsKey(optionName);
    }

    public boolean isPresent(String argName) {
        throw new NoImplementationException();
    }

    public boolean containsArgument(String argName) {
        return _values.containsKey(argName);
    }

    public String getValue(String argName) {
        throw new NoSuchElementException();
    }

    public Optional<String> fetchValue(String argName) {
        throw new NoSuchElementException();
    }

    public ArrayList<String> getArray() {
        throw new NoImplementationException();
    }

    public boolean containsArrayArgument(String name) {
        return _arrays.containsKey(name);
    }

}
