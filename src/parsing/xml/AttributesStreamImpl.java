package parsing.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * @author Patrick Plieschnegger
 */
public class AttributesStreamImpl extends XMLStreamImpl<AttributeToken, AttributeStream>
        implements AttributeStream {

    /*package*/ AttributesStreamImpl(List<AttributeToken> attributes, XMLStreamImpl<AttributeToken, AttributeStream> parent) {
        super(attributes, AttributesStreamImpl::new, parent);
    }

    /*package*/ AttributesStreamImpl(List<AttributeToken> attributes) {
        this(attributes, null);
    }

    @Override
    public AttributeStream filter(String name) {
        return filter(attr -> attr.getName().equals(name));
    }

    @Override
    public AttributeStream filter(Predicate<AttributeToken> predicate) {
        var filtered = new ArrayList<AttributeToken>();

        for (AttributeToken value : _values) {
            if (predicate.test(value)) {
                filtered.add(value);
            }
        }

        return new AttributesStreamImpl(filtered);
    }

    @Override
    public OptionalAttribute findFirst() {
        Optional<AttributeToken> attribute = _values.isEmpty()
            ? Optional.empty()
            : Optional.of(_values.get(0));

        return new OptionalAttribute(attribute);
    }

    @Override
    public Optional<String> getValue() {
        return Optional.empty();
    }
}
