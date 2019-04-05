package parsing.xml;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
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
            String expectedTag = "ArmorSet";
            Navigator navigator = new Navigator();
            Path documentPath = TEST_FILE_DIR.resolve("document.xml");

            XMLDocument document = XMLDocument.ofFile(documentPath);
            Optional<XMLTag> tag = navigator.getTag(document, "AssetDeclaration", "GameObject", expectedTag);

            assertEquals(expectedTag, tag.get().getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}