package parsing.xml;

import essentials.contract.NoImplementationException;
import parsing.model.*;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * '<' Text Blank Attributes Blank '>' ( String ) '<' Text Blank '>'
 * // TODO: Assert that the closing brackets are the same as the opening brackets.
 */
public class TagOpening extends SequenceNode implements CopyNode<TagOpening> {
    private final XMLBody _body;
    private final TextToken _name;
    private final WhitespaceToken _whitespace;

    public TagOpening() {
        super(new ArrayList<>());
        _body = new XMLBody();
        _name = new TextToken();
        _whitespace = new WhitespaceToken();

        _sequence.addAll(Arrays.asList(
                new CharTerminal('>'),

                // Value / Inner nodes
                _body,
                // new StringToken(), // Node Value -> Needs to be either a sub-node or an (empty) string.

                // XML Node
                new CharTerminal('<'),
                _name, // Node name
                _whitespace,
                new CharTerminal('>')
        ));
    }

    public XMLBody getBody() {
        return _body;
    }

    public String getName() {
        return _name.toString();
    }

    public WhitespaceToken getWhitespace() {
        return _whitespace;
    }

    @Override
    public TagOpening deepCopy() {
        throw new NoImplementationException();
    }

    @Override
    public void setData(TagOpening other) {
        throw new NoImplementationException();
    }

    @Override
    public void reset() {
        super.reset();
    }
}
