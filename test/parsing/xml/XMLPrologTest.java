package parsing.xml;

import org.junit.Test;

import java.util.List;

/**
 * Creator: Patrick
 * Created: 25.03.2019
 * Purpose:
 */
public class XMLPrologTest extends AbstractParseNodeTest {
    private static final List<String> TEST_DATA = readTestData("XMLProlog.xml");

    @Test
    public void testParse() {
        for (String data : TEST_DATA) {

            var token = new XMLProlog();
            checkParse(data, data, token, token::toString);
        }
    }
}