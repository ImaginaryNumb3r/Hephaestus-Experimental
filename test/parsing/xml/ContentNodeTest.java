package parsing.xml;

import org.junit.Test;
import parsing.model.ContentNode;
import parsing.model.ParseNode;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Purpose:
 */
public class ContentNodeTest {

    @Test
    public void testParse() {
        String data = "=== data ! !!";
        String expected = " data ! ";
        ContentNode token = new ContentNode("===", "!!");

        checkParse(expected, data, token);

        data = "=== data ! #%&";
        expected = " data ! ";
        token = new ContentNode("===", "#%&");

        checkParse(expected, data, token);

        data = " data />";
        expected = " data ";
        token = new ContentNode("", "/>");

        checkParse(expected, data, token);
    }

    public void checkParse(String expected, String data, ContentNode token) {

        var result = token.parse(data, 0);
        assertTrue(result.isValid());

        assertEquals(expected, token.getContent());
        assertEquals(data, token.toString());

        ParseNode copy = token.deepCopy();
        assertEquals(token, copy);
    }
}
