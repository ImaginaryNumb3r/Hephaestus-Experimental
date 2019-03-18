package xml;

import lib.Strings;

/**
 * Creator: Patrick
 * Created: 18.03.2019
 * Purpose:
 */
public class OptionalNode extends AbstractToken {
    private final TokenBuilder _optional;
    private final TokenBuilder _mandatory;
    private boolean _validOptional;

    protected OptionalNode(TokenBuilder optional, TokenBuilder mandatory) {
        _optional = optional;
        _mandatory = mandatory;
        _validOptional = true;
    }

    @Override
    public Status accept(char character) {
        assertParsing();

        /*
         * The mandatory and optional tokens are parsed at the same time.
         * If the parsing of the optional node turns invalid, the optional node is dropped and the
         * parsing continues with the mandatory token only.
         * If the mandatory token turns invalid it must be reset and the optional node must finish first.
         */

        if (_optional.canAccept()) {
            Status optionalStatus = _optional.accept(character);
        }

        _parseStatus = _mandatory.accept(character);
        if (_parseStatus == Status.INVALID) {
            _mandatory.reset();
        }


        return null;
    }

    public static OptionalNode ofMultiple(TokenBuilder... tokens) {
        if (tokens.length < 2) {
            System.out.println("Optional nodes must have at least length 2: an optional and mandatory token");
        }
        int tokenIndex = tokens.length;
        TokenBuilder mandatory = tokens[--tokenIndex];
        OptionalNode node;

        do {
            TokenBuilder optional = tokens[--tokenIndex];
            node = new OptionalNode(optional, mandatory);
            mandatory = node;

        } while (tokenIndex != 0);

        return node;
    }

    @Override
    protected void partialReset() {
        _optional.reset();
        _mandatory.reset();
        _validOptional = true;
    }

    @Override
    public String toString() {
        return _parseStatus.isValid() ? Strings.concat(_optional, _mandatory) : null;
    }
}
