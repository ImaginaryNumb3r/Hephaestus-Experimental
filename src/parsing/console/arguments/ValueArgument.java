package parsing.console.arguments;

import org.jetbrains.annotations.NotNull;
import parsing.console.AbstractArgument;
import parsing.console.ArgumentParseException;

import java.util.ArrayList;
import java.util.List;


public class ValueArgument extends AbstractArgument<String> {
    private final Type _type;
    private final String _delimiter;
    private String _name; // The matching name for the argument.

    /**
     * // TODO: Allow definition of further secondary names.
     * @param primaryName
     * @param type
     * @param delimiter Must not contain whitespaces.
     */
    public ValueArgument(@NotNull String primaryName,
                         @NotNull String delimiter,
                         @NotNull Type type
    ) {
        super(primaryName);
        _type = type;
        _delimiter = delimiter;

        int whitespaces = primaryName.split("[W]").length;
        if (whitespaces != 1) throw new IllegalArgumentException("Name in ValueArgument must not contain whitespaces");

        whitespaces = delimiter.split("[W]").length;
        if (whitespaces != 1) throw new IllegalArgumentException("Delimiter in ValueArgument must not contain whitespaces");
    }

    @Override
    protected boolean consume(List<String> tokens) throws ArgumentParseException {
        // Set to error and unset if parsing was successful.
        _status = ParseStatus.FAIL;
        var matches = new ArrayList<String>();

        for (String token : tokens) {
            boolean doesMatch = false;

            var iter = _names.iterator();
            while (iter.hasNext() && !doesMatch) {
                String prefix = iter.next() + _delimiter;
                doesMatch = token.startsWith(prefix);
            }

            // Assert that only one delimiter exists.
            int delimiterCount = token.split(_delimiter).length - 1;
            if (delimiterCount > 1) {
                switch (_type) {
                    case MANDATORY:
                        throw new ArgumentParseException("Token " + token + " contains multiple delimiters of " + _delimiter);
                    case OPTIONAL:
                        // Continue parsing.
                }
            }

            // Add if no checks failed.
            matches.add(token);
        }

        // Count assertion must come after token invariant check.
        assertMatchOnce(matches);
        String match = matches.get(0);
        tokens.remove(match);

        String[] parts = match.split(_delimiter);
        _name = parts[0];
        _status = ParseStatus.SUCCESS;

        _value = parts[1];
        return true;
    }

    @Override
    public Type getType() {
        return _type;
    }
}
