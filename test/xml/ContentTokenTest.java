package xml;

import org.junit.Test;

import static org.junit.Assert.*;
import static xml.ParseNode.INVALID;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Purpose:
 */
public class ContentTokenTest {

    @Test
    public void testParse() {
        String data = "=== data ! !!";
        String expected = " data ! ";
        ContentToken token = new ContentToken("===", "!!");

        int parse = token.parse(data, 0);
        assertNotEquals(INVALID, parse);

        assertEquals(expected, token.getContent());
        assertEquals(data, token.toString());

        ParseNode copy = token.deepCopy();
        assertEquals(token, copy);
    }
}