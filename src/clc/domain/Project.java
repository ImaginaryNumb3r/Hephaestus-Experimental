package clc.domain;

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
    public static final Path PROJECT_PATH = Path.of("/Users/p.plieschnegger/private/One-Vision/Data");
    private final Map<Path, XMLDocument> _xmlDirectory = new HashMap<>();
    private final Path _projectPath;

    public Project(Path projectPath) throws IOException {
        _projectPath = projectPath;
    }

    public void initialize() throws IOException {
        Path staticPath = _projectPath.resolve("Static.xml");

        var staticXML = XMLDocument.ofFile(staticPath);
        var xmlQueue = new ArrayDeque<XMLDocument>();
        xmlQueue.add(staticXML);

        // TODO: You could also say this is the "check config" step.
        while (!xmlQueue.isEmpty()) {
            XMLDocument xml = xmlQueue.pop();

            var includePath = singletonList("AssetDeclaration");
            Optional<XMLTag> includeTag = Navigator.getTag(xml, includePath);

            if (includeTag.isPresent()) {
                XMLTag includes = includeTag.get();

                for (XMLTag include : includes.children()) {
                    if (!include.hasAttributes("source")) break;

                    AttributeToken source = include.getAttribute("source");
                    AttributeToken typeAttr = include.getAttribute("type");

                    String relLocation = source.getValue();
                    String type = typeAttr.getValue();

                    // Ignore art xmls and other special case xmls.
                    if (type.equalsIgnoreCase("all") && !relLocation.startsWith("ART:")) {
                        var xmlPath = _projectPath.resolve(relLocation);

                        try {
                            var xmlDocument = XMLDocument.ofFile(xmlPath);

                            if (xmlDocument == null) {
                                System.out.println("Cannot parse: " + xmlPath);
                                xmlDocument = XMLDocument.ofFile(xmlPath);
                            }

                            // Add to queue.
                            xmlQueue.add(xmlDocument);
                            _xmlDirectory.put(xmlPath, xmlDocument);
                        } catch (NoSuchFileException ex) {
                            System.out.println("No such file: " + xmlPath);
                        }
                    }
                }
            }
        }
    }

    public Map<Path, XMLDocument> getXmlDirectory() {
        return _xmlDirectory;
    }
}
