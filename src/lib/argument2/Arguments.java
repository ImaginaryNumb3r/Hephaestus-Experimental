package lib.argument2;

import java.util.*;

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


    public boolean containsArgument(String argName) {
        return _values.containsKey(argName);
    }

    public boolean hasValue(String argName) {
        Argument argument = _values.get(argName);

        return !argument.getValues().isEmpty();
    }

    public String getValue(String argName) {
        return fetchValue(argName)
            .orElseThrow(NoSuchElementException::new);
    }

    public Optional<String> fetchValue(String argName) {
        Argument argument = _values.get(argName);
        if (argument == null) return Optional.empty();

        List<String> values = argument.getValues();

        return values.isEmpty()
            ? Optional.empty()
            : Optional.of(values.get(0));
    }


    public boolean containsArrayArgument(String name) {
        return _arrays.containsKey(name);
    }

    public List<String> getArrayArgument(String arrayName) {
        return fetchArrayArgument(arrayName)
            .orElseThrow(NoSuchElementException::new);
    }

    public Optional<List<String>> fetchArrayArgument(String name) {
        Argument argument = _arrays.get(name);

        return Optional.ofNullable(argument).map(Argument::getValues);
    }
}