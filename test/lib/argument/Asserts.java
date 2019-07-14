package lib.argument;

import essentials.functional.exception.RunnableEx;
import org.junit.Assert;

import static java.lang.String.join;
import static org.junit.Assert.assertTrue;

/**
 * @author Patrick Plieschnegger
 */
public class Asserts {

    public static <X extends Exception> void assertException(RunnableEx<X> runnable, X exception) {
        try {
            runnable.run();
            Assert.fail(join(" ", "Expected exception", exception.toString(), "was not thrown."));
        } catch (Exception ex) {
            String message = "Failed to assert that the correct exception was thrown.";
            assertTrue(message, canCast(ex, exception));
        }
    }

    private static <T> boolean canCast(Object object, T target) {
        try {
            T t = (T) object;
            return true;
        } catch (ClassCastException ex) {
            return false;
        }
    }

}
