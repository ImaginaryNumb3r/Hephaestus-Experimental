package lib.argument;

import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

public class ArgumentParserTest {

    @Test
    public void testOption() {
        String validInput = "-enabled";
        var validInputExpected = Set.of("enabled");
        String invalidInput = "-invalid";
        String semiValidInput = "-invalid -extra";
        String syntacticallyWrongInput = "invalid";

        var parser = makeParser(validInput);
        Assert.assertEquals(parser.parse(validInput), validInputExpected);
        Assert.assertEquals(parser.parseStrict(validInput), validInputExpected);
    }

    private ArgumentParser makeParser(String input) {
        var disabled = Argument.asOption("disabled");
        var enabled = Argument.asOption("enabled");

        var parser = new ArgumentParser();
        parser.addArgument(disabled);
        parser.addArgument(enabled);

        return parser;
    }

}
