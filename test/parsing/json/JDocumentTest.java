package parsing.json;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.Assert.*;

/**
 * Creator: Patrick
 * Created: 28.10.2019
 * Purpose:
 */
public class JDocumentTest {
    public static final Path TEST_FILE_DIR = Path.of("test", "parsing", "json", "files");

    @Test
    public void testParse() throws IOException {
        Path filePath = TEST_FILE_DIR.resolve("sample1.json");
        JDocument jsonDocument = JDocument.ofFile(filePath);

        assertNotNull("Document could not be parsed!", jsonDocument);
    }
}
