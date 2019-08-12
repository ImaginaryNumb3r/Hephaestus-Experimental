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

    Status getStatus();

    enum Type {
        MANDATORY, OPTIONAL
    }

    enum Status {
        PARSED, UNPARSED, ERROR
    }
}
