package parsing.xml;

import org.junit.Test;

import java.util.List;

/**
 * Creator: Patrick
 * Created: 21.03.2019
 * Purpose:
 */
public class XMLTagTest extends AbstractParseNodeTest {
    private static final List<String> SAMPLE_DATA;

    static {
        SAMPLE_DATA = readTestData("tags.xml");

    }

    @Test
    public void name() {
        String data = SAMPLE_DATA.get(0);
        XMLTag token = new XMLTag();

        checkParse(data, data, token, token::toString);
    }
}