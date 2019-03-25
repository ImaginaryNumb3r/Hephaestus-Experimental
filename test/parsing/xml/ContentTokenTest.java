package parsing.xml;

import org.junit.Test;
import parsing.model.ContentToken;
import parsing.model.ParseNode;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static parsing.model.ParseNode.INVALID;

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

        checkParse(expected, data, token);

        data = "=== data ! #%&";
        expected = " data ! ";
        token = new ContentToken("===", "#%&");

        checkParse(expected, data, token);

        data = " data />";
        expected = " data ";
        token = new ContentToken("", "/>");

        checkParse(expected, data, token);
    }

    public void checkParse(String expected, String data, ContentToken token) {

        int parse = token.parse(data, 0);
        assertNotEquals(INVALID, parse);

        assertEquals(expected, token.getContent());
        assertEquals(data, token.toString());

        ParseNode copy = token.deepCopy();
        assertEquals(token, copy);
    }
}