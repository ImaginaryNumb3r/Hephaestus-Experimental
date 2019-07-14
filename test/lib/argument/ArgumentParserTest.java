package lib.argument;

import org.junit.Test;

import java.util.Set;

import static java.util.Collections.emptySet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ArgumentParserTest {

    @Test
    public void testOption() {
        String validInput = "-enabled";
        var validInputExpected = Set.of("enabled");
        String invalidInput = "-invalid";
        String semiValidInput = "-enabled -extra";
        String syntacticallyWrongInput = "invalid";

        var parser = makeParser();
        assertEquals(parser.parse(validInput), validInputExpected);
        assertEquals(parser.parseStrict(validInput), validInputExpected);

        assertEquals(parser.parse(invalidInput), emptySet());
        assertThrow(IllegalStateException.class, () -> parser.parseStrict(invalidInput));

        assertEquals(parser.parse(semiValidInput), validInputExpected);
        assertThrow(IllegalStateException.class, () -> parser.parseStrict(semiValidInput));

        assertEquals(parser.parse(syntacticallyWrongInput), emptySet());
        assertThrow(IllegalStateException.class, () -> parser.parseStrict(syntacticallyWrongInput));
    }

    private void assertThrow(Class<? extends Exception> exceptionClass, Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception ex) {
            var exClass = ex.getClass();
            assertTrue(exClass.isAssignableFrom(exceptionClass));
        }
    }

    private ArgumentParser makeParser() {
        var disabled = Argument.asOption("disabled");
        var enabled = Argument.asOption("enabled");

        var parser = new ArgumentParser();
        parser.addArgument(disabled);
        parser.addArgument(enabled);

        return parser;
    }

}
