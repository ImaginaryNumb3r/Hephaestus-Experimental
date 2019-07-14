package lib.argument;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Patrick Plieschnegger
 */
public class ArgumentParser {
    private final Set<Argument<?>> _arguments;
    private final char _delimiter;

    public ArgumentParser() {
        this('-');
    }
    public ArgumentParser(char delimiter) {
        _arguments = new HashSet<>();
        _delimiter = delimiter;
    }

    /**
     * @param argument that is to be parsed in the "parse" method.
     * @return true if the argument was already defined.
     */
    public boolean addArgument(Argument<?> argument) {
        return !_arguments.add(argument);
    }

    /**
     * @param consoleInput that is to be parsed.
     * @return names of parsed arguments
     * @throws IllegalStateException if a token in the console input does not correspond to the provided arguments.
     */
    public Set<String> parseStrict(String consoleInput) {
        return parse(consoleInput, true);
    }

    /**
     * @param consoleInput that is to be parsed.
     * @return names of parsed arguments
     */
    public Set<String> parse(String consoleInput) {
        return parse(consoleInput, false);
    }

    private Set<String> parse(String consoleInput, boolean strictMode) {
        String[] parts = consoleInput.split("[" + _delimiter +  "]");
        // Ignore first element.
        parts = Arrays.copyOfRange(parts, 1, parts.length);

        var set = new HashSet<String>();
        for (String token : parts) {
            boolean parsed = false;
            token = token.trim();

            var iter = _arguments.iterator();
            while (iter.hasNext() && !parsed) {
                Argument<?> argument = iter.next();
                parsed = argument.parse(token);

                if (parsed) {
                    set.add(argument.getName());
                }
            }

            if (!parsed && strictMode) {
                throw new IllegalStateException("Cannot find argument for input token \"" + token + "\".");
            }
        }


        return set;
    }
}
