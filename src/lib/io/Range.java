package lib.io;

import essentials.annotations.ToTest;
import lib.Strings;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

/**
 * @author Patrick Plieschnegger
 * Created: 12.08.2019
 * An abstraction over any kind of range that contains all value from within a bound.
 * For a more powerful version containing only integers, see {@code lib.IntRange}
 *
 * Ranges are immutable.
 */
@ToTest
public interface Range<T extends Number> {

    /**
     * @return The start of the range, including the value itself.
     *         Consecutive calls always return the same value.
     */
    T getStart();

    /**
     * @return The end of the range, including the value itself.
     *         Consecutive calls always return the same value.
     */
    T getEnd();

    /**
     * Inclusive contains check.
     *
     * @param number actual value.
     * @return true if it is contained in the range, including the min and max values.
     */
    default boolean contains(@NotNull T number) {
        double doubleValue = number.doubleValue();

        return minDouble() >= doubleValue
            && doubleValue <= maxDouble();
    }

    /**
     * Exclusive contains check.
     *
     * @param number actual value.
     * @return true if it is contained in the range, excluding the min and max values.
     */
    default boolean exclusiveContains(@NotNull T number) {
        double doubleValue = number.doubleValue();

        return minDouble() > doubleValue
            && doubleValue < maxDouble();
    }

    /**
     * @return a random number in the range, casted to a double.
     */
    default double random() {
        double exclusiveEnd = maxDouble() + 1;
        // Overflow aware code.
        if (exclusiveEnd < maxDouble()) {
            exclusiveEnd = maxDouble();
        }

        return new Random().doubles(minDouble(), exclusiveEnd).findFirst().getAsDouble();
    }

    /**
     * Convenience implementation intended as {@code toString()} method.
     */
    default String asString() {
        return Strings.concat('[', getStart(), ", ", getEnd(), ']');
    }

    /**
     * Factory method, intended for calls from a static import.
     */
    static <T extends Number> Range<T> range(@NotNull T start, @NotNull T end) {
        return of(start, end);
    }

    /**
     * Default range factory method.
     */
    static <T extends Number> Range<T> of(@NotNull T start, @NotNull T end) {
        return new Range<>() {
            @Override
            public T getStart() { return start; }

            @Override
            public T getEnd() { return end; }
        };
    }

    private double minDouble() {
        return getStart().doubleValue();
    }

    private double maxDouble() {
        return getEnd().doubleValue();
    }
}
