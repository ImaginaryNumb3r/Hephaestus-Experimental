package lib;

import java.util.function.Consumer;

/**
 * Creator: Patrick
 * Created: 21.03.2019
 * Purpose:
 */
public class Nulls {

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
}
