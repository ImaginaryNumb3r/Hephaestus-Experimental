package parsing.console;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Patrick Plieschnegger
 *
 * It has the following responsibilities:
 *  - Must ensure that no two arguments share a name.
 *  - Implementation dependent on whether all arguments must be parsed or if an "unrecognized option" exception is thrown.
 *
 */ // TODO: Consider making it a parameterized type.
public interface AbstractArgumentParser {

    /**
     * Needs to enforce that no duplicates may exist.
     * @param argument
     */
    void addArgument(AbstractArgument<?> argument);

    void parse(String input);

}
