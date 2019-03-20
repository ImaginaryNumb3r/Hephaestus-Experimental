package lib;

import collections.iteration.LinearCollector;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Purpose:
 */
public class Lists {

    public static <T, R extends List<T>> LinearCollector<T, R> receive(R receiver) {
        return new LinearCollector<>() {
            @Override
            public Supplier<R> supplier() {
                return () -> receiver;
            }

            @Override
            public BiConsumer<R, T> accumulator() {
                return List::add;
            }

            @Override
            public BinaryOperator<R> combiner() {
                return (left, right) -> left;
            }
        };
    }

}
