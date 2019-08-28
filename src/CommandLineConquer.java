import lib.io.ForkJoinXML;
import parsing.xml.XMLDocument;

import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ForkJoinPool;

/**
 * @author Patrick Plieschnegger
 */
public class CommandLineConquer {
    private final Map<Path, XMLDocument> _xmlDirectory = new HashMap<>();
    private final Path _projectPath;

    public CommandLineConquer(Path projectPath) {
        _projectPath = projectPath;
    }

    private void parseDirectory(String directory) {
        Path path = _projectPath.resolve(directory);
        var pool = new ForkJoinPool(8);

        ForkJoinXML rootTask = new ForkJoinXML(path, XMLDocument::ofFile);
        pool.execute(rootTask);

        _xmlDirectory.putAll(rootTask.join());
    }

    public static void main(String[] args) {
        var path = Path.of("/Users/p.plieschnegger/private/One-Vision/Data");
        long start = System.currentTimeMillis();
        var program = new CommandLineConquer(path);
        program.parseDirectory("GlobalData");
        program.parseDirectory("RRF");
        program.parseDirectory("ZOCOM");
        program.parseDirectory("SteelTalons");
        program.parseDirectory("Messenger");
        program.parseDirectory("Traveler");
        program.parseDirectory("Reaper");
        program.parseDirectory("Renegades");
        program.parseDirectory("MoK");
        program.parseDirectory("BlackHand");

        long end = System.currentTimeMillis();

        for (var entry : program._xmlDirectory.entrySet()) {
            XMLDocument value = entry.getValue();
            if (value == null) {
                System.out.println("Null key at: " + entry.getKey());
            }
        }

        System.out.println("Seconds: " + (end - start) / 1000d);
    }
}
