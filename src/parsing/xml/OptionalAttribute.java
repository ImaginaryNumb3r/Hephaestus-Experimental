package parsing.xml;

import java.util.Optional;

/**
 * @author Patrick Plieschnegger
 */
public class OptionalAttribute implements OptionalXML<AttributeToken> {
    private final Optional<AttributeToken> _attribute;

    public OptionalAttribute(Optional<AttributeToken> attribute) {_attribute = attribute;}

    public Optional<AttributeToken> get() {
        return _attribute;
    }

    public Optional<String> getName() {
        return _attribute.map(AttributeToken::getName);
    }

    public Optional<String> getValue() {
        return _attribute.map(AttributeToken::getValue);
    }
}
