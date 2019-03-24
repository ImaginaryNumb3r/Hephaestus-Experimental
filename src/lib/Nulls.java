package lib;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Creator: Patrick
 * Created: 21.03.2019
 * Purpose:
 */
public final class Nulls {

    public static <T> void ifPresent(T object, Consumer<T> consumer) {
        if (object != null) {
            consumer.accept(object);
        }
    }

    public static <T> void ifPresent(T object, Runnable action) {
        if (object != null) {
            action.run();
        }
    }

    public static <T> void ifnull(T object, Runnable action) {
        if (object == null) {
            action.run();
        }
    }

    public static <T> Optional<T> box(boolean condition, T instance) {
        return condition ? Optional.of(instance) : Optional.empty();
    }

}
