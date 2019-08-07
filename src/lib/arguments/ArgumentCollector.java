package lib.arguments;

import essentials.contract.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static java.lang.String.join;

/**
 * @author Patrick Plieschnegger
 */
public abstract class ArgumentCollector {
    protected final Map<String, ArgumentImpl> _options;
    protected final Map<String, ArgumentImpl> _values;
    protected final Map<String, ArgumentImpl> _arrays;
    protected final Map<String, String> _descriptions;

    public ArgumentCollector(@NotNull Map<String, String> descriptions) {
        _options = new HashMap<>();
        _values = new HashMap<>();
        _arrays = new HashMap<>();
        _descriptions = new HashMap<>(descriptions);
    }

    protected ArgumentCollector(@NotNull Map<String, ArgumentImpl> options,
                                @NotNull Map<String, ArgumentImpl> values,
                                @NotNull Map<String, ArgumentImpl> arrays,
                                @NotNull Map<String, String> descriptions
    ) {
        _options = new HashMap<>(options);
        _values = new HashMap<>(values);
        _arrays = new HashMap<>(arrays);
        _descriptions = new HashMap<>(descriptions);
    }

    protected ArgumentImpl get(@NotNull String name) {
        ArgumentImpl argument = _options.get(name);
        argument = argument != null ? argument : _values.get(name);
        argument = argument != null ? argument : _arrays.get(name);

        return argument;
    }

    /**
     * @param argName name of the argument.
     * @param description description to add to the argument.
     * @throws java.util.NoSuchElementException if the argument was not defined.
     */
    public void addDescription(@NotNull String argName, @NotNull String description) {
        ArgumentImpl argument = get(argName);

        if (argument == null) {
            String message = join(" ", "ArgumentImpl", argName, "is not a defined argument.");
            throw new IllegalArgumentException(message);
        }

        _descriptions.put(argName, description);
    }

    public String getDescription(@NotNull String argumentName) {
        Contract.checkNull(argumentName);

        return _descriptions.get(argumentName);
    }

    protected Iterator<ArgumentImpl> iterator() {
        var arguments = new ArrayList<ArgumentImpl>();
        arguments.addAll(_options.values());
        arguments.addAll(_values.values());
        arguments.addAll(_arrays.values());

        return arguments.iterator();
    }
}
