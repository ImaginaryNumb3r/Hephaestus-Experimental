package parsing.arguments;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Patrick Plieschnegger
 *
 * It has the following responsibilities:
 *  - Must ensure that no two arguments share a name.
 *  - Implementation dependent on whether all arguments must be parsed or not.
 *
 */ // TODO: Consider making it a parameterized type.
public class ArgumentParser {
    private final Set<Argument<?>> _arguments;

    public ArgumentParser() {
        _arguments = new HashSet<>();
    }

    public void addArgument(Argument<?> argument) {
        _arguments.add(argument);
    }

}
