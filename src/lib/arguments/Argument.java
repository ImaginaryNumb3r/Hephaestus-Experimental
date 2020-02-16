package lib.arguments;

import java.util.List;
import java.util.Set;

/**
 * @author Patrick Plieschnegger
 * @param T return type
 */
public interface Argument<T> {

    Set<String> names();

    void setValue(T value);

    T getValue();

    String getDescription();

    void setDescription(String description);

}
