package parsing.model;

import essentials.contract.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Creator: Patrick
 * Created: 26.03.2019
 * Purpose:
 */
public class ParseResult {
    private final int _cursorPosition;
    private final String _message;
    private final boolean _isValid;
    private final List<ParseResult> _innerErrors;

    public ParseResult(int cursorPosition, String message, boolean isValid) {
        this(cursorPosition, message, isValid, new ArrayList<>());
    }

    public ParseResult(int cursorPosition,
                       String message,
                       boolean isValid,
                       Collection<ParseResult> innerErrors
    ) {
        _cursorPosition = cursorPosition;
        _message = message;
        _isValid = isValid;
        _innerErrors = new ArrayList<>(innerErrors);
    }

    public List<ParseResult> innerErrors() {
        return _innerErrors;
    }

    public boolean isValid() {
        return _isValid;
    }

    public boolean isInvalid() {
        return !_isValid;
    }

    public String getMessage() {
        if (_isValid) {
            throw new IllegalStateException("Cannot get message because cursor is at a valid position.");
        }

        return _message;
    }

    public int index() {
        if (!_isValid) {
            throw new IllegalStateException("Cannot get position of an invalid cursor.");
        }

        return _cursorPosition;
    }

    public static ParseResult invalid(int index, @NotNull String message) {
        return invalid(index, message, Collections.emptyList());
    }

    public static ParseResult invalid(int index, @NotNull String message, Collection<ParseResult> innerExceptions) {
        Contract.checkNull(message, "message");

        return new ParseResult(index, message, false, innerExceptions);
    }

    public static ParseResult invalid(int index, @NotNull String message, ParseResult... innerExceptions) {
        return invalid(index, message, Arrays.asList(innerExceptions));
    }

    public static ParseResult notMatch(int index, char expected, char actual) {
        return notMatch(index, Character.toString(expected), Character.toString(actual));
    }

    public static ParseResult notMatch(int index, String expected, String actual) {
        // If this ever throws, consider turning input nulls into string nulls "null".
        Contract.checkNull(expected, actual);
        String message = "Failed to match expected \"" + expected + "\" with \"" + actual + "\"";

        return new ParseResult(index, message, false);
    }

    public static ParseResult at(int cursor) {
        Contract.checkNegative(cursor);
        return new ParseResult(cursor, null, true);
    }

    @Override
    public String toString() {
        return isValid() ? "Index: " + _cursorPosition : "Invalid";
    }
}
