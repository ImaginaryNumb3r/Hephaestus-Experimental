package lib.argument;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author Patrick Plieschnegger
 */
public class Argument<T> {
    private final String _name;
    private final Function<String, T> _parser;
    protected boolean _isParsed;
    protected T _value;

    /**
     *
     * @param name The name by which it is identified later.
     * @param parser A function which parses the argument token into values.
     *               It must return "null" if the token could not be parsed.
     */
    public Argument(@NotNull String name, @NotNull Function<String, T> parser) {
        _name = name;
        _parser = parser;
    }

    /**
     * Returns the value if it was successfully parsed prior to calling this method.
     *
     * @return The value, if all preconditions are set.
     * @throws IllegalStateException if the Argument has not be parsed prior to calling "getValue".
     * @throws NoSuchElementException if the Argument could not be parsed.
     */
    public T getValue() {
        if (!_isParsed) throw new IllegalStateException("Argument must be parsed before its value can be derived.");
        if (_value == null) throw new NoSuchElementException("Argument could not be parsed");

        return _value;
    }

    public String getName() {
        return _name;
    }

    /**
     * @param string
     * @return true if it was parsed successfully.
     */
    public boolean parse(@NotNull String string) {
        _value = _parser.apply(string);
        _isParsed = _value != null;

        return _value != null;
    }

    public static Argument<String> withValue(String name, String delimiter) {
        return withDefaultValue(name, delimiter, null);
    }

    public static Argument<String> withDefaultValue(String name, String delimiter, String defaultValue) {
        return new Argument<>(name, string -> {
            String[] parts = string.split(delimiter);

            // Validate argument name.
            if (!parts[0].equals(name)) return null;
            // Verify that only one delimiter was used.
            if (parts.length > 2) return null;
            // Use default value if no value was set.
            if (parts.length == 1) return defaultValue;

            // Parse argument value normally.
            return parts[1];
        });
    }

    public static Argument<List<String>> withMultiValue(String name, String delimiter, List<String> defaultValue) {
        return new Argument<>(name, string -> {
            String[] parts = string.split(delimiter);

            // Validate argument name.
            if (!parts[0].equals(name)) return null;
            // Use default value if no value was set.
            if (parts.length == 1) return defaultValue;

            var values = new ArrayList<String>();
            int i = 0;
            while (i++ != parts.length) {
                values.add(parts[i]);
            }

            return values;
        });
    }

    public static Argument<Boolean> asOption(String name) {
        return new OptionalArgument(name);
    }

    private static class OptionalArgument extends Argument<Boolean> {

        public OptionalArgument(@NotNull String name) {
            super(name, string -> string.equals(name)
                    ? true
                    : null
            );
            // Does not need to be parsed, it is simply "false" by default.
            _isParsed = true;
            _value = false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Argument)) return false;

        Argument<?> argument = (Argument<?>) o;

        return Objects.equals(_name, argument._name);
    }

    @Override
    public int hashCode() {
        return _name.hashCode();
    }
}
