package lib.argument2;

import essentials.contract.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;

import static java.lang.String.join;

/**
 * @author Patrick Plieschnegger
 */
public abstract class AbstractArgumentCollector {
    protected final Map<String, Argument> _options;
    protected final Map<String, Argument> _values;
    protected final Map<String, Argument> _arrays;
    protected final Map<String, String> _descriptions;

    public AbstractArgumentCollector(@NotNull Map<String, String> descriptions) {
        _options = new HashMap<>();
        _values = new HashMap<>();
        _arrays = new HashMap<>();
        _descriptions = new HashMap<>(descriptions);
    }

    protected AbstractArgumentCollector(@NotNull Map<String, Argument> options,
                                        @NotNull Map<String, Argument> values,
                                        @NotNull Map<String, Argument> arrays,
                                        @NotNull Map<String, String> descriptions
    ) {
        _options = new HashMap<>(options);
        _values = new HashMap<>(values);
        _arrays = new HashMap<>(arrays);
        _descriptions = new HashMap<>(descriptions);
    }

    protected Argument get(@NotNull String name) {
        Argument argument = _options.get(name);
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
        Argument argument = get(argName);

        if (argument == null) {
            String message = join(" ", "Argument", argName, "is not a defined argument.");
            throw new IllegalArgumentException(message);
        }

        _descriptions.put(argName, description);
    }

    public String getDescription(@NotNull String argumentName) {
        Contract.checkNull(argumentName);

        return _descriptions.get(argumentName);
    }

    protected Iterator<Argument> iterator() {
        var arguments = new ArrayList<Argument>();
        arguments.addAll(_options.values());
        arguments.addAll(_values.values());
        arguments.addAll(_arrays.values());

        return arguments.iterator();
    }
}
