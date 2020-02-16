package parsing.xml;

import java.nio.file.Path;
import java.util.*;
import collections.iterator.Iterators;
import essentials.annotations.Unfinished;

import static java.util.Arrays.asList;

/**
 * Creator: Patrick
 * Created: 05.04.2019
 * TODO: Either finish or remove.
 */
@Unfinished
public class Navigator {

    public static Optional<XMLTag> getTag(XMLDocument root, Path path) {
        return getTag(root.getRoot(), path);
    }

    public static Optional<XMLTag> getTag(XMLTag root, Path path) {
        var list = new ArrayList<String>();
        Iterator<Path> iter = path.iterator();

        while (iter.hasNext()) {
            iter.next();
            // list.add(path);
        }

        return getTag(root, list);
    }

    public static Optional<XMLTag> getTag(XMLDocument root, String... path) {
        return getTag(root.getRoot(), asList(path), 0);
    }

    public static Optional<XMLTag> getTag(XMLTag root, String... path) {
        return getTag(root, asList(path), 0);
    }


    public static Optional<XMLTag> getTag(XMLDocument root, Iterable<String> path) {
        return getTag(root, path.iterator());
    }

    public static Optional<XMLTag> getTag(XMLTag root, Iterable<String> path) {
        var list = new ArrayList<String>();
        path.forEach(list::add);

        return getTag(root, list, 0);
    }


    private static Optional<XMLTag> getTag(XMLDocument root, Iterator<String> path) {
        var list = new ArrayList<String>();
        path.forEachRemaining(list::add);

        return getTag(root.getRoot(), list, 0);
    }

    private static Optional<XMLTag> getTag(XMLTag start, List<String> path, int index) {
        // End recursion.
        if (index == path.size()) return Optional.of(start);

        String expected = path.get(index);
        String tagName = start.getName();

        if (tagName.equals(expected))  {
            for (XMLTag child : start.children()) {

                Optional<XMLTag> tag = getTag(child, path, index + 1);
                if (tag.isPresent()) return tag;
            }
        }

        // Has found the requested tag.
        if (index == path.size() - 1) return Optional.of(start);

        return Optional.empty();
    }
}
