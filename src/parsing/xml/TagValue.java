package parsing.xml;

import parsing.model.EitherNode;
import parsing.model.StringToken;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Purpose:
 */
public class TagValue extends EitherNode<StringToken, TagOpening> {

    public TagValue(StringToken optional, TagOpening mandatory) {
        super(optional, mandatory);
    }
}
