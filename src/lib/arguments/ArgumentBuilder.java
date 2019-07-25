package lib.arguments;

import essentials.contract.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Arrays.copyOfRange;
import static java.util.function.Predicate.not;
import static lib.Maps.join;
import static lib.arguments.Argument.makeOption;

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
 *  TODO: You could turn it into a fluent builder.
 */
public class ArgumentBuilder extends AbstractArgumentCollector {
    public static final String ARGUMENT_PREFIX = "-";
    public static final String ARGUMENT_ASSIGNMENT = "=";
    public static final String ARGUMENT_DELIMITER = " ";

    public ArgumentBuilder() {
        super(new HashMap<>());
    }

    public void addOption(@NotNull String name) {
        validateArgument(name);
        checkForDuplicate(name, "option");

        _options.put(name, new Argument(ArgumentType.OPTIONAL, name));
    }


    public void addArgument(@NotNull String name, @NotNull ArgumentType type) {
        addArgument(name, type, null);
    }

    /**
     * Makes the argument as implicitly optional since it can always fall back to the default value.
     * @param name
     * @param defaultValue
     */
    public void addArgument(@NotNull String name, @NotNull String defaultValue) {
        Contract.checkNull(defaultValue);
        addArgument(name, ArgumentType.OPTIONAL, defaultValue);
    }

    private void addArgument(@NotNull String name, @NotNull ArgumentType type, @Nullable String defaultValue) {
        Contract.checkNulls(type);
        validateArgument(name);

        if (name.isEmpty()) throw new IllegalArgumentException("Argument name may not be empty");
        checkForDuplicate(name, "argument");

        var argument = new Argument(type, name, defaultValue);

        _values.put(name, argument);
    }


    public void addArrayArgument(@NotNull String name, @NotNull ArgumentType type, @NotNull Iterable<String> defaultValues) {
        Contract.checkNulls(type, defaultValues);
        Contract.checkNulls(defaultValues);
        validateArgument(name);
        checkForDuplicate(name, "argument");

        for (String argument : defaultValues) {
            validateArgument(argument);
        }

        if (name.isEmpty()) throw new IllegalArgumentException("Argument name may not be empty");

        _arrays.put(name, new Argument(type, name, defaultValues));
    }

    public void addArrayArgument(@NotNull String name, @NotNull ArgumentType type) {
        addArrayArgument(name, type, new String[0]);
    }

    public void addArrayArgument(@NotNull String name, @NotNull ArgumentType type, @NotNull String... defaultValues) {
        addArrayArgument(name, type, asList(defaultValues));
    }

    public void addArrayArgument(@NotNull String name, @NotNull ArgumentType type, @NotNull String defaultValue) {
        addArrayArgument(name, type, List.of(defaultValue));
    }

    public Arguments parse(@NotNull String input) {
        Map<String, Argument> options = new HashMap<>();
        Map<String, Argument> values = new HashMap<>();
        Map<String, Argument> arrays = new HashMap<>();

        // Strip trailing whitespaces of all arguments.
        var arguments = Stream.of(input.split(ARGUMENT_PREFIX))
            .map(String::stripTrailing)
            .filter(not(String::isBlank))
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
                tempArguments.add(argument);
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
                argument = argument.copy();
                if (parts.length == 2) {
                    argument.putValue(parts[1]);
                }

                values.put(name, argument);
            } else {
                // If argument cannot be parsed, add it to the list of residuals.
                tempArguments.add(argumentPair);
            }
        }

        // Parse array argument.
        argumentLists.swap();
        for (String argumentPair : arguments) {
            String[] parts = argumentPair.split(ARGUMENT_DELIMITER);
            String name = parts[0];
            isContained = _arrays.containsKey(name);

            if (isContained) {
                // Ensure that no illegal characters are contained.
                if (argumentPair.contains(ARGUMENT_PREFIX)) throw new ArgumentParseException("Array Argument", ARGUMENT_PREFIX);
                if (argumentPair.contains(ARGUMENT_ASSIGNMENT)) throw new ArgumentParseException("Array Argument", ARGUMENT_ASSIGNMENT);

                Argument argument = _arrays.get(name);
                argument = argument.copy();
                var argValues = copyOfRange(parts, 1, parts.length);

                argument.clearValues();
                argument.putValues(argValues);

                arrays.put(name, argument);
            } else {
                throw new ArgumentParseException("Cannot parse argument token: \"" + argumentPair + "\"");
            }
        }

        // Reminder: Initially, the field lists contain the default values.
        options = join(options, _options, Argument::mergeWith);
        values = join(values, _values, Argument::mergeWith);
        arrays = join(arrays, _arrays, Argument::mergeWith);

        verifyParsing(options, values, arrays);

        // Apply changes to internal state once everything is verified.
        _options.putAll(options);
        _values.putAll(values);
        _arrays.putAll(arrays);

        return new Arguments(options, values, arrays, _descriptions);
    }

    private void verifyParsing(Map<String, Argument>... argumentMaps) {
        var forgottenArguments = new ArrayList<String>();

        for (var argumentMap : argumentMaps) {
            for (Argument arg : argumentMap.values()) {
                if (!arg.isValid())
                    forgottenArguments.add(arg.getName());
            }
        }

        if (!forgottenArguments.isEmpty()) {
            throw new ArgumentParseException(forgottenArguments);
        }
    }

    private void checkForDuplicate(@NotNull String argName, @NotNull String argKind) {
        boolean duplicate = _options.containsKey(argName);
        duplicate |= _values.containsKey(argName);
        duplicate |= _arrays.containsKey(argName);

        if (duplicate) {
            throw new IllegalArgumentException("Cannot redefine " + argKind + " with name \"" + argName + "\" in argument builder");
        }
    }

    private void validateArgument(String input) {
        Contract.checkNull(input);
        if (input.isEmpty())
            throw new IllegalArgumentException("Empty strings are not allowed as arguments");

        for (char ch : input.toCharArray()) {
            if (!Character.isAlphabetic(ch) && !Character.isDigit(ch))
                throw new IllegalArgumentException("Only letters and numbers are allowed as argument names");
        }
    }

    private interface Swapper {
        void swap();
    }
}
