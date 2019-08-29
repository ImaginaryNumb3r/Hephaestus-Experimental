import lib.io.ForkJoinXML;
import parsing.xml.AttributeToken;
import parsing.xml.Navigator;
import parsing.xml.XMLDocument;
import parsing.xml.XMLTag;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;

/**
 * @author Patrick Plieschnegger
 */
public class CommandLineConquer {
    private final Map<Path, XMLDocument> _xmlDirectory = new HashMap<>();
    private final Path _projectPath;

    public CommandLineConquer(Path projectPath) throws IOException {
        _projectPath = projectPath;
        Path staticPath = projectPath.resolve("Static.xml");

        var staticXML = XMLDocument.ofFile(staticPath);
        var includeFiles = new ArrayDeque<XMLDocument>();
        includeFiles.add(staticXML);

        // TODO: You could also say this is the "check config" step.
        // Remove from queue.
        while (!includeFiles.isEmpty()) {
            XMLDocument xml = includeFiles.pop();

            var includePath = singletonList("AssetDeclaration");
            Optional<XMLTag> includeTag = Navigator.getTag(xml, includePath);

            if (includeTag.isPresent()) {
                XMLTag includes = includeTag.get();

                for (XMLTag include : includes.children()) {
                    if (!include.hasAttributes("source")) break;

                    // TODO: Ignore xmls which are named like the containing directory.

                    AttributeToken source = include.getAttribute("source");
                    AttributeToken typeAttr = include.getAttribute("type");

                    String relLocation = source.getValue();
                    String type = typeAttr.getValue();

                    // Ignore art xmls and other special case xmls.
                    if (type.equalsIgnoreCase("all") && !relLocation.startsWith("ART:")) {
                        var xmlPath = projectPath.resolve(relLocation);

                        try {
                            var xmlDocument = XMLDocument.ofFile(xmlPath);

                            if (xmlDocument == null) {
                                System.out.println("Cannot parse: " + xmlPath);
                                xmlDocument = XMLDocument.ofFile(xmlPath);
                            }

                            // Add to queue.
                            includeFiles.add(xmlDocument);
                            _xmlDirectory.put(xmlPath, xmlDocument);
                        } catch (NoSuchFileException ex) {
                            System.out.println("No such file: " + xmlPath);
                        }
                        // System.out.println("Added: " + xmlPath);
                    }
                }
            }
        }
    }

    private void parseDirectory(String directory) {
        Path path = _projectPath.resolve(directory);
        var pool = new ForkJoinPool(8);

        ForkJoinXML rootTask = new ForkJoinXML(path, XMLDocument::ofFile);
        pool.execute(rootTask);

        _xmlDirectory.putAll(rootTask.join());
    }

    public static void main(String[] args) throws IOException {
        var path = Path.of("/Users/p.plieschnegger/private/One-Vision/Data");
        long start = System.currentTimeMillis();
        var program = new CommandLineConquer(path);

        long end = System.currentTimeMillis();

        System.out.println("Seconds: " + (end - start) / 1000d);
        System.out.println("Done Parsing!");

        for (var entry : program._xmlDirectory.entrySet()) {
            XMLDocument value = entry.getValue();
            if (value == null) {
                System.out.println("Null key at: " + entry.getKey());
            }
        }

        System.out.println();
        var list = program._xmlDirectory.keySet().stream()
            .sorted()
            .collect(Collectors.toList()); /*

        for (Object xmlFile : list) {
            System.out.println(xmlFile);
        } */

        System.out.println(list.get(0));

        // Test for Predator
        var predator = list.stream().filter(xml -> xml.toString().contains("GDIPredator.xml")).findAny();
        Path predatorPath = predator.orElse(Path.of(""));
        XMLDocument predatorXML = XMLDocument.ofFile(predatorPath);

        Optional<XMLTag> health = Navigator.getTag(predatorXML, "AssetDeclaration", "GameObject", "Body", "ActiveBody");

        health.ifPresent(tag -> System.out.println("Predator HP: " + tag.getAttribute("MaxHealth").getValue()));


    }
}
