package parsing.console.arguments;

import org.junit.Test;
import parsing.console.Argument;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ValueArgumentTest {

    @Test
    public void testParse() {
        // TODO: Make negative test cases.
        var arguments = asList(
            new TestCase("-key", "=", Argument.Type.MANDATORY, "value", "-key=value"),
            new TestCase("-key", "=", Argument.Type.OPTIONAL, "value","-key=value"),
            new TestCase("-", "=", Argument.Type.OPTIONAL, "value","-=value"),
            new TestCase("-", "=", Argument.Type.MANDATORY, "value","-=value"),

            // Test with single, non-alphanumerical char.
            new TestCase("-", "=", Argument.Type.OPTIONAL, "value","-=value"),
            new TestCase("-", "=", Argument.Type.MANDATORY, "value","-=value"),

            // Test with multi character delimiter.
            new TestCase("-key", "===", Argument.Type.OPTIONAL, "value","-key===value"),
            new TestCase("-key", "===", Argument.Type.MANDATORY, "value","-key===value")
        );

        for (var test : arguments) {
            checkParseOK(test.tokens, test.argument, test.expected);
        }
    }

    private void checkParseOK(List<String> tokens, ValueArgument argument, String expected) {
        boolean success = argument.consume(tokens);

        assertTrue("Valid argument could not be parsed", success);
        assertEquals("Parsed value does not match with expected", expected, argument.fetchValue());
        assertEquals("Token list not consumed", 0, tokens.size());
    }/*

    private void checkParseFail(List<String> tokens, ValueArgument argument) {
        boolean success = argument.consume(tokens);

        assertTrue("Valid argument could not be parsed", success);
        assertEquals("Parsed value does not match with expected", expected, argument.fetchValue());
        assertEquals("Token list not consumed", 0, tokens.size());
    }*/


    private final class TestCase {
        private final ValueArgument argument;
        private final List<String> tokens;
        private final String expected;

        private TestCase(String name, String delimiter, Argument.Type type, String expected, String... token) {
            this.expected = expected;
            this.argument = new ValueArgument(name, delimiter, type);
            tokens = new ArrayList<>();
            tokens.addAll(asList(token));
        }
    }
}
