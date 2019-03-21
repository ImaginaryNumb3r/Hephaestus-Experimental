package parsing.xml;

import org.junit.Test;

/**
 * Creator: Patrick
 * Created: 21.03.2019
 * Purpose:
 */
public class CommentTokenTest extends AbstractParseNodeTest {

    @Test
    public void testParse() {
        String comment = "<!-- comment -->";
        String expected = " comment ";
        CommentToken token = new CommentToken();

        checkParse(expected, comment, token, token::getContent);

        comment = "<!---->";
        expected = "";
        token = new CommentToken();

        checkParse(expected, comment, token, token::getContent);

        comment = "<!--a-->";
        expected = "a";
        token = new CommentToken();

        checkParse(expected, comment, token, token::getContent);
    }
}