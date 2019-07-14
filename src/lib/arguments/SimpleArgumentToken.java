package lib.arguments;

import graphs.abstract_machine.StringParserBuilder;

import java.util.List;
import java.util.function.Function;

/**
 * @author Patrick Plieschnegger
 */
public class SimpleArgument extends Argument {
    public SimpleArgument(String name, Function<List<String>, String> validator) {
        super(name, validator);
    }

    @Override
    protected List<String> parseToken() {
        var valueBuilder = new StringBuilder();

        var builder = new StringParserBuilder<>(Status.START);
        builder.addSoftTransition(Status.START, Status.NAME, ch -> ch.equals('-'));
        builder.addReflection(Status.NAME, Character::isAlphabetic, (ch, buff) -> buff.append(ch));
        builder.addSoftTransition(Status.NAME, Status.EQUALS, ch -> ch.equals('='));
        builder.addSoftTransition(Status.EQUALS, Status.EQUALS, ch -> ch.equals('='));

        return null;
    }

    private enum Status {
        START, NAME, EQUALS
    }

}
