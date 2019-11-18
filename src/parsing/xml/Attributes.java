package parsing.xml;

import java.util.OptionalDouble;

import static java.lang.Double.parseDouble;

/**
 * @author Patrick Plieschnegger
 */
public class Attributes {

    public static double getDouble(XMLTag tag, String attributeName) {
        return fetchDouble(tag, attributeName).orElseThrow();
    }

    public static OptionalDouble fetchDouble(XMLTag tag, String attributeName) {
        AttributeToken token = tag.getAttribute(attributeName);
        return fetchDouble(token);
    }

    public static double getDouble(AttributeToken token) {
        return parseDouble(token.getValue());
    }

    public static OptionalDouble fetchDouble(AttributeToken token) {
        try {
            double value = parseDouble(token.getValue());
            return OptionalDouble.of(value);
        } catch (NumberFormatException ex) {
            return OptionalDouble.empty();
        }
    }
}
