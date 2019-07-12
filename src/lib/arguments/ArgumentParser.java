package lib.arguments;

import java.util.HashMap;

/**
 * @author Patrick Plieschnegger
 * TODO: Check for duplicate entries of arguments, check strict mode.
 */
public class ArgumentParser {
    protected final HashMap<String, Argument<String>> _argumentValueMap;
    protected final HashMap<String, Argument<String[]>> _argumentArrayMap;

    public ArgumentParser(ArgumentsBuilder builder) {
        _argumentValueMap = new HashMap<>(builder._argumentValueMap);
        _argumentArrayMap = new HashMap<>(builder._argumentArrayMap);
    }
}
