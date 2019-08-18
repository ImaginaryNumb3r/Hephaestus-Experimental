package parsing.console;

import java.util.Optional;
import java.util.Set;

/**
 * @param <T> return type
 */
public interface Argument<T> {

    Set<String> names();

    String getDescription();

    T fetchValue();

    default Optional<T> getValue() {
        return Optional.ofNullable(fetchValue());
    }

    default boolean hasValue() {
        return getValue().isPresent();
    }

    Type getType();

    ParseStatus getStatus();

    enum Type {
        MANDATORY, OPTIONAL
    }

    enum ParseStatus {
        SUCCESS, UNPARSED, FAIL
    }
}
