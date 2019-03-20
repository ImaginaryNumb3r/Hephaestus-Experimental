package xml.simple;

import lib.Strings;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * This node never returns invalid.
 */
public class MultiNode extends AbstractParseNode {
    private final List<ParseNode> _elements;
    private final Supplier<ParseNode> _tokenConstructor;

    public MultiNode(Supplier<ParseNode> tokenConstructor) {
        _elements = new ArrayList<>();
        _tokenConstructor = tokenConstructor;
    }

    @Override
    protected int parseImpl(char[] chars, int index) {
        ParseNode token = _tokenConstructor.get();

        int nextIndex;

        // Parse whole tokens until the one where it fails.
        while ((nextIndex = token.parse(chars, index)) != INVALID) {
            _elements.add(token);
            index = nextIndex;
        }

        return index;
    }

    @Override
    public String toString() {
        return Strings.concat(_elements);
    }
}
