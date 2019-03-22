package parsing.xml;

import org.junit.Test;

import java.nio.file.Path;

import static org.junit.Assert.*;

/**
 * Creator: Patrick
 * Created: 22.03.2019
 * Purpose:
 */
public class XMLNodeTest extends AbstractParseNodeTest {

    @Test
    public void testParseComments() {
        for (TestPair testPair : CommentTokenTest.TEST_DATA) {
            XMLNode token = new XMLNode();

            checkParse(testPair.data, testPair.data, token, token::toString);
            assertTrue(token.isComment());

            assertTrue(token.getCommentContent().isPresent());
            assertEquals(testPair.expected, token.getCommentContent().get());
        }
    }
}