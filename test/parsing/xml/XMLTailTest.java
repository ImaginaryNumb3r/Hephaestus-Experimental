package parsing.xml;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Creator: Patrick
 * Created: 22.03.2019
 * Purpose:
 */
public class XMLTailTest extends AbstractParseNodeTest {
    public static final List<String> TEST_DATA = readTestData("XMLTail.xml");

    @Test
    public void testParse() {
        for (String tail : TEST_DATA) {
            XMLTail token = new XMLTail();

            checkParse(tail, tail, token, token::toString);
        }
    }
}