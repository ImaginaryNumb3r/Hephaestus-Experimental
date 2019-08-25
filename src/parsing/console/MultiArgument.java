package parsing.console;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * An opinionated implementation of the multi-value argument archetype that is intended for re-use.
 */
public abstract class MultiArgument<T> extends AbstractArgument<List<T>> {
    protected final Function<String, T> _constructor;
    protected final Type _type;

    public MultiArgument(@NotNull String primaryName,
                         @NotNull Type type,
                         @NotNull Function<String, T> constructor
    ) {
        super(primaryName);
        _constructor = constructor;
        _type = type;
    }

    protected MultiArgument(@NotNull MultiArgument<T> original) {
        super(original._primaryName);
        _constructor = original._constructor;
        _type = original._type;
    }

    /**
     * By default, multiple matchingIndices will throw an exception.
     *
     *
     * OLD:
     * TODO: Huge problem. An exception will be thrown if the name of the argument is also a value in another multi argument.
     * In that case, an ambiguity exception must be thrown. The problem is that there is no delimiter for multi arguments.
     * Therefore... only one multi argument can exist? At the very least, an identifier must not be a valid name for a value
     * On the other hand: You should know what you are doing. We should define this case very well and put the constraints accordingly.
     * The multi argument class is a compromise and like value argument, it is inherently opinionated.
     *
     * @param tokens the separated command line arguments. Leading or trailing whitespaces must be trimmed.
     * @return
     * @throws ArgumentParseException if the method fails on an instance of type MANDATORY.
     */
    @Override
    public final boolean consume(List<String> tokens) throws ArgumentParseException {
        // Set to error and unset if parsing was successful.

        // Preconditions.
        assertPreconditions();
        List<String> matches = tokens.stream()
            .filter(_names::contains)
            .collect(Collectors.toList());

        var resultType = assertMatchOnce(matches);
        if (resultType.isError()) throw resultType.getData();
        if (resultType.isFailure()) return false;

        // Continue with logic.
        String key = matches.get(0);
        int keyIndex = tokens.indexOf(key);

        return consume(tokens, key, keyIndex);
    }

    /**
     * @implSpec implementor must set the {@code _status} to SUCCESS if successful.
     *
     * @param tokens
     * @param key
     * @param keyIndex
     * @return
     */
    protected abstract boolean consume(List<String> tokens, String key, int keyIndex);

    @Override
    public Type getType() {
        return _type;
    }
}
