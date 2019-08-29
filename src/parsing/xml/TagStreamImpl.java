package parsing.xml;

import essentials.functional.Predicates;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author Patrick Plieschnegger
 */
/*package*/ class TagStreamImpl extends XMLStreamImpl<XMLTag, TagStream> implements TagStream {

    /*package*/ TagStreamImpl(List<XMLTag> tags) {
        super(tags, TagStreamImpl::new);
    }

    @Override
    public TagStream filter(String name) {
        return filter(tag -> tag.getName().equals(name));
    }

    @Override
    public TagStream filter(Predicate<XMLTag> predicate) {
        var filtered = new ArrayList<XMLTag>();

        for (XMLTag xmlTag : _values) {
            if (predicate.test(xmlTag)) {
                filtered.add(xmlTag);
            }
        }

        return new TagStreamImpl(filtered);
    }

    @Override
    public AttributesStreamImpl findAttributes() {
        return findAttributes(Predicates::alwaysTrue);
    }

    @Override
    public AttributesStreamImpl findAttributes(String attributeName) {
        return findAttributes(attr -> attr.getName().equals(attributeName));
    }

    @Override
    public AttributesStreamImpl findAttributes(Predicate<AttributeToken> predicate) {
        var attributes = new ArrayList<AttributeToken>();

        for (XMLTag tag : _values) {
            for (AttributeToken attribute : tag.attributes()) {
                if (predicate.test(attribute)) {
                    attributes.add(attribute);
                }
            }
        }

        return new AttributesStreamImpl(attributes);
    }

    @Override
    public OptionalTag findFirst() {
        Optional<XMLTag> xmlTag = _values.isEmpty()
            ? Optional.empty()
            : Optional.of(_values.get(0));

        return new OptionalTag(xmlTag);
    }

    @Override
    public <T> Stream<T> map(Function<XMLTag, T> mapper) {
        return _values.stream().map(mapper);
    }

    @Override
    public <T> Stream<T> flatMap(Function<XMLTag, Stream<T>> mapper) {
        return _values.stream().flatMap(mapper);
    }

    @Override
    public Stream<XMLTag> stream() {
        return _values.stream();
    }
}
