package parsing.console.arguments;

import parsing.console.ArgumentParseException;

/**
 * @author Patrick Plieschnegger
 */
public class ParseConstraintViolation extends ArgumentParseException {

    public ParseConstraintViolation(String message) {
        super(message);
    }
}
