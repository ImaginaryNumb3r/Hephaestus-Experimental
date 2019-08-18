package parsing.console.arguments;

import org.jetbrains.annotations.NotNull;
import parsing.console.AbstractArgument;
import parsing.console.ArgumentParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


public class ValueArgument<T> extends AbstractArgument<T> {
    private final Type _type;
    private final String _delimiter;
    private final Function<String, T> _constructor;
    private String _name; // The matching name for the argument.
    private T _value;

    public ValueArgument(@NotNull String primaryName,
                         @NotNull Type type,
                         @NotNull String delimiter,
                         @NotNull Function<String, T> constructor
    ) {
        super(primaryName);
        _type = type;
        _delimiter = delimiter;
        _constructor = constructor;
    }

    @Override
    protected boolean consume(List<String> tokens) throws ArgumentParseException {
        var matches = new ArrayList<String>();

        for (String token : tokens) {
            boolean doesMatch = false;

            var iter = _names.iterator();
            while (iter.hasNext() && !doesMatch) {
                String prefix = iter.next() + _delimiter;
                doesMatch = token.startsWith(prefix);
            }

            // Assert that only one delimiter exists.
            int delimiterCount = token.split(_delimiter).length;
            if (delimiterCount > 1) {
                switch (_type) {
                    case MANDATORY:
                        throw new ArgumentParseException("Token " + token + " contains multiple delimiters of " + _delimiter);
                    case OPTIONAL:
                        // Continue parsing.
                }
            }
        }

        // Count assertion must come after token invariant check.
        assertMatchOnce(matches);
        String match = matches.get(0);
        tokens.remove(match);

        String[] parts = match.split(_delimiter);
        _name = parts[0];
        _value = _constructor.apply(parts[1]);

        return true;
    }

    @Override
    public Type getType() {
        return _type;
    }
}
