package parsing.console.arguments;

import lib.Lists;
import org.junit.Test;
import parsing.console.Argument;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ValueArgumentTest {
    /**
     * Test Cases:
     *  - ok case
     *  - failure case
     *  - error case
     *  each with mandatory/optional
     */

    @Test
    public void name() {
        var argument = new ValueArgument("-key", "=", Argument.Type.MANDATORY);

        checkParseOK(Lists.of("-key=value"), argument, "value");
        checkParseOK(Lists.of("-key=data"), argument, "data");
    }

    private void checkParseOK(List<String> tokens, ValueArgument argument, String expected) {
        boolean success = argument.consume(tokens);

        assertTrue("Valid argument could not be parsed", success);
        assertEquals("Parsed value does not match with expected", expected, argument.fetchValue());
    }
}
