package parsing.json;

import org.junit.Test;
import parsing.model.ParseResult;

import java.util.Arrays;

import static java.lang.Integer.parseInt;
import static org.junit.Assert.*;

/**
 * Creator: Patrick
 * Created: 28.10.2019
 * Purpose:
 */
public class JNumberTest {

    @Test
    public void testInteger() {
        var values = Arrays.asList("100", "0", "0123", "0123456789", "-1", "-0");

        for (String value : values) {
            JNumber number = new JNumber();
            ParseResult result = number.parse(value, 0);
            long maxValue = Long.MAX_VALUE;

            assertTrue("Cannot parse integer", result.isValid());
            assertEquals("Parsed number is not equal", value, number.getAsString());
            assertEquals("Parsed number is not equal", parseInt(value), number.getAsInteger());
        }

        String intOverFlow = Long.toString(Long.MAX_VALUE);

        JNumber number = new JNumber();
        ParseResult result = number.parseValue(intOverFlow);

        assertTrue("Cannot parse integer", result.isValid());
        assertEquals("Parsed number is not equal", intOverFlow, number.getAsString());
        assertFalse("Should have failed to parse integer because it was too big to represent", number.isInteger());
        assertTrue("Should parsed long", number.isLong());
    }
}