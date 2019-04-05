package lib;

import java.util.function.Function;

/**
 * Creator: Patrick
 * Created: 05.04.2019
 * Purpose:
 */
@FunctionalInterface
public interface Broadcast<T> extends Function<T, Iterable<T>> {

}
