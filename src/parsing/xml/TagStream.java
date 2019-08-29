package parsing.xml;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author Patrick Plieschnegger
 */
public interface TagStream extends XMLStream<XMLTag, TagStream>{

    TagStream filter(String tagName);

    TagStream filter(Predicate<XMLTag> predicate);

    AttributeStream findAttributes();

    AttributeStream findAttributes(String attributeName);

    AttributeStream findAttributes(Predicate<AttributeToken> predicate);

    Optional<XMLTag> findFirst();

    Optional<TagStream> first();

    <T> Stream<T> map(Function<XMLTag, T> mapper);

    <T> Stream<T> flatMap(Function<XMLTag, Stream<T>> mapper);

    Stream<XMLTag> stream();

}
