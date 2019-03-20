package xml;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Purpose:
 */
public class ClosedTagTest extends AbstractParseNodeTest {
    private static final List<String> SAMPLES = readTestData("ClosedTag.xml");

    @Test
    public void testParse() {
        for (String sample : SAMPLES) {
            checkParse(sample);
        }
    }

    private void checkParse(String xml) {
        ClosedTag closedTag = new ClosedTag();
        int parse = closedTag.parse(xml, 0);
        Assert.assertNotEquals(parse, ParseNode.INVALID);

        String string = closedTag.toString();
        assertEquals(xml, string);
    }

    @Test
    public void testCopy() {
        for (String sample : SAMPLES) {
            checkCopy(sample);
        }
    }

    public void checkCopy(String xml) {
        ClosedTag closedTag = new ClosedTag();
        int parse = closedTag.parse(xml, 0);
        Assert.assertNotEquals(parse, ParseNode.INVALID);

        ClosedTag copy = closedTag.deepCopy();
        String s = closedTag.toString();
        String s1 = copy.toString();

        assertEquals(closedTag, copy);
    }
}
