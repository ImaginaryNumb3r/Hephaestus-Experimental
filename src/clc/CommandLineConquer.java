package clc;

import clc.domain.Project;
import clc.infrastructure.GameTagEntry;
import clc.infrastructure.VisionMapper;
import parsing.xml.Navigator;
import parsing.xml.XMLDocument;
import parsing.xml.XMLTag;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static clc.domain.Communicator.forceUpdate;
import static clc.domain.Project.PROJECT_PATH;

/**
 * Data Driven One Vision:
 *  - Name
 *  - Document
 *  - Hash
 *  - Timestamp
 *
 * TODO: Command which triggers a re-index.
 * What I cannot save:
 *  - Path
 *
 */
public class CommandLineConquer {

    public static void main(String[] args) throws IOException {
        // Parse all XMLs in path.
        long start = System.currentTimeMillis();
        var program = new Project(PROJECT_PATH);
        program.initialize();
        long end = System.currentTimeMillis();

        System.out.println("Seconds: " + (end - start) / 1000d);
        System.out.println("Done Parsing!");

        System.out.println();
        var list = program.getXmlDirectory().keySet().stream()
            .sorted()
            .collect(Collectors.toList());

        /*
        var predator = list.stream().filter(xml -> xml.toString().contains("ZOCOMPredator.xml")).findAny();
        Path predatorPath = predator.orElse(Path.of(""));
        XMLDocument predatorXML = XMLDocument.ofFile(predatorPath);

        Optional<XMLTag> health = Navigator.getTag(predatorXML, "AssetDeclaration", "GameObject", "Body", "ActiveBody");
        health.ifPresent(tag -> System.out.println("Predator HP: " + tag.getAttribute("MaxHealth").getValue())); */

        forceUpdate(program.getXmlDirectory());
        end = System.currentTimeMillis();
        System.out.println("Total time: " + (end - start) / 1000d);
    }
}
