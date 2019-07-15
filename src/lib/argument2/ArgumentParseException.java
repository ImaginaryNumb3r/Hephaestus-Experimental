package lib.argument2;

/**
 * @author Patrick Plieschnegger
 */
public class ArgumentParseException extends RuntimeException {

    public ArgumentParseException(String message) {
        super(message);
    }
    public ArgumentParseException(String typeName, String character) {
        super(typeName + " contains illegal character: " + character);
    }
}
