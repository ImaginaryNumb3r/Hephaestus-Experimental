package parsing.xml;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Creator: Patrick
 * Created: 22.03.2019
 * Purpose:
 */
public class InnerNodesTest extends AbstractParseNodeTest {
    private static final List<String> TEST_DATA = readTestData("InnerNodes.xml");

    @Test
    public void testParse() {
        String comment = TEST_DATA.get(0);
        String expected = "<!-- Comment -->";
        InnerNodes nodes = new InnerNodes();

        checkParse(expected, comment, nodes, nodes::toString);

        XMLNode node = nodes.getElements().get(0);
        assertTrue(node.isComment());
    }
}