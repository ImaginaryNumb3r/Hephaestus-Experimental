package xml.simple;

import lib.ListIterable;
import lib.Strings;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Supplier;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * This node never returns invalid.
 */
public class MultiNode<T extends ParseNode> extends AbstractParseNode implements ListIterable<T> {
    private final List<T> _elements;
    private final Supplier<T> _tokenConstructor;

    protected MultiNode(Supplier<T> tokenConstructor) {
        _elements = new ArrayList<>();
        _tokenConstructor = tokenConstructor;
    }

    @Override
    protected int parseImpl(String chars, int index) {
        T token = _tokenConstructor.get();

        int nextIndex;

        // Parse whole tokens until the one where it fails.
        while ((nextIndex = token.parse(chars, index)) != INVALID) {
            _elements.add(token);
            index = nextIndex;

            token = _tokenConstructor.get();
        }

        return index;
    }

    @Override
    public String toString() {
        return Strings.concat(_elements);
    }

    @NotNull
    @Override
    public ListIterator<T> listIterator() {
        return _elements.listIterator();
    }
}
