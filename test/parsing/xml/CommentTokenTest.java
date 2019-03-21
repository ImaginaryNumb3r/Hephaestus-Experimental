package parsing.xml;

import org.junit.Test;

/**
 * Creator: Patrick
 * Created: 21.03.2019
 * Purpose:
 */
public class CommentTokenTest extends AbstractParseNodeTest {
    private static final String COMMENT = "<!-- comment -->";

    @Test
    public void testParse() {
        CommentToken token = new CommentToken();
        String expected = " comment ";

        checkParse(expected, COMMENT, token, token::getContent);
    }
}