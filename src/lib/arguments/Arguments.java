package lib.arguments;

import java.util.*;

/**
 * @author Patrick Plieschnegger
 */
public class Arguments extends ArgumentCollector {

    protected Arguments(Map<String, ArgumentImpl> options,
                        Map<String, ArgumentImpl> values,
                        Map<String, ArgumentImpl> arrays,
                        Map<String, String> descriptions
    ) {
        super(options, values, arrays, descriptions);
    }

    public boolean hasOption(String optionName) {
        return _options.containsKey(optionName);
    }

    public boolean containsArgument(String argName) {
        return _values.containsKey(argName);
    }

    public boolean hasValue(String argName) {
        ArgumentImpl argument = _values.get(argName);

        return !argument.getValue().isEmpty();
    }

    public String getValue(String argName) {
        return fetchValue(argName)
            .orElseThrow(NoSuchElementException::new);
    }

    public Optional<String> fetchValue(String argName) {
        ArgumentImpl argument = _values.get(argName);
        if (argument == null) return Optional.empty();

        Collection<String> values = argument.getValue();

        return values.isEmpty()
            ? Optional.empty() // Since collection doesn't have "get(0)".
            : Optional.of(values.iterator().next());
    }

    public boolean containsArrayArgument(String name) {
        return _arrays.containsKey(name);
    }

    /**
     * Returns the arguments in the order they were declared.
     * @param arrayName
     * @return
     */
    public List<String> getArrayArgument(String arrayName) {
        return fetchArrayArgument(arrayName)
            .orElseThrow(NoSuchElementException::new);
    }

    public Optional<List<String>> fetchArrayArgument(String name) {
        ArgumentImpl argument = _arrays.get(name);

        return Optional.ofNullable(argument).map(ArgumentImpl::getValue);
    }
}
