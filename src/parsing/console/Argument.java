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

    ParseStatus getStatus();

    Type getType();

    default boolean isMandatory() {
        return getType() == Type.MANDATORY;
    }

    default boolean isOptional() {
        return getType() == Type.OPTIONAL;
    }

    enum Type {
        MANDATORY, OPTIONAL
    }

    enum ParseStatus {
        SUCCESS, UNPARSED, FAIL
    }
}
