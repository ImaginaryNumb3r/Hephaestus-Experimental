package parsing.console.arguments;

import parsing.console.common.ArgumentParseException;

/**
 * @author Patrick Plieschnegger
 */
public class ParseConstraintViolation extends ArgumentParseException {

    public ParseConstraintViolation(String message) {
        super(message);
    }
}
