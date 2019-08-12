package parsing.console.arguments;

import essentials.annotations.Positive;
import lib.EnumNotPresentException;
import lib.IntRange;
import org.jetbrains.annotations.NotNull;
import parsing.console.MultiArgument;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static java.lang.Integer.parseInt;
import static lib.IntRange.range;

/**
 * @author Patrick Plieschnegger
 */
public class IndexArguments extends MultiArgument<Integer> {
    private final IntRange _range;

    public IndexArguments(@NotNull String primaryName,
                          @NotNull Type type,
                          @NotNull Function<String, Integer> constructor,
                          @Positive int minOccurrences,
                          @Positive int maxOccurrences
    ) {
        super(primaryName, type, constructor);
        _range = range(minOccurrences, maxOccurrences);
    }

    public IndexArguments(@NotNull String primaryName,
                          @NotNull Type type,
                          @NotNull Function<String, Integer> constructor,
                          @Positive int expectedLength
    ) {
        this(primaryName, type, constructor, expectedLength, expectedLength);
    }

    public IndexArguments(@NotNull String primaryName,
                          @NotNull Type type,
                          @NotNull Function<String, Integer> constructor
    ) {
        this(primaryName, type, constructor, 0, Integer.MAX_VALUE);
    }

    @Override
    protected boolean consume(List<String> tokens, String key, int keyIndex) {
        var iter = tokens.listIterator(keyIndex + 1);
        var values = new ArrayList<Integer>();
        var invalidValues = new ArrayList<Integer>();

        try {
            while (iter.hasNext()) {
                int value = parseInt(iter.next());
                values.add(value);
            }
        } catch (NumberFormatException ignore) { }

        for (int value : values) {
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

        _value = values;
        return true;
    }
}
