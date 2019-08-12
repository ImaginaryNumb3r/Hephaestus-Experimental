package parsing.console;

import lib.EnumNotPresentException;
import lib.Result;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static java.lang.String.join;
import static parsing.console.ArgumentParseException.ofMandatory;

/**
 * An argument is only responsible for its own state and must assume that the list of tokens is a valid configuration.
 * The caller of consume must ensure that previous callers on consume of the same token list do not change the parsing effort of a later instance.
 *
 * Must not be concerned about the way it is employed by the parser (or more general, its owner).
 * Neither must it care whether there are subsequent or precedent calls from other arguments to the same token list.
 *
 * TODO: Mention invariant that it requires at least one value name. The names must not contain whitespaces. Null is not a valid value.
 *
 * @param <T>
 */
public abstract class AbstractArgument<T> implements Argument<T> {
    // It is reasonable to assume to have arguments with no or only one alias.
    protected final Set<String> _names = new HashSet<>(1);
    protected Status _status = Status.UNPARSED;
    protected String _description;
    protected String _primaryName;
    protected T _value;

    public AbstractArgument(@NotNull String primaryName) {
        _primaryName = primaryName;
        _names.add(primaryName);
    }

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

    public void addAliases(String... aliases) {
        Collections.addAll(_names, aliases);
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
     * In other words, an optional argument never throws ArgumentParseException.
     *
     * On a failure, the list must not be changed and the instance's status changed to ERROR.
     * If consume was called on an instance which previously succeeded, this method has no defined behavior.
     *
     * The implementation can choose whether multiple matches counts as success or failure.
     *
     * @param tokens the separated command line arguments. Leading or trailing whitespaces must be trimmed.
     * @return true if argument could be parsed.
     *         false if the argument cannot be parsed.
     * @throws ArgumentParseException if this method fails on a MANDATORY argument instance.
     * @throws IllegalStateException if the Argument cannot be constructed in a expected way.
     *                               E.g. when the method names() returns an empty set.
     */
    protected abstract boolean consume(List<String> tokens) throws ArgumentParseException;

    protected Result<ArgumentParseException> assertMatchOnce(String[] matches) {
        Type type = getType();
        int occurrences = matches.length;

        // Handle errors first.
        if (occurrences == 0) {
            switch (type) {
                case MANDATORY:
                    var parseException = ofMandatory(names());
                    return Result.failure(parseException);
                case OPTIONAL: return Result.failure(null);
                default: throw new EnumNotPresentException(Type.class, type);
            }
        }
        if (occurrences > 1) {
            switch (type) {
                case MANDATORY:
                    var parseException = new ArgumentParseException(join(" ",
                        "Expected one match for argument ", _primaryName,
                        "but received " + occurrences, Arrays.toString(matches)
                    ));
                    return Result.failure(parseException);
                case OPTIONAL: return Result.failure(null);
                default: throw new EnumNotPresentException(Type.class, type);
            }
        }

        return Result.ok(null);
    }

    /**
     * A convenience method that can be called by implemented classes to assert well-defined instance state.
     */
    protected void assertPreconditions() {
        if (getStatus() == Status.PARSED) {
            throw new IllegalStateException("Cannot re-parse an argument instance which was created previously .");
        }

        if (names().size() == 0) {
            throw new IllegalStateException("Arguments without at least one name cannot be parsed");
        }
    }

    /**
     * @param tokens
     * @return the list of matches in the token list.
     */
    protected List<Integer> matches(List<String> tokens) {
        var indices = new ArrayList<Integer>();

        int index = 0;
        for (String token : tokens) {
            if (_names.contains(token)) {
                indices.add(index);
            }
            ++index;
        }

        return indices;
    }

    public Status getStatus() {
        return _status;
    }
}
