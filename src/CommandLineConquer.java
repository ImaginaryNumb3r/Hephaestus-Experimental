import lib.io.ForkJoinXML;
import parsing.xml.XMLDocument;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.concurrent.ForkJoinPool;

/**
 * @author Patrick Plieschnegger
 */
public class CommandLineConquer {
    private final Map<Path, XMLDocument> _xmlDirectory;

    public CommandLineConquer(Path path) {
        _xmlDirectory = initialize(path);
        System.out.println();
    }

    private Map<Path, XMLDocument> initialize(Path root) {
        var pool = new ForkJoinPool();

        ForkJoinXML rootTask = new ForkJoinXML(root, XMLDocument::ofFile);
        pool.execute(rootTask);

        return rootTask.join();
    }

    public static void main(String[] args) {
        var path = Path.of("/Users/p.plieschnegger/private/One-Vision/Data/");
        long start = System.currentTimeMillis();
        var program = new CommandLineConquer(path);
        long end = System.currentTimeMillis();

        System.out.println("Seconds: " + (end - start) / 1000d);
    }

    private class FileVisitor extends SimpleFileVisitor<Path> {
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {


            return super.visitFile(file, attrs);
        }
    }
}
