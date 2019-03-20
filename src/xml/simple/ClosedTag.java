package xml.simple;

import lib.Strings;

import java.util.Arrays;
import java.util.List;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * '<' Text Blank Attributes Blank '/>'
 */
public class ClosedTag extends AbstractParseNode {
    private final List<ParseNode> _elements;

    public ClosedTag() {
        _elements = Arrays.asList(
                // Tag + name
                new CharToken('<'),
                new TextToken(),
                // Attributes

                new MultiNode(AttributeToken::new)
                // Tag Close
                // new StringToken("/>")
        );
    }

    @Override
    protected int parseImpl(String chars, int index) {

        for (ParseNode element : _elements) {
            index = element.parse(chars, index);

            if (index == INVALID) {
                return INVALID;
            }
        }

        return index;
    }

    @Override
    public String toString() {
        return Strings.concat(_elements);
    }
}
