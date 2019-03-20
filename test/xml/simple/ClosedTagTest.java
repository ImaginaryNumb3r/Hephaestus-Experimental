package xml.simple;

import org.junit.Assert;
import org.junit.Test;

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
            "        third=\"lorem ipsum\" />";

    @Test
    public void testParse() {
        ClosedTag closedTag = new ClosedTag();
        int parse = closedTag.parse(XML.toCharArray(), 0);
        Assert.assertNotEquals(parse, INVALID);


        String string = closedTag.toString();
        System.out.println(string);
    }
}