package lib.io;

import essentials.functional.exception.FunctionEx;
import parsing.xml.XMLDocument;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author Patrick Plieschnegger
 */
public class ForkJoinXML extends RecursiveTask<Map<Path, XMLDocument>> {
    private final FunctionEx<Path, XMLDocument, IOException> _constructor;
    private final Path _path;

    public ForkJoinXML(Path path, FunctionEx<Path, XMLDocument, IOException> constructor) {
        _constructor = constructor;
        _path = path;
    }

    @Override
    protected Map<Path, XMLDocument> compute() {
        try {
            return computeEx();
        } catch (Throwable ex) {
            throw ex;
        }
    }

    protected Map<Path, XMLDocument> computeEx() {
        if (!Files.isDirectory(_path)) return Collections.emptyMap();

        var childDirectories = new ArrayList<ForkJoinXML>();
        var xmls = new ArrayList<ForkJoinXML>();
        var xmlMap = new HashMap<Path, XMLDocument>();

        try {
            for (Path path : Files.newDirectoryStream(_path)) {

                if (isXML(path)) {
                    // Perform work.
                    ForkJoinXML task = new ForkJoinXML(path, _constructor::apply);
                    task.fork();

                    xmls.add(task);
                } // Recursive parallel descent.
                else if (Files.isDirectory(path)) {
                    var childTask = new ForkJoinXML(path, _constructor);

                    childTask.fork();
                    childDirectories.add(childTask);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Collective join on xml files.
        for (ForkJoinXML childTask : xmls) {
            var result = childTask.join();
            System.out.println("Done with: " + childTask._path);
            xmlMap.putAll(result);
        }

        // Collective join on directories.
        for (ForkJoinXML childTask : childDirectories) {
            var result = childTask.join();
            xmlMap.putAll(result);
        }

        return xmlMap;
    }

    private boolean isXML(Path path) {
        if (!Files.isRegularFile(path)) return false;

        return path.toString()
                    .toLowerCase(Locale.ENGLISH)
                    .endsWith(".xml");
    }
}
