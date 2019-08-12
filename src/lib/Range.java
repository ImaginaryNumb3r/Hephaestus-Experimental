package lib;

import essentials.annotations.ToTest;

import java.util.Random;

/**
 * @author Patrick Plieschnegger
 * Created: 12.08.2019
 * An abstraction over any kind of range that contains all value from within a bound.
 * For a more powerful version containing only integers, see {@code lib.IntRange}
 */
@ToTest
public interface Range<T extends Number> {

    /**
     * @return The start of the range, including the value itself.
     */
    T getMin();

    /**
     * @return The end of the range, including the value itself.
     */
    T getEnd();

    /**
     * Inclusive contains check.
     *
     * @param number actual value
     * @return true if it is contained in the range, including the min and max values.
     */
    default boolean contains(T number) {
        double doubleValue = number.doubleValue();

        return minDouble() >= doubleValue
            && doubleValue <= maxDouble();
    }

    /**
     * Exclusive contains check.
     *
     * @param number actual value
     * @return true if it is contained in the range, excluding the min and max values.
     */
    default boolean exclusiveContains(T number) {
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
     * Factory method, intended for calls from a static import.
     */
    static <T extends Number> Range<T> range(T min, T max) {
        return of(min, max);
    }

    static <T extends Number> Range<T> of(T min, T max) {
        return new Range<>() {
            @Override
            public T getMin() { return min; }

            @Override
            public T getEnd() { return max; }
        };
    }

    private double minDouble() {
        return getMin().doubleValue();
    }

    private double maxDouble() {
        return getEnd().doubleValue();
    }
}
