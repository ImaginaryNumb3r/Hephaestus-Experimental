package lib;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BinaryOperator;

/**
 * @author Patrick Plieschnegger
 */
@Deprecated // Use Maps from hephaestus collections instead
public final class Maps {

    public static <K, V> Map<K, V> leftRecursiveJoin(Map<K, V>... primary) {
        if (primary.length == 0) return new HashMap<>(0);
        int last = primary.length - 1;

        var previous = primary[last];

        for (int i = last - 1; i >= 0; --i) {
            var curMap = primary[i];
            previous = leftJoin(curMap, previous);
        }

        return previous;
    }

    public static <K, V> Map<K, V> leftJoin(Map<K, V> primary, Map<K, V> secondary) {
        var joined = new HashMap<>(secondary);
        joined.putAll(primary);

        return joined;
    }

    public static <K, V> Map<K, V> join(Map<K, V> left, Map<K, V> right, BinaryOperator<V> resolver) {
        var joined = new HashMap<>(right);
        for (var entry : left.entrySet()) {
            K key = entry.getKey();
            V value = entry.getValue();

            V prev = joined.put(key, value);
            if (prev != null) {
                value = resolver.apply(value, prev);
                joined.put(key, value);
            }
        }

        return joined;
    }
}
