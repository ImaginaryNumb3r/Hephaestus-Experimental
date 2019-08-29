package parsing.xml;

import java.util.Optional;

/**
 * @author Patrick Plieschnegger
 */
public interface XMLStream<T, S extends XMLStream<T, S>> {

    Optional<T> findFirst();

    public Optional<S> first();

}
