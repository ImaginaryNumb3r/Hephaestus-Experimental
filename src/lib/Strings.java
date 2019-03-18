package lib;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Patrick Plieschnegger
 */
@SuppressWarnings("StringConcatenationInLoop")
public class Strings {

    public static String toString(Object object, String fallback) {
        return object != null ? object.toString() : fallback;
    }

    public static String concat(Object... objects) {
        return concat(Arrays.asList(objects));
    }

    public static String concat(Iterable<?> objects) {
        return joinToString(objects, "");
    }

    public static String joinToString(Iterable<?> objects, String separator) {
        var strings = new ArrayList<String>();
        objects.forEach(obj -> strings.add(obj.toString()));

        return join(strings, separator);
    }

    public static String join(Iterable<String> strings, String separator) {
        String result = "";

        var iter = strings.iterator();
        while (iter.hasNext()) {
            String string = iter.next();
            result += string;

            if (iter.hasNext()) {
                result += separator;
            }
        }

        return result;
    }
}
