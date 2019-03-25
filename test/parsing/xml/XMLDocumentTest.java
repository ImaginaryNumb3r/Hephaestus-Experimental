package parsing.xml;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
        var token = new XMLDocument();

        checkParse(file, file, token, token::toString);

        System.out.println();
    }
}