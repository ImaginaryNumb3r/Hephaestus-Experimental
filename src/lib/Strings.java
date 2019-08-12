package lib;

import org.jetbrains.annotations.NotNull;

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

    /**
     * Derives the string representation of the provided objects and concatenates them.
     * @param objects using toString to derive their string representation.
     * @return joined string of all objects (via string representation).
     */
    public static String concat(Object... objects) {
        return concat(Arrays.asList(objects));
    }

    /**
     * Derives the string representation of the provided objects and concatenates them.
     * @param objects using toString to derive their string representation.
     * @return joined string of all objects (via string representation).
     */
    public static String concat(Iterable<?> objects) {
        return joinToString(objects, "");
    }

    /**
     * Joins the string representation of the provided objects, separated by a delimiter.
     *
     * @param objects using toString to derive their string representation.
     * @param delimiter the value between the objects.
     * @return joined string of objects (via string representation), separated by a delimiter.
     */
    public static String joinToString(@NotNull Iterable<?> objects, @NotNull String delimiter) {
        var strings = new ArrayList<String>();
        objects.forEach(obj -> strings.add(obj.toString()));

        return join(strings, delimiter);
    }

    /**
     * Joins the strings, each separated by a delimiter.
     *
     * @param strings which are joined
     * @param delimiter the value between the objects.
     * @return joined string of all strings, separated by delimiters.
     */
    public static String join(@NotNull Iterable<? extends CharSequence> strings, @NotNull CharSequence delimiter) {
        String result = "";

        var iter = strings.iterator();
        while (iter.hasNext()) {
            CharSequence string = iter.next();
            result += string;

            if (iter.hasNext()) {
                result += delimiter;
            }
        }

        return result;
    }
}
