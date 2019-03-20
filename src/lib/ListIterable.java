package lib;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.ListIterator;

/**
 * @author Patrick Plieschnegger
 */
@FunctionalInterface
public interface ListIterable<T> extends Iterable<T> {

    @NotNull
    @Override
    default Iterator<T> iterator() {
        return listIterator();
    }

    @NotNull
    ListIterator<T> listIterator();
}
