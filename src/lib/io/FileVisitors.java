package lib.io;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author Patrick Plieschnegger
 */
public final class FileVisitors {

    public static FileVisitor<Path> preVisitFile(FileVisitFunction visitor) {
        return new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                return visitor.apply(file, attrs);
            }
        };
    }
}
