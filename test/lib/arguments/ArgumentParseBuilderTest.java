package lib.arguments;

import org.junit.Test;
import org.junit.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import static junit.framework.TestCase.*;

public class ArgumentParseBuilderTest {

    // TODO: Some more tests which mix different types
    // Next API Version:
    //  - non-whitespace characters as valid identifiers.
    //  - long and short names for arguments
    //  - pretty print of all arguments
    //  - Get rid of option and have two enums? 1) Mandatory/Optional declaration 2) Mandatory/Optional value
    //  - don't have three different sets
    //  - Consider fluent API which exposes the ArgumentImpl API
    //  - Fluent Parser construction for existing API

    /**
     * Checks for thrown exceptions for different combinations of illegal characters.
     */
    @Test
    public void testInvalidNames() {
        List<String> invalids = Arrays.asList("-invalid", " ", "", null, "space space", "trailing ", "dot.", "\t", "question?");

        for (String invalid : invalids) {
            try {
                var builder = new ArgumentParseBuilder();
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

        var builders = List.of(new ArgumentParseBuilder(), new ArgumentParseBuilder(), new ArgumentParseBuilder());
        var kinds = List.of("Option", "ArgumentImpl", "Array ArgumentImpl");

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

    /**
     * Adds description to existing and non-existing arguments and verifies their behavior.
     */
    @Test
    public void testDescription() {
        String optionDesc = "Description of the option";
        String argDesc = "Description of the arg";
        String arrayArgDesc = "Description of the array";

        var builder = new ArgumentParseBuilder();
        builder.addOption("option");
        builder.addArgument("argument", ArgumentType.OPTIONAL);
        builder.addArrayArgument("arrayArgument", ArgumentType.OPTIONAL);

        builder.addDescription("option", optionDesc);
        builder.addDescription("argument", argDesc);
        builder.addDescription("arrayArgument", arrayArgDesc);

        // ArgumentImpl descriptions can be derived from the builder and the argument object.
        assertEquals(optionDesc, builder.getDescription("option"));
        assertEquals(argDesc, builder.getDescription("argument"));
        assertEquals(arrayArgDesc, builder.getDescription("arrayArgument"));

        var inputStrings = Arrays.asList("", "-option -argument -arrayArgument");
        for (String inputString : inputStrings) {
            Arguments arguments = builder.parse(inputString);

            assertEquals(optionDesc, arguments.getDescription("option"));
            assertEquals(argDesc, arguments.getDescription("argument"));
            assertEquals(arrayArgDesc, arguments.getDescription("arrayArgument"));

            assertException(IllegalArgumentException.class,
                () -> builder.addDescription("invalid", "desc")
            );
        }
    }

   @Test
    public void testDuplicates() {
       var arguments = Arrays.asList("first", "second", "third");

       for (String argument : arguments) {
           for (int i = 0; i != 3; ++i) {

               var builder = new ArgumentParseBuilder();
               if (i == 0) {
                   arguments.forEach(builder::addOption);
               }
               if (i == 1) {
                   arguments.forEach(arg -> builder.addArgument(arg, ArgumentType.MANDATORY));
               }
               if (i == 2) {
                   arguments.forEach(arg -> builder.addArrayArgument(arg, ArgumentType.MANDATORY));
               }

               assertException(IllegalArgumentException.class, () -> builder.addOption(argument));
               assertException(IllegalArgumentException.class, () -> builder.addArgument(argument, ArgumentType.MANDATORY));
               assertException(IllegalArgumentException.class, () -> builder.addArrayArgument(argument, ArgumentType.MANDATORY));
           }
       }
   }

    @Test
    public void testDefaultValues() {
        var builder = new ArgumentParseBuilder();
        var arrayArguments = Arrays.asList("arg1", "arg2", "arg3");
        var defaultArgument = "defaultArg";

        builder.addArgument("argument", defaultArgument);
        builder.addArrayArgument("arrayArg", ArgumentType.OPTIONAL, arrayArguments);

        Arguments defaultArgs = builder.parse("");
        assertEquals(defaultArgument, defaultArgs.getValue("argument"));
        assertEquals(arrayArguments, defaultArgs.getArrayArgument("arrayArg"));

        // Overwrite default arguments
        String argument = "-argument=value";
        String arrayArgument = "-arrayArg 1 2 3";
        // String arrayArgument = "-arrayArg arg-1 arg-2 arg-3"; // TODO: This should work, fix it.

        Arguments arguments = builder.parse(argument + " " + arrayArgument);
        assertEquals("value", arguments.getValue("argument"));
        assertEquals(Arrays.asList("1", "2", "3"), arguments.getArrayArgument("arrayArg"));

        // Test with default and overwritten arguments.
        builder = new ArgumentParseBuilder();
        builder.addArrayArgument("arrayArg", ArgumentType.OPTIONAL);
        builder.addArgument("argument", defaultArgument);

        arguments = builder.parse(arrayArgument);
        assertEquals(defaultArgument, arguments.getValue("argument"));
        assertEquals(Arrays.asList("1", "2", "3"), arguments.getArrayArgument("arrayArg"));
    }

    @Test
    public void testMandatoryArguments() {
        var builder = new ArgumentParseBuilder();
        builder.addArgument("mandatory", ArgumentType.MANDATORY);
        builder.addArgument("optional", ArgumentType.OPTIONAL);
        builder.addArgument("default", "default");

        // Assert exception when no mandatory is set.
        assertException(ArgumentParseException.class, () -> builder.parse("-optional -default"));
        assertException(ArgumentParseException.class, () -> builder.parse("-optional"));
        assertException(ArgumentParseException.class, () -> builder.parse("-default"));
        assertException(ArgumentParseException.class, () -> builder.parse(""));

        // Assert exception when only one mandatory is set.
        builder.addArgument("mandatory2", ArgumentType.MANDATORY);
        assertException(ArgumentParseException.class, () -> builder.parse("-optional -default -mandatory=value"), ex -> ex.getMissing().size() == 1);
        assertException(ArgumentParseException.class, () -> builder.parse("-optional -mandatory=value"), ex -> ex.getMissing().size() == 1);
        assertException(ArgumentParseException.class, () -> builder.parse("-default -mandatory=value"), ex -> ex.getMissing().size() == 1);
        assertException(ArgumentParseException.class, () -> builder.parse("-mandatory=value"), ex -> ex.getMissing().size() == 1);

        assertException(ArgumentParseException.class, () -> builder.parse("-optional -default"), ex -> ex.getMissing().size() == 2);
        assertException(ArgumentParseException.class, () -> builder.parse("-optional"), ex -> ex.getMissing().size() == 2);
        assertException(ArgumentParseException.class, () -> builder.parse("-default"), ex -> ex.getMissing().size() == 2);
        assertException(ArgumentParseException.class, () -> builder.parse(""), ex -> ex.getMissing().size() == 2);

        // Assert that no exception is thrown when mandatory argument is set.
        builder.parse("-optional -default -mandatory=value -mandatory2=value");
        builder.parse("-optional -mandatory=value -mandatory2=value");
        builder.parse("-default -mandatory=value -mandatory2=value");
        builder.parse("-mandatory=value -mandatory2=value");
    }

    private <T extends RuntimeException> void assertException(Class<T> expected, Runnable action) {
        assertException(expected, action, null);
    }

    private <T extends RuntimeException> void assertException(Class<T> expected, Runnable action, Predicate<T> test) {
        try {
            action.run();
            Assert.fail();
        } catch(RuntimeException ex) {
            Assert.assertEquals(ex.getClass(), expected);

            if (test != null) {
                // This cast will never fail because of the assertion above.
                T exception = (T) ex;
                assertTrue(test.test(exception));
            }
        }
    }
}
