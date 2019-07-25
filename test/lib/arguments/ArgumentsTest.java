package lib.arguments;

import org.junit.Test;

public class ArgumentsTest {

    @Test
    public void testOptions() {
        String enabled = "-enabled";
        String invalid = "invalid";

        var builder = new ArgumentBuilder();
        builder.addOption(enabled);
        builder.addOption(invalid);
    }
}
