package lib.io;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author Patrick Plieschnegger
 */
@FunctionalInterface
public interface FileVisitFunction {

    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param u the second function argument
     * @return the function result
     */
    FileVisitResult apply(Path t, BasicFileAttributes u) throws IOException;
}
