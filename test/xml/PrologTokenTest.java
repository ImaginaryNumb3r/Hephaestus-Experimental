package xml;

import org.junit.Test;

import static org.junit.Assert.*;
import static xml.ParseNode.INVALID;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Purpose:
 */
public class PrologTokenTest {
    private static final String PROLOG = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

    @Test
    public void testToken() {
        ParseNode token = new PrologToken();
        int parse = token.parse(PROLOG, 0);

        assertNotEquals(INVALID, parse);
        assertEquals(PROLOG, token.toString());

        ParseNode copy = token.deepCopy();
        assertEquals(token, copy);
    }
}