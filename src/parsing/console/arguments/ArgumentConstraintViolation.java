package parsing.console.arguments;

import parsing.console.ArgumentParseException;

/**
 * @author Patrick Plieschnegger
 */
public class ArgumentConstraintViolation extends ArgumentParseException {

    public ArgumentConstraintViolation(String message) {
        super(message);
    }
}
