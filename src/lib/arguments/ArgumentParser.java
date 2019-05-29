package lib.arguments;

import essentials.contract.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Creator: Patrick
 * Created: 17.05.2019
 * Purpose:
 */
public class ArgumentParser {
    private final Map<String, Argument> _declaredArguments;
    private Map<String, Argument> _actualArguments;

    public ArgumentParser() {
        _declaredArguments = new HashMap<>();
        _actualArguments = null;
    }

    public void addArgument(
            @NotNull String argumentName,
            @NotNull ArgumentOption option
    ) {
        addArgument(argumentName, null, option);
    }

    public Iterable<Argument> getDeclaredArguments() {
        return _declaredArguments.values();
    }

    public Iterable<Argument> getActualArguments() {
        return _declaredArguments.values();
    }

    public void parse(String string) {

    }

    public void addArgument(
            @NotNull String argumentName,
            @Nullable String defaultValue,
            @NotNull ArgumentOption option
    ) {
        Contract.checkNulls(option, argumentName);
        Argument previous = _declaredArguments.put(argumentName, new Argument(argumentName, defaultValue, option));

        if (previous != null) {
            String message = "Argument \"" + argumentName + "\" has already been defined";
            throw new ArgumentAlreadyExistsException(message);
        }
    }
}
