package lib;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author Patrick Plieschnegger
 */
public class Sets {

    // TODO: intersection(Collection, Collection);

    public <T> T first(Collection<T> collection) {
        return collection.iterator().next();
    }

    public static String toString(Collection<String> strings) {
        return Arrays.toString(strings.toArray());
    }
}
