package xml;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 */
public class PrologToken extends ContentToken implements CopyNode<PrologToken> {

    public PrologToken() {
        super("<?", "?>");
    }

    public PrologToken deepCopy() {
        PrologToken copy = new PrologToken();
        copy.setContent(_buffer);

        return copy;
    }
}
