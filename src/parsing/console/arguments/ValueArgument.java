package parsing.console.arguments;

import org.jetbrains.annotations.NotNull;
import parsing.console.AbstractArgument;
import parsing.console.ArgumentParseException;

import java.util.List;

/**
 *
 */
public class ValueArgument<T> extends AbstractArgument<T> {
    private final Type _type;

    public ValueArgument(@NotNull String primaryName, Type type) {
        super(primaryName);
        _type = type;
    }

    @Override
    protected boolean consume(List<String> tokens) throws ArgumentParseException {
        return false;
    }

    @Override
    public Type getType() {
        return _type;
    }
}
