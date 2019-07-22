package lib.argument2;

import org.junit.Test;
import org.junit.Assert;

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
        List<String> invalids = Arrays.asList("-invalid", " ", "", null, "space space", "trailing ", "dot.", "\t", "question?");

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

    /**
     * Adds description to existing and non-existing arguments and verifies their behavior.
     */
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

        // Argument descriptions can be derived from the builder and the argument object.
        assertEquals(optionDesc, builder.getDescription("option"));
        assertEquals(argDesc, builder.getDescription("argument"));
        assertEquals(arrayArgDesc, builder.getDescription("arrayArgument"));

        var inputStrings = Arrays.asList("", "-option -argument -arrayArgument");
        for (String inputString : inputStrings) {
            Arguments arguments = builder.parse(inputString);

            assertEquals(optionDesc, arguments.getDescription("option"));
            assertEquals(argDesc, arguments.getDescription("argument"));
            assertEquals(arrayArgDesc, arguments.getDescription("arrayArgument"));

            assertException(IllegalArgumentException.class, () -> builder.addDescription("invalid", "desc"));
        }
    }

   @Test
    public void testDuplicates() {
       var arguments = Arrays.asList("first", "second", "third");

       for (String argument : arguments) {
           for (int i = 0; i != 3; ++i) {

               var builder = new ArgumentBuilder();
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
        var builder = new ArgumentBuilder();
        var arrayArguments = Arrays.asList("arg1", "arg2", "arg3");
        var defaultArgument = "defaultArg";

        builder.addArgument("argument", ArgumentType.OPTIONAL, defaultArgument);
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
    }

    private <T extends RuntimeException> void assertException(Class<T> expected, Runnable action) {
        try {
            action.run();
            Assert.fail();
        } catch(RuntimeException ex) {
            Assert.assertEquals(ex.getClass(), expected);
        }
    }

    /*
     * TODO: Test
     *  - Default Values
     *  - Parsing with multiple values
     *  - Test Optional/Mandatory
     */
}
