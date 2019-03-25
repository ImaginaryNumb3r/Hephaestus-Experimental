package parsing.xml;

import org.junit.Test;

import java.util.List;
import java.util.function.Supplier;

import static org.junit.Assert.assertTrue;

/**
 * Creator: Patrick
 * Created: 22.03.2019
 * Purpose:
 */
@SuppressWarnings("OptionalGetWithoutIsPresent")
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

        String string = TEST_DATA.get(1);
        expected = "Text aoiweu!\"30912 <!-- -->";
        var innerNodes = new InnerNodes();
        Supplier<String> actual = () -> innerNodes.getData().get() + "</";
        checkParse(expected, string, expected.length(), innerNodes, () -> innerNodes.getData().get(), actual);

        string = TEST_DATA.get(2);
        expected = string;
        nodes = new InnerNodes();
        checkParse(expected, string, nodes, nodes::toString);
    }
}