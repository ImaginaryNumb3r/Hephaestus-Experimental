package parsing.xml;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static parsing.xml.AbstractParseNodeTest.TEST_FILE_DIR;

/**
 * Creator: Patrick
 * Created: 05.04.2019
 * Purpose:
 */
public class NavigatorTest {

    @Test
    public void testNavigation() {
        try {
            var list = Arrays.asList("1", "2");
            var iter1 = list.iterator();
            var iter2 = list.iterator();

            boolean eq = iter1.equals(iter2);
            boolean b = iter1.hasNext();
            boolean b1 = iter2.hasNext();


            String expectedTag = "ArmorSet";
            Path documentPath = TEST_FILE_DIR.resolve("document.xml");

            XMLDocument document = XMLDocument.ofFile(documentPath);
            // Optional<XMLTag> tag = Navigator.getTag(document, "AssetDeclaration", "GameObject", expectedTag);

            // assertEquals(expectedTag, tag.get().getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
