package lib;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.IntPredicate;

/**
 * @author Patrick Plieschnegger
 */
public class Numbers {

    public static OptionalInt parseInt(CharSequence string) {
        int lastIndex = string.length() - 1;
        IntPredicate isNumber = ch -> ch >= '0' && ch <= '9';
        char ch = string.charAt(lastIndex);

        // Ensure that last digit is a number. Character '-' is not valid.
        if (!isNumber.test(ch)) {
            return OptionalInt.empty();
        }

        int value = string.charAt(lastIndex) - '0';
        int mult = 10;

        for (int i = lastIndex - 1; i >= 0; --i) {
            ch = string.charAt(i);

            if (isNumber.test(ch)) {
                value += (ch - '0') * mult;
                mult *= 10;
            } else if (ch != '_') {
                return OptionalInt.empty();
            }
        }

        return OptionalInt.of(value);
    }

    public static OptionalInt box(Integer value) {
        return value == null ? OptionalInt.empty() : OptionalInt.of(value);
    }
}
