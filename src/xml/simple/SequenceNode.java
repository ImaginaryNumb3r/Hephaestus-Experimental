package xml.simple;

import lib.Strings;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Purpose:
 */
public class SequenceNode extends AbstractParseNode {
    protected final List<ParseNode> _sequence;

    public SequenceNode(Collection<ParseNode> sequence) {
        _sequence = new ArrayList<>(sequence);
    }

    @Override
    protected int parseImpl(String chars, int index) {

        for (ParseNode element : _sequence) {
            index = element.parse(chars, index);

            if (index == INVALID) {
                return INVALID;
            }
        }

        return index;
    }

    @Override
    public String toString() {
        return Strings.concat(_sequence);
    }
}
