package parsing.xml;

import java.util.List;

/**
 * @author Patrick Plieschnegger
 */
public class AttributesStreamImpl extends XMLStreamImpl<AttributeToken, AttributeStream>
        implements AttributeStream {

    public AttributesStreamImpl(List<AttributeToken> attributes) {
        super(attributes, AttributesStreamImpl::new);
    }
}
