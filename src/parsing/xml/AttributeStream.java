package parsing.xml;

import java.util.Optional;

/**
 * @author Patrick Plieschnegger
 */
public interface AttributeStream extends XMLStream<AttributeToken, AttributeStream> {

    @Override
    OptionalAttribute findFirst();

    Optional<String> getValue();

}
