package parsing.json;

import org.junit.Test;
import parsing.model.ParseResult;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Creator: Patrick
 * Created: 09.11.2019
 * Purpose:
 */
public class JAttributeTest {

    @Test
    public void testParse() {
        var attributes = Arrays.asList("\"useJSP\": false", "\"useDataStore\": true");

        for (String attributeStr : attributes) {
            JAttribute token = new JAttribute();
            ParseResult result = token.parse(attributeStr, 0);

            assertTrue(result.isValid());
            assertEquals(attributeStr, token.toString());
        }
    }
}