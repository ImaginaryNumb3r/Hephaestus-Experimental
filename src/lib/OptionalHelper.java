package lib;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * A simple functional class with the same APIs such as {@link java.util.Optional}.
 *
 */
public interface OptionalHelper<V, O extends OptionalHelper<V, O>> {
/*
    static <V, O> OptionalHelper<V, O> empty() { return Optional.empty(); }

    static <V, O> OptionalHelper<V, O> of(T value) {return Optional.of(value);}

    static <V, O> OptionalHelper<V, O> ofNullable(T value) {return Optional.ofNullable(value);}

    V get();

    default boolean isEmpty() { return !isPresent(); }

    boolean isPresent();

    default void ifPresent(Consumer<? super V> action) { if (isPresent()) { action.accept(get()); }}

    default void ifPresentOrElse(Consumer<? super V> action, Runnable emptyAction) {
        if (isPresent()) {
            action.accept(get());
        } else {
            emptyAction.run();
        }
    }

    default OptionalHelper<V, O> filter(Predicate<? super V> predicate) {
        if (predicate.test(get())) {
            return this;
        } else {
            return empty();
        }
    }

    default <U> Optional<U> map(Function<? super V, ? extends U> mapper) {return _delegate.map(mapper);}

    default <U> Optional<U> flatMap(Function<? super V, ? extends Optional<? extends U>> mapper) {return _delegate.flatMap(mapper);}

    default Optional<V> or(Supplier<? extends Optional<? extends V>> supplier) {return _delegate.or(supplier);}

    default Stream<V> stream() {return _delegate.stream();}

    default V orElse(V other) {return _delegate.orElse(other);}

    default V orElseGet(Supplier<? extends V> supplier) {return _delegate.orElseGet(supplier);}

    default V orElseThrow() {return _delegate.orElseThrow();}

    default <X extends Throwable> V orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {return _delegate.orElseThrow(exceptionSupplier);}

    @Override
    default boolean equals(Object obj) {return _delegate.equals(obj);}

    @Override
    default int hashCode() {return _delegate.hashCode();}

    @Override
    default String toString() {return _delegate.toString();}
*/
}
