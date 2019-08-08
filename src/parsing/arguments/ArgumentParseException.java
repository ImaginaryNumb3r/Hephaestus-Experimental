package parsing.arguments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author Patrick Plieschnegger
 */
public class ArgumentParseException extends RuntimeException {
    private final List<String> _missing = new ArrayList<>();

    public ArgumentParseException(String message) {
        super(message);
    }

    public ArgumentParseException(Collection<String> missing) {
        super("Cannot consume arguments because there are mandatory arguments left unparsed: " + Arrays.toString(missing.toArray()));
        _missing.addAll(missing);
    }

    public ArgumentParseException(String typeName, String character) {
        super(typeName + " contains illegal character: " + character);
    }

    protected List<String> getMissing() {
        return _missing;
    }
}
