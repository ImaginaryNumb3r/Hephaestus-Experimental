package parsing.arguments;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class AbstractArgument<T> implements Argument<T> {
    protected final Set<String> _names = new HashSet<>();
    protected String _description = "<No description>";
    protected Status _status = Status.UNPARSED;
    protected T _value;

    @Override
    public Set<String> names() {
        return _names;
    }

    @Override
    public String getDescription() {
        return _description;
    }

    public void setDescription(String description) {
        _description = description;
    }

    /**
     * @return a non-null instance if the Status is PARSED.
     * @throws IllegalStateException if a null value is returned on an instance with status PARSED:
     */
    @Override
    public T fetchValue() {
        if (_value == null && _status == Status.PARSED) {
            throw new IllegalStateException("null cannot be returned on a parsed element.");
        }

        return _value;
    }

    protected void setValue(@NotNull T value) {
        _value = value;
    }

    /**
     * Attempts to create the argument from the list of tokens which represents the order of arguments from the command line.
     * The method succeeds if the token list contains at least one possible argument name and all other constraints are satisfied.
     * An implementation of this class can freely choose what kind of constraints the token list must adhere to.
     *
     * If the method succeeds, all tokens required to construct the argument are removed.
     * Further, the status of the instance returns PARSED and it contains a non-null value.
     *
     * If the method does not succeed, its status is changed to ERROR and the token list remains unchanged.
     * Further, if the instance's type is MANDATORY it throws an ArgumentParseException, otherwise it returns false.
     *
     * On a failure, the list must not be changed. The instance's status must be changed to ERROR.
     *
     * If consume was called on an instance which previously succeeded, this method has no defined behavior.
     *
     * @param tokens the separated command line arguments. Leading or trailing whitespaces must be trimmed.
     * @return true if argument could be parsed.
     *         false if the argument cannot be parsed.
     * @throws ArgumentParseException if this method fails on a MANDATORY argument instance.
     * @throws IllegalStateException if the method names() returns an empty set.
     */
    protected abstract boolean consume(List<String> tokens) throws ArgumentParseException;

    protected void throwOnReparse() {
        if (getStatus() == Status.PARSED) {
            throw new IllegalStateException("Cannot re-parse an argument instance which was created previously .");
        }
    }

    public Status getStatus() {
        return _status;
    }
}
