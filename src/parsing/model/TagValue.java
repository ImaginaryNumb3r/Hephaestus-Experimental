package parsing.model;

import parsing.xml.OpenedTag;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Purpose:
 */
public class TagValue extends EitherNode<StringToken, OpenedTag> {

    public TagValue(StringToken optional, OpenedTag mandatory) {
        super(optional, mandatory);
    }
}
