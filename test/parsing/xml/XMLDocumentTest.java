package parsing.xml;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Creator: Patrick
 * Created: 25.03.2019
 * Purpose:
 */
public class XMLDocumentTest extends AbstractParseNodeTest {

    @Test
    public void testComplete() throws IOException {
        Path testFile = TEST_FILE_DIR.resolve("document.xml");
        String file = Files.readString(testFile);
        var document = new XMLDocument();

        checkParse(file, file, document, document::toString);

        XMLTag root = document.getRoot();
        assertEquals(root.getName(), "AssetDeclaration");

        assertEquals(1, root.children().size());
        var gameObject = root.children().get(0);

        assertEquals(gameObject.getName(), "GameObject");

        var attributes = gameObject.attributes();
        var optionalId = attributes.stream()
                .filter(attribute -> attribute.getName().equals("id"))
                .findFirst();

        assertTrue("Failed to assert that an attribute \"id\" exists", optionalId.isPresent());
        var idAttribute = optionalId.get();

        assertEquals(idAttribute.getName(), "id");
        assertEquals(idAttribute.getValue(), "BlackHandConfessorSquad");

        System.out.println();
    }
}