package parsing.xml;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Collections.singletonList;

/**
 * @author Patrick Plieschnegger
 */
public abstract class XMLStreamImpl<T, S extends XMLStream<T, S>> implements XMLStream<T, S> {
    protected final List<T> _tags;
    private final Function<List<T>, S> _constructor;

    protected XMLStreamImpl(List<T> tags, Function<List<T>, S> constructor) {
        _tags = tags;
        _constructor = constructor;
    }

    @Override
    public Optional<T> findFirst() {
        return _tags.isEmpty()
            ? Optional.empty()
            : Optional.of(_tags.get(0));
    }

    @Override
    public Optional<S> first() {
        if (_tags.isEmpty()) return Optional.empty();
        List<T> first = singletonList(_tags.get(0));

        return Optional.of(_constructor.apply(first));
    }
}
