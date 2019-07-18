package lib.argument2;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;

public class ArgumentBuilderTest {

    /**
     * Checks for thrown exceptions for different combinations of illegal characters.
     */
    @Test
    public void testInvalidNames() {
        List<String> invalids = Arrays.asList("-invalid", " ", "", null, "space ", "dot.", "\t", "question?");

        for (String invalid : invalids) {
            try {
                var builder = new ArgumentBuilder();
                builder.addOption(invalid);

                fail("Expected IllegalArgumentException was not thrown for argument: \" " + invalid + "\"");
            } catch (IllegalArgumentException ignored) { }
        }
    }

    /**
     * Adds argument names to the builder and checks whether they can be correctly accessed as Arguments.
     */
    @Test
    public void testValidNames() {
        List<String> valids = Arrays.asList("123", "alphabetical", "letter123", "uiw7e9rh2", "äöü");

        var builders = List.of(new ArgumentBuilder(), new ArgumentBuilder(), new ArgumentBuilder());
        var kinds = List.of("Option", "Argument", "Array Argument");

        List<BiPredicate<Arguments, String>> predicates = List.of(
            Arguments::hasOption,
            Arguments::containsArgument,
            Arguments::containsArrayArgument
        );

        for (String valid : valids) {
            builders.get(0).addOption(valid);
            builders.get(1).addArgument(valid, ArgumentType.OPTIONAL);
            builders.get(2).addArrayArgument(valid, ArgumentType.OPTIONAL);
        }

        int i = 0;
        for (var builder : builders) {
            for (String valid : valids) {
                var arguments = builder.parse(valid);
                boolean isValid = predicates.get(i).test(arguments, valid);

                String kind = kinds.get(i);
                assertTrue(kind + " could not be defined for: " + valid, isValid);
            }
            ++i;
        }
    }

    /*
     * To Test
     *  - Description
     *  - Duplicate Entries
     *  - Default Values
     *  - Parsing with multiple values
     */


}
