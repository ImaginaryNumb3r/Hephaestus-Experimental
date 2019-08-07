package lib.arguments;

import java.util.List;

/**
 * @author Patrick Plieschnegger
 * @param T return type
 */
public interface Argument<T> {

    List<String> names();

    void setValue(T value);

    T getValue();

    String getDescription();

    void setDescription(String description);

}
