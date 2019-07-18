package lib.argument2;

import org.junit.Test;

import static org.junit.Assert.*;

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
