package clc;

import parsing.xml.AttributeToken;
import parsing.xml.Navigator;
import parsing.xml.XMLDocument;
import parsing.xml.XMLTag;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Collections.singletonList;

/**
 * @author Patrick Plieschnegger
 */
public class Project {
    private final Map<Path, XMLDocument> _xmlDirectory = new HashMap<>();

    public Project(Path projectPath) throws IOException {
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

    public Map<Path, XMLDocument> getXmlDirectory() {
        return _xmlDirectory;
    }
}
