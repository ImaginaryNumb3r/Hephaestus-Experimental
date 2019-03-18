package xml;

import java.util.Arrays;

/**
 * @author Patrick Plieschnegger
 */
public class TagNode extends EitherNode {

    public TagNode() {
        super(Arrays.asList(new OpenedTag(), new ClosedTag()));
    }
}
