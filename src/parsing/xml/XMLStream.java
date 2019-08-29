package parsing.xml;

import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Collector;

/**
 * @author Patrick Plieschnegger
 */
public interface XMLStream<T, Stream extends XMLStream<T, Stream>> {

    Stream filter(String name);

    Stream filter(Predicate<T> predicate);

    /**
     * @return a java.lang.Optional of the aggregated type.
     */
    OptionalXML<T> findFirst();

    Stream limit(int count);

    <R, A> R collect(Collector<? super T, A, R> collector);

    Object[] toArray();

    T[] toArray(IntFunction<T[]> arrayConstructor);

    static TagStream of(XMLTag tag) {
        return tag.stream();
    }
}
