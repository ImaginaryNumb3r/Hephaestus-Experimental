package lib;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.ListIterator;

/**
 * @author Patrick Plieschnegger
 */
@FunctionalInterface
@Deprecated // Use from essentials collection instead.
public interface ListIterable<T> extends Iterable<T> {

    @NotNull
    @Override
    default Iterator<T> iterator() {
        return listIterator();
    }

    @NotNull
    default ListIterator<T> listIterator() {
        return listIterator(0);
    }

    @NotNull
    ListIterator<T> listIterator(int index);
}
