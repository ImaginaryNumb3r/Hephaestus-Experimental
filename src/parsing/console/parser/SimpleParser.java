package parsing.console.parser;

import org.jetbrains.annotations.NotNull;
import parsing.console.AbstractArgument;
import parsing.console.Argument;
import parsing.console.AbstractArgumentParser;
import parsing.console.arguments.ParseConstraintViolation;

import java.util.*;

import static java.util.function.Predicate.not;

/**
 * @author Patrick Plieschnegger
 */
public class SimpleParser implements AbstractArgumentParser {
    private final Set<AbstractArgument<?>> _arguments = new HashSet<>();
    private final HashMap<String, AbstractArgument<?>> _names = new HashMap<>();

    @Override
    public void addArgument(@NotNull AbstractArgument<?> argument) {
        _arguments.add(argument);

        for (String argName : argument.names()) {
            var previous = _names.put(argName, argument);

            if (previous != null) {
                throw new IllegalArgumentException("Duplicate names from different arguments added");
            }
        }
    }

    /**
     * Enforced Rules:
     *  - Every argument can only be parsed once.
     * @param input
     */
    @Override
    public void parse(String input) {
        var tokens = Arrays.asList(input.split("[\\s+]"));

        /*
         * Arguments consuming the tokens must not consume the list in a way that other arguments suddenly cannot be parsed.
         * Therefore we must check the list of arguments contained in the token list before parsing and after.
         * If it changed, an exception is thrown.
         */
        Set<AbstractArgument<?>> arguments = precondition(tokens);
        Set<AbstractArgument<?>> parsed = new HashSet<>();

        for (var argument : arguments) {
            boolean success = argument.consume(tokens);
            parsed.add(argument);

            // A parser implementation might omit throwing an exception if false was returned.
            if (!success) {
                // TODO: Problem, what if something failed, but you dont know because there are many possible points of failure?
                throw new ParseConstraintViolation("TODO: Failure");
            }
        }

        if (parsed.equals(arguments)) {
            throw new ParseConstraintViolation("");
        }
    }

    private Set<AbstractArgument<?>> precondition(List<String> tokens) {
        var parsedArguments = new HashSet<AbstractArgument<?>>();

        // Find arguments in input.
        for (String token : tokens) {
            if (_names.containsKey(token)) {
                var argument = _names.get(token);

                boolean duplicate = parsedArguments.add(argument);

                if (duplicate) {
                    String message = String.join(" ", "Duplicate argument", argument.primaryName(),
                        "for token", token, "and", "TODO: Other token name.");
                    throw new ParseConstraintViolation(message);
                }
            }
        }

        // Find missing mandatory arguments if they exist. If they do, throw an exception.
        var missing = _arguments.stream()
            .filter(Argument::isMandatory)
            .filter(not(parsedArguments::contains))
            .toArray();

        if (missing.length != 0) {
            throw new ParseConstraintViolation("Mandatory arguments not found: " + Arrays.toString(missing));
        }

        return parsedArguments;
    }
}
