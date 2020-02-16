package lib.arguments;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Patrick Plieschnegger
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
