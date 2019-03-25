package parsing.xml;

import org.junit.Test;

import java.util.List;

/**
 * Creator: Patrick
 * Created: 24.03.2019
 * Purpose:
 */
public class XMLTextTest extends AbstractParseNodeTest {
    private static final List<String> TEST_DATA = readTestData("xmlText.xml");

    @Test
    public void testParse() {
        for (String text : TEST_DATA) {

            XMLText token = new XMLText();
            String expected = text.substring(0, text.length() - 1);
            checkParse(expected, text, expected.length(), token, token::toString, token::getRaw);
        }
    }
}