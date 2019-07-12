package lib.arguments;

import essentials.contract.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Objects;

import static lib.arguments.ArgumentAlreadyExistsException.assertNoDuplicate;

/**
 * @author Patrick Plieschnegger
 */
public class ArgumentsBuilder {
    protected final HashMap<String, Argument<String>> _argumentValueMap;
    protected final HashMap<String, Argument<String[]>> _argumentArrayMap;

    public ArgumentsBuilder() {
        _argumentValueMap = new HashMap<>();
        _argumentArrayMap = new HashMap<>();
    }

    //<editor-fold desc="Single Argument">
    public void addArgument(@NotNull String name, @NotNull String defaultValue) {
        addArgument(name, defaultValue, true);
    }

    public void addMandatoryArgument(@NotNull String name) {
        addArgument(name, null, true);
    }

    public void addOptionalArgument(@NotNull String name) {
        addArgument(name, null, false);
    }

    private void addArgument(@NotNull String name, @Nullable String value, boolean required) {
        Contract.checkNulls(name, value);
        // TODO: Turn into local function.
        assertNoDuplicate(_argumentArrayMap, name);
        assertNoDuplicate(_argumentValueMap, name);

        var stringArgument = new Argument<>(name, value, required);
        _argumentValueMap.put(name, stringArgument);
    }
    //</editor-fold>

    //<editor-fold desc="Array Argument">
    public void addArrayArgument(@NotNull String name, @NotNull String[] defaultArray) {
        addArrayArgument(name, defaultArray, true);
    }

    public void addMandatoryArrayArgument(@NotNull String name) {
        addArrayArgument(name, null, true);
    }

    public void addOptionalArrayArgument(@NotNull String name) {
        addArrayArgument(name, null, false);
    }

    private void addArrayArgument(@NotNull String name, @Nullable String[] value, boolean required) {
        Contract.checkNull(name);
        assertNoDuplicate(_argumentArrayMap, name);
        assertNoDuplicate(_argumentValueMap, name);

        var stringArgument = new Argument<>(name, value, required);
        _argumentArrayMap.put(name, stringArgument);
    }
    //</editor-fold>

    public ArgumentParser buildParser() {
        return buildParser(false);
    }

    public ArgumentParser buildParser(boolean strict) {
        return new ArgumentParser(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArgumentsBuilder)) return false;

        ArgumentsBuilder that = (ArgumentsBuilder) o;

        if (!Objects.equals(_argumentValueMap, that._argumentValueMap))
            return false;
        return Objects.equals(_argumentArrayMap, that._argumentArrayMap);
    }

    @Override
    public int hashCode() {
        int result = _argumentValueMap.hashCode();
        result = 31 * result + _argumentArrayMap.hashCode();
        return result;
    }
}
