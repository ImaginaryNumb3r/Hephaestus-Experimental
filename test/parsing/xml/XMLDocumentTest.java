package parsing.xml;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Creator: Patrick
 * Created: 25.03.2019
 * Purpose:
 */
public class XMLDocumentTest extends AbstractParseNodeTest {

    @Test
    public void testComplete() {
        for (String data : readTestData("document.xml")) {
            XMLTag token = new XMLTag();

            checkParse(data, data, token, token::toString);
        }
    }
}