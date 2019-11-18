package parsing.xml;

import collections.iterator.Iterators;
import essentials.annotations.Unfinished;

import java.util.Iterator;
import java.util.Optional;

/**
 * Creator: Patrick
 * Created: 05.04.2019
 * TODO: Either finish or remove.
 */
@Unfinished
public class Navigator {

    public static Optional<XMLTag> getTag(XMLDocument root, String... path) {
        return getTag(root, Iterators.of(path));
    }

    public static Optional<XMLTag> getTag(XMLTag start, String... path) {
        return getTag(start, Iterators.of(path));
    }

    public static Optional<XMLTag> getTag(XMLDocument root, Iterable<String> path) {
        return getTag(root, path.iterator());
    }

    public static Optional<XMLTag> getTag(XMLTag root, Iterable<String> path) {
        return getTag(root, path.iterator());
    }

    private static Optional<XMLTag> getTag(XMLDocument root, Iterator<String> path) {
        return getTag(root.getRoot(), path);
    }

    private static Optional<XMLTag> getTag(XMLTag start, Iterator<String> path) {
        // End recursion.
        if (!path.hasNext()) return Optional.of(start);
        String expected = path.next();
        String tagName = start.getName();

        if (tagName.equals(expected))  {
            for (XMLTag child : start.children()) {

                Optional<XMLTag> tag = getTag(child, path);
                if (tag.isPresent()) return tag;
            }
        }

        return Optional.empty();
    }
}
