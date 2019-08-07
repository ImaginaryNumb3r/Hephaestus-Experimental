package lib;

import collections.iteration.LinearCollector;
import collections.iterator.Iterables;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Purpose:
 */
public class Lists {

    public static <T> List<T> of(T... elements) {
        var list = new ArrayList<T>();
        for (T element : elements) {
            list.add(element);
        }

        return list;
    }

    public static <T> List<T> of(Iterable<T> elements) {
        var list = new ArrayList<T>();
        for (T element : elements) {
            list.add(element);
        }

        return list;
    }

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
                return (left, right) -> {
                    left.addAll(right);
                    return left;
                };
            }
        };
    }

}
