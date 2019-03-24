package parsing.xml;

import parsing.model.ParseNode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static parsing.model.ParseNode.INVALID;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Purpose:
 */
public class AbstractParseNodeTest {
    protected static final Path TEST_FILE_DIR = Path.of("test", "parsing", "xml", "files");

    protected static List<String> readTestData(String string) {
        return readTestData(Path.of(string));
    }

    protected static List<String> readTestData(Path fileName) {
        ArrayList<String> samples = new ArrayList<>();
        Path filePath = TEST_FILE_DIR.resolve(fileName);

        try (var reader = new BufferedReader(new FileReader(filePath.toFile()))){
            String xml = "";

            String line;
            while ( (line = reader.readLine()) != null ) {
                if (line.isBlank()) {
                    if (!xml.isBlank()) {
                        samples.add(xml);
                    }
                    xml = "";
                } else {
                    if (!xml.isEmpty()) {
                        xml += '\n';
                    }
                    xml += line;
                }
            }

            // Flush
            samples.add(xml);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot initialize Test: " + ClosedTagTest.class);
        }

        return samples;
    }

    protected void checkParse(String expected, String data, ParseNode token, Supplier<String> dataSupplier) {
        checkParse(expected, data, token, dataSupplier, token::toString);
    }

    protected void checkParse(String expected, String data, ParseNode token, Supplier<String> dataSupplier, Supplier<String> toString) {
        int parse = token.parse(data, 0);
        String message = "Asserting that the token could be parsed fails for: " + token.getClass().getName();
        assertNotEquals(message, INVALID, parse);

        message = "Comparison between parse output and expected output fails";
        assertEquals(message, expected, dataSupplier.get());

        message = "Raw comparison between parse input and output fails";
        assertEquals(message, data, toString.get());

        message = "Asserting that a copy is equal failed for: " + token.getClass().getName();
        ParseNode copy = token.deepCopy();
        assertEquals(message, token, copy);
    }
}
