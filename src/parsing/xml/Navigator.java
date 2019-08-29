package parsing.xml;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Creator: Patrick
 * Created: 05.04.2019
 * Purpose:
 */
public class Navigator {

    /*
    public static Optional<XMLTag> getTag(XMLDocument root, String... path) {
        return getTag(root, Iterators.of(path));
    }

    public static Optional<XMLTag> getTag(XMLTag start, String... path) {
        return getTag(start, Iterators.of(path));
    } */

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

    // Problem: Iterator is updated, even when his value is not accepted. -> needs to peek
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

        if (index == path.size() - 1) return Optional.of(start);

        return Optional.empty();
    }
}
