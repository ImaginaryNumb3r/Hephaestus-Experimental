package lib.arguments;

import java.util.Map;
import java.util.function.Supplier;

/**
 * Creator: Patrick
 * Created: 17.05.2019
 * Purpose:
 */
public class ArgumentAlreadyExistsException extends RuntimeException {

    public ArgumentAlreadyExistsException(String argumentName) {
        super("Cannot define duplicate entry of argument with name\"" + argumentName + "\".");
    }

    /*package*/ static void assertNoDuplicate(Map<String, ? extends Argument> argumentMap, String name) throws ArgumentAlreadyExistsException {
        if (argumentMap.containsKey(name)) throw new ArgumentAlreadyExistsException(name);
    }
}
