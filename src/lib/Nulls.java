package lib;

import essentials.contract.InstanceNotAllowedException;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author Patrick Plieschnegger
 */
public final class Nulls {

    private Nulls() {
        throw new InstanceNotAllowedException(getClass());
    }

    public static <T> T init(T value, @NotNull T fallback) {
        return value != null ? value : fallback;
    }

    public static <T> T init(T value, @NotNull Supplier<T> initializer) {
        return value != null ? value : initializer.get();
    }

    public static <T> T ifNull(T value, @NotNull T supplement) {
        return value != null ? value : supplement;
    }

    public static <T> void ifnull(T object, @NotNull Runnable action) {
        if (object == null) {
            action.run();
        }
    }

    public static <T> void ifPresent(T object, @NotNull Consumer<T> consumer) {
        if (object != null) {
            consumer.accept(object);
        }
    }

    public static <T> void ifPresent(T object, @NotNull Runnable action) {
        if (object != null) {
            action.run();
        }
    }

    public static <T> T orElse(T object, T other) {
        return object != null ? object : other;
    }

    public static <T> Optional<T> box(boolean condition, T instance) {
        return condition ? Optional.of(instance) : Optional.empty();
    }
}
