package xml;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Purpose:
 */
public class AbstractParseNodeTest {
    protected static final Path TEST_FILE_DIR = Path.of("test", "xml", "files");

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
}