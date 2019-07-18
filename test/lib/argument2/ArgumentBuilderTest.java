package lib.argument2;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;

import static junit.framework.TestCase.*;

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
            String kind = kinds.get(i);
            var containsArgument = predicates.get(i);

            for (String valid : valids) {
                var arguments = builder.parse(valid);
                boolean isValid = containsArgument.test(arguments, valid);

                assertTrue(kind + " could not be defined for: " + valid, isValid);
            }
            ++i;
        }
    }

    @Test
    public void testDescription() {
        String optionDesc = "Description of the option";
        String argDesc = "Description of the arg";
        String arrayArgDesc = "Description of the array";

        var builder = new ArgumentBuilder();
        builder.addOption("option");
        builder.addArgument("argument", ArgumentType.OPTIONAL);
        builder.addArrayArgument("arrayArgument", ArgumentType.OPTIONAL);

        builder.addDescription("option", optionDesc);
        builder.addDescription("argument", argDesc);
        builder.addDescription("arrayArgument", arrayArgDesc);

        Arguments arguments = builder.parse("");

        assertEquals(optionDesc, arguments.getDescription("option"));
        assertEquals(argDesc, arguments.getDescription("argument"));
        assertEquals(arrayArgDesc, arguments.getDescription("arrayArgument"));
    }

    /*
     * To Test
     *  - Duplicate Entries
     *  - Default Values
     *  - Parsing with multiple values
     *  - Test Optional/Mandatory
     */


}
