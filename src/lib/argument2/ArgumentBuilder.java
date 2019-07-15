package lib.argument2;

import essentials.contract.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.join;
import static java.util.Arrays.asList;
import static lib.argument2.Argument.makeOption;

/**
 * A small and opinionated parser for command line arguments.
 * Constraints:
 *  - Each argument can only be used once. Duplicates are not allowed
 *  - The console input must exactly match the defined arguments. No redundancy allowed.
 *  - No extensive validation. If you want to ensure certain constraints (like a limit of total arguments), you must validate them after the input is parsed.
 *  - Very strict construction. This is a very transparent class. If you make a mistake, it is likely a source of bugs. I am merciless, even in terms of case sensitivity.
 *  - Don't like it? Don't use it!
 *  - You want to abuse the API? Well fine, have fun dealing with the consequences.
 *  - If you are hell-bent on destroying the API, let yourself go. We are not babysitters.
 */
public class ArgumentBuilder {
    public static final String ARGUMENT_PREFIX = "-";
    public static final String ARGUMENT_ASSIGNMENT = "=";
    public static final String ARGUMENT_DELIMITER = " ";
    private final Map<String, Argument> _options = new HashMap<>();
    private final Map<String, Argument> _values = new HashMap<>();
    private final Map<String, Argument> _arrays = new HashMap<>();

    public void addOption(@NotNull String name) {
        _options.put(name, new Argument(ArgumentType.OPTIONAL, name));
        checkForDuplicate(name, "option");
    }


    public void addArgument(@NotNull String name, @NotNull ArgumentType type) {
        test(name, type, name);
    }

    public void addArgument(@NotNull String name, @NotNull ArgumentType type, @NotNull String defaultValue) {
        test(name, type, defaultValue);
    }

    private void test(@NotNull String name, @NotNull ArgumentType type, String defaultValue) {
        Contract.checkNulls(name, type, defaultValue);
        if (name.isEmpty()) throw new IllegalArgumentException("Argument name may not be empty");

        var argument = new Argument(type, name);
        if (defaultValue != null) {
            argument.addValue(defaultValue);
        }

        _values.put(name, argument);
        checkForDuplicate(name, "argument");
    }


    public void addArrayArgument(@NotNull String name, @NotNull ArgumentType type, @NotNull Iterable<String> defaultValues) {
        Contract.checkNulls(name, type, defaultValues);
        Contract.checkNulls(defaultValues);
        if (name.isEmpty()) throw new IllegalArgumentException("Argument name may not be empty");

        _arrays.put(name, new Argument(type, name, defaultValues));
        checkForDuplicate(name, "argument");
    }

    public void addArrayArgument(@NotNull String name, @NotNull ArgumentType type, @NotNull String[] defaultValues) {
        addArrayArgument(name, type, asList(defaultValues));
    }

    public void addArrayArgument(@NotNull String name, @NotNull ArgumentType type, @NotNull String defaultValue) {
        addArrayArgument(name, type, List.of(defaultValue));
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
    }

    public Arguments parse(@NotNull String input) {
        var options = new HashMap<String, Argument>();
        var values = new HashMap<String, Argument>();
        var arrays = new HashMap<String, Argument>();


        // Strip trailing whitespaces of all arguments.
        var arguments = Stream.of(input.split(ARGUMENT_PREFIX))
            .map(String::stripTrailing)
            .collect(Collectors.toList());
        var tempArguments = new ArrayList<String>();
        boolean isContained;
        Swapper argumentLists = () -> {
            arguments.clear();
            arguments.addAll(tempArguments);
            tempArguments.clear();
        };

        // Parse Options.
        for (String argument : arguments) {
            isContained = _options.containsKey(argument);

            if (isContained) {
                // Ensure that no illegal characters are contained.
                if (argument.contains(ARGUMENT_ASSIGNMENT)) throw new ArgumentParseException("Option", ARGUMENT_ASSIGNMENT);
                if (argument.contains(ARGUMENT_DELIMITER)) throw new ArgumentParseException("Option", "[space]");

                options.put(argument, makeOption(input));
            } else {
                // If argument cannot be parsed, add it to the list of residuals.
                tempArguments.addAll(arguments);
            }
        }

        // Parse value Argument.
        argumentLists.swap();
        for (String argumentPair : arguments) {
            String[] parts = argumentPair.split(ARGUMENT_ASSIGNMENT);
            String name = parts[0];
            isContained = _values.containsKey(name);

            if (parts.length > 2) throw new ArgumentParseException("Cannot parse token: \"" + argumentPair + "\"");

            if (isContained) {
                // Ensure that no illegal characters are contained.
                if (argumentPair.contains(ARGUMENT_PREFIX)) throw new ArgumentParseException("Value Argument", ARGUMENT_PREFIX);
                if (argumentPair.contains(ARGUMENT_DELIMITER)) throw new ArgumentParseException("Value Argument", "[space]");

                // Retrieve value and set its value, if one is present.
                Argument argument = _values.get(name);
                if (parts.length == 2) {
                    String value = parts[1];
                    argument.addValue(value);
                }

                values.put(name, argument);
            } else {
                // If argument cannot be parsed, add it to the list of residuals.
                tempArguments.addAll(arguments);
            }
        }

        // Parse array argument.
        argumentLists.swap();
        for (String argumentPair : arguments) {
            String[] parts = argumentPair.split(ARGUMENT_DELIMITER);
            String name = parts[0];
            var argValues = Arrays.copyOfRange(parts, 1, parts.length);

            isContained = _arrays.containsKey(name);

            if (isContained) {
                // Ensure that no illegal characters are contained.
                if (argumentPair.contains(ARGUMENT_PREFIX)) throw new ArgumentParseException("Array Argument", ARGUMENT_PREFIX);
                if (argumentPair.contains(ARGUMENT_ASSIGNMENT)) throw new ArgumentParseException("Array Argument", ARGUMENT_ASSIGNMENT);

                Argument argument = _arrays.get(name);
                argument.addValues(argValues);

                values.put(name, argument);
            } else {
                throw new ArgumentParseException("Cannot parse argument token: \"" + argumentPair + "\"");
            }
        }

        return new Arguments(options, values, arrays);
    }

    private void checkForDuplicate(@NotNull String argName, @NotNull String argKind) {
        boolean duplicate = _options.containsKey(argName);
        duplicate &= _values.containsKey(argName);
        duplicate &= _arrays.containsKey(argName);

        if (duplicate) {
            throw new IllegalArgumentException("Cannot redefine " + argKind + " with name \"" + argName + "\" in argument builder");
        }
    }

    private Argument get(@NotNull String name) {
        Argument argument = _options.get(name);
        argument = argument != null ? argument : _values.get(name);
        argument = argument != null ? argument : _arrays.get(name);

        return argument;
    }

    private interface Swapper {
        void swap();
    }
}
