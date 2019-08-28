package lib;

import java.util.Map;

/**
 * @author Patrick Plieschnegger
 */
public class Tuples {

    public static <K, V> Map.Entry<K, V> entryKey(K key, V value) {
        return new Map.Entry<>() {
            @Override
            public K getKey() {
                return key;
            }

            @Override
            public V getValue() {
                return value;
            }

            @Override
            public V setValue(V value) {
                throw new UnsupportedOperationException();
            }
        };
    }
}
