package lib.argument2;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.fail;

public class ArgumentBuilderTest {

    @Test
    public void testArguments() {
        List<String> invalids = Arrays.asList("-invalid", " ", "", null, "space ", "dot.", "\t", "question?");

        for (String invalid : invalids) {
            try {
                var builder = new ArgumentBuilder();
                builder.addOption(invalid);
                fail("Expected IllegalArgumentException was not thrown for argument: \" " + invalid + "\"");
            } catch (IllegalArgumentException ex) {
            }
        }


        var builder = new ArgumentBuilder();
    }
}
