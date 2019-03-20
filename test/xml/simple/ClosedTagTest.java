package xml.simple;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static xml.simple.ParseNode.INVALID;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Purpose:
 */
public class ClosedTagTest {
    private static final String XML = "<attributes\n" +
            "        first = \"true\"\n" +
            "        second=\"1.5s\"\n" +
            "        empty =\"\"\n" +
            "        third= \"lorem ipsum\" />";

    @Test
    public void testParse() {
        ClosedTag closedTag = new ClosedTag();
        int parse = closedTag.parse(XML, 0);
        Assert.assertNotEquals(parse, INVALID);

        String string = closedTag.toString();
        assertEquals(XML, string);
    }

    @Test
    public void testCopy() {
        ClosedTag closedTag = new ClosedTag();
        int parse = closedTag.parse(XML, 0);
        Assert.assertNotEquals(parse, INVALID);

        ClosedTag copy = closedTag.deepCopy();
        System.out.println(closedTag);
        System.out.println();
        System.out.println(copy);

        assertEquals(closedTag.toString(), copy.toString());
    }
}
