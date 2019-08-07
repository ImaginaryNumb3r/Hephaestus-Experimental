package lib.arguments;

import org.junit.Assert;
import org.junit.Test;

public class ArgumentsTest {

    @Test
    public void testOptions() {
        String enabled = "-enabled";
        String invalid = "invalid";

        var builder = new ArgumentParseBuilder();
        try {
            builder.addOption(enabled);
            Assert.fail();
        } catch (RuntimeException ignore) { }

        builder.addOption(invalid);
    }
}
