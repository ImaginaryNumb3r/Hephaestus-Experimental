package parsing.arguments;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

/**
 * Basic argument archetype.
 */
public class MultiArgument<T> extends AbstractArgument<List<T>> {
    private final Function<String, T> _constructor;
    private final Type _type;

    public MultiArgument(@NotNull Function<String, T> constructor,
                         @NotNull Type type
    ) {
        _constructor = constructor;
        _type = type;
    }

    /**
     *
     * @param tokens the separated command line arguments. Leading or trailing whitespaces must be trimmed.
     * @return
     * @throws ArgumentParseException
     */
    @Override
    protected boolean consume(List<String> tokens) throws ArgumentParseException {
        return false;
    }

    @Override
    public Type getType() {
        return _type;
    }
}
