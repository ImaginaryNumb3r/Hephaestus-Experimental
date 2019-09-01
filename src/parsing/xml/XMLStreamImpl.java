package parsing.xml;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author Patrick Plieschnegger
 */
public abstract class XMLStreamImpl<T, Stream extends XMLStream<T, Stream>> implements XMLStream<T, Stream> {
    protected final List<T> _values;
    protected final XMLStreamImpl<T, Stream> _parent;
    private final BiFunction<List<T>, XMLStreamImpl<T, Stream>, Stream> _constructor;

    protected XMLStreamImpl(List<T> tags,
                            BiFunction<List<T>, XMLStreamImpl<T, Stream>, Stream> constructor,
                            XMLStreamImpl<T, Stream> parent
    ) {
        _values = tags;
        _constructor = constructor;
        _parent = parent;
    }

    @Override
    public Stream limit(int count) {
        List<T> limited = _values.stream()
            .limit(count)
            .collect(Collectors.toList());

        return _constructor.apply(limited, this);
    }

    @Override
    public abstract Stream filter(String name);

    @Override
    public abstract Stream filter(Predicate<T> predicate);

    @Override
    public <R, A> R collect(Collector<? super T, A, R> collector) {
        return _values.stream().collect(collector);
    }

    @Override
    public Object[] toArray() {
        return _values.toArray();
    }

    @Override
    public T[] toArray(IntFunction<T[]> arrayConstructor) {
        return _values.toArray(arrayConstructor);
    }
}
