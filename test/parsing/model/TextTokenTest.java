package parsing.model;

import org.junit.Test;
import parsing.xml.AbstractParseNodeTest;

import java.util.Arrays;
import java.util.List;

/**
 * Creator: Patrick
 * Created: 30.03.2019
 * Purpose:
 */
public class TextTokenTest extends AbstractParseNodeTest {
    private static final List<String> TEST_DATA = Arrays.asList("Text1Number_Underscore ");

    @Test
    public void testParse() {
        for (String data : TEST_DATA) {
            TextToken token = new TextToken();

            checkParse(data.trim(), data, data.length() - 1, token, token::toString, () -> token.toString() + " ");
        }
    }
}
