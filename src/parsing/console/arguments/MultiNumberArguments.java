package parsing.console.arguments;

import essentials.annotations.Positive;
import lib.EnumNotPresentException;
import lib.Range;
import org.jetbrains.annotations.NotNull;
import parsing.console.Argument;
import parsing.console.MultiArgument;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static lib.Range.range;

/**
 * @author Patrick Plieschnegger
 */
public class MultiNumberArguments<T extends Number> extends MultiArgument<T> {
    private final Range<T> _range;

    public MultiNumberArguments(@NotNull String primaryName,
                                @NotNull Type type,
                                @NotNull Function<String, T> constructor,
                                @Positive T minOccurrences,
                                @Positive T maxOccurrences
    ) {
        super(primaryName, type, constructor);
        _range = range(minOccurrences, maxOccurrences);
    }

    public static MultiNumberArguments<Integer> ofIntegers(
        @NotNull String primaryName,
        @NotNull Argument.Type type,
        @NotNull String... aliases
    ) {
        var argument = new MultiNumberArguments<>(
            primaryName, type,
            Integer::parseInt,
            Integer.MIN_VALUE,
            Integer.MAX_VALUE
        );
        argument.addAliases(aliases);

        return argument;
    }

    public static MultiNumberArguments<Double> ofDoubles(
        @NotNull String primaryName,
        @NotNull Argument.Type type,
        @NotNull String... aliases
    ) {
        var argument = new MultiNumberArguments<>(
            primaryName, type,
            Double::parseDouble,
            Double.MIN_VALUE,
            Double.MAX_VALUE
        );
        argument.addAliases(aliases);

        return argument;
    }

    @Override
    protected boolean consume(List<String> tokens, String key, int keyIndex) {
        // Set to error and unset if parsing was successful.
        _status = ParseStatus.FAIL;
        var iter = tokens.listIterator(keyIndex + 1);
        var values = new ArrayList<T>();
        var invalidValues = new ArrayList<T>();

        try {
            while (iter.hasNext()) {
                T value = _constructor.apply(iter.next());
                values.add(value);
            }
        } catch (NumberFormatException ignore) { }

        for (T value : values) {
            if (!_range.contains(value)) {
                invalidValues.add(value);
            }
        }

        if (!invalidValues.isEmpty()) {
            switch (_type) {
                case MANDATORY: return false;
                case OPTIONAL: throw new ArgumentConstraintViolation("TODO!");
                default: throw new EnumNotPresentException(Type.class, _type);
            }
        }

        for (int count = 0; count != values.size(); ++count) {
            iter.remove();
            iter.previous();
        }

        _status = ParseStatus.SUCCESS;
        _value = values;
        return true;
    }
}
