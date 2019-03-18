package xml;

/**
 * Creator: Patrick
 * Created: 14.03.2019
 * Purpose:
 */
public class Prologue extends ContentToken {
    private static final String START = "<?";
    private static final String END = "?>";

    public Prologue() {
        super(START, END);
    }
}
