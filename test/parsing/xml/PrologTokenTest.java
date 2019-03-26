package parsing.xml;

import org.junit.Assert;
import org.junit.Test;
import parsing.model.ParseNode;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Purpose:
 */
public class PrologTokenTest {
    private static final String PROLOG = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

    @Test
    public void testToken() {
        ParseNode token = new PrologToken();
        var result = token.parse(PROLOG, 0);

        assertTrue(result.isValid());
        assertEquals(PROLOG, token.toString());

        ParseNode copy = token.deepCopy();
        assertEquals(token, copy);
    }
}
