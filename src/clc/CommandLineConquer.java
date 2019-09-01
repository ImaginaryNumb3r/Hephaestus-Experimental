package clc;

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
    private static final Path PROJECT_PATH = Path.of("/Users/p.plieschnegger/private/One-Vision/Data");

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        var program = new Project(PROJECT_PATH);
        long end = System.currentTimeMillis();

        System.out.println("Seconds: " + (end - start) / 1000d);
        System.out.println("Done Parsing!");

        System.out.println();
        var list = program.getXmlDirectory().keySet().stream()
            .sorted()
            .collect(Collectors.toList());

        System.out.println(list.get(0));

        // Test for Predator
        var predator = list.stream().filter(xml -> xml.toString().contains("ZOCOMPredator.xml")).findAny();
        Path predatorPath = predator.orElse(Path.of(""));
        XMLDocument predatorXML = XMLDocument.ofFile(predatorPath);

        Optional<XMLTag> health = Navigator.getTag(predatorXML, "AssetDeclaration", "GameObject", "Body", "ActiveBody");
        health.ifPresent(tag -> System.out.println("Predator HP: " + tag.getAttribute("MaxHealth").getValue()));

        forceRead(program.getXmlDirectory());
    }

    private static void forceRead(Map<Path, XMLDocument> xmlDirectory) throws IOException {
        VisionMapper mapper = VisionMapper.instance();
        mapper.resetCollections();

        var list = new ArrayList<GameTagEntry>();

        for (var entry : xmlDirectory.entrySet()) {
            Path absPath = entry.getKey();
            Path relPath = PROJECT_PATH.relativize(absPath);
            XMLDocument xml = entry.getValue();

            FileTime lastModifiedTime = Files.getLastModifiedTime(absPath);
            long millies = lastModifiedTime.to(TimeUnit.MILLISECONDS);
            Date date = new Date(millies);

            // Only save xml tags with ids, which are Templates.
            XMLTag tags = xml.getRoot();
            tags.children().stream()
                .filter(tag -> tag.getName().contains("Template") || tag.getName().contains("GameObject"))
                .filter(tag -> tag.hasAttributes("id"))
                .forEach(tag -> {
                    String tagName = tag.getName();
                    String id = tag.getAttribute("id").getValue();

                    GameTagEntry object = new GameTagEntry(id, date, tag, relPath, tagName);
                    list.add(object);
                }); /*

            if (list.size() > batchThreshold) {
                System.out.println("Persisting " + count + " out of " + xmls + " files");
                mapper.persist(list);
                list.clear();
            }
            ++count; */
        }

        // flush
        System.out.println("Flushing...");

        list.stream()
            .map(GameTagEntry::getCollectionName)
            .distinct()
            .forEach(mapper::drop);

        final int batchThreshold = 300;
        int count = 0;
        for (GameTagEntry entry : list) {
            mapper.commit(entry);

            if (++count % batchThreshold == 0) {
                mapper.persist();
            }
        }

        mapper.persist(list);
        System.out.println("Done!");
    }
}
