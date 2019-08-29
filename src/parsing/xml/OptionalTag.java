package parsing.xml;

import java.util.Optional;
import java.util.function.Function;

/**
 * @author Patrick Plieschnegger
 */
public class OptionalTag implements OptionalXML<XMLTag> {
    private final Optional<XMLTag> _tag;

    public OptionalTag(Optional<XMLTag> attribute) {_tag = attribute;}

    public Optional<XMLTag> get() {
        return _tag;
    }

    public Optional<String> getName() {
        return _tag.map(XMLTag::getName);
    }

    public Optional<AttributeToken> getAttributeToken(String attributeName) {
        return _tag.map(tag -> tag.getAttribute(attributeName));
    }

    public <T> Optional<T> map(Function<XMLTag, T> mapper) {
        return _tag.map(mapper);
    }

    public Optional<String> getAttribute(String keyName) {
        return getAttributeToken(keyName).map(AttributeToken::getValue);
    }

}
