package parsing.xml;

import org.junit.Assert;
import org.junit.Test;
import parsing.model.ParseNode;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Purpose:
 */
public class TagCloseTest extends AbstractParseNodeTest {
    private static final List<String> SAMPLES = readTestData("ClosedTag.xml");

    @Test
    public void testParse() {
        for (String sample : SAMPLES) {
            checkParse(sample);
        }
    }

    private void checkParse(String xml) {
        TagClose tagClose = new TagClose();
        int parse = tagClose.parse(xml, 0);
        Assert.assertNotEquals(parse, ParseNode.INVALID);

        String string = tagClose.toString();
        assertEquals(xml, string);
    }

    @Test
    public void testCopy() {
        for (String sample : SAMPLES) {
            checkCopy(sample);
        }
    }

    public void checkCopy(String xml) {
        TagClose tagClose = new TagClose();
        int parse = tagClose.parse(xml, 0);
        Assert.assertNotEquals(parse, ParseNode.INVALID);

        TagClose copy = tagClose.deepCopy();

        assertEquals(tagClose, copy);
    }
}
