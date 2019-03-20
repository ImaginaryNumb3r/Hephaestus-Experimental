package parsing.xml;

import org.junit.Test;
import parsing.xml.model.ContentToken;
import parsing.xml.model.ParseNode;

import static org.junit.Assert.*;
import static parsing.xml.model.ParseNode.INVALID;

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