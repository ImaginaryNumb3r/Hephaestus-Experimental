package parsing.console.common;

import essentials.contract.Result;
import org.jetbrains.annotations.NotNull;
import parsing.console.Argument;

import java.util.*;

import static java.lang.String.join;

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
    protected ParseStatus _status = ParseStatus.UNPARSED;
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
     * @return a non-null instance if the ParseStatus is SUCCESS.
     * @throws IllegalStateException if a null value is returned on an instance with status SUCCESS:
     */
    @Override
    public T fetchValue() {
        if (_value == null && _status == ParseStatus.SUCCESS) {
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
     * Further, the status of the instance returns SUCCESS and it contains a non-null value.
     *
     * If the method does not succeed, its status is changed to FAIL and the token list remains unchanged.
     * Further, if the instance's type is MANDATORY it throws an ArgumentParseException, otherwise it returns false.
     * In other words, an optional argument never throws ArgumentParseException.
     *
     * On a failure, the list must not be changed and the instance's status changed to FAIL.
     * If consume was called on an instance which previously succeeded, this method has no defined behavior.
     *
     * The implementation can choose whether multiple matchingIndices counts as success or failure.
     *
     * It is the parser's job to only call consume on Arguments which are contained in the token list.
     *
     * @param tokens a mutable list, containing the separated command line arguments.
     *               The tokens must not contain whitespaces of any kind.
     * @return true if argument could be parsed.
     *         false if the OPTIONAL argument cannot be parsed.
     * @throws ArgumentParseException if this method fails on a MANDATORY argument instance.
     * @throws IllegalArgumentException
     * @throws IllegalStateException if the Argument cannot be constructed in a expected way.
     *                               E.g. when the method names() returns an empty set.
     */
    public abstract boolean consume(List<String> tokens) throws ArgumentParseException;

    protected Result<ArgumentParseException> assertMatchOnce(List<String> matches) {
        // Type type = getType();
        int occurrences = matches.size();

        // Handle errors first.
        if (occurrences == 0) { /*
            switch (type) {
                case MANDATORY: */
                throw ArgumentParseException.ofMandatory(names()); /*
                case OPTIONAL: return Result.failure(null);
                default: throw new EnumNotPresentException(Type.class, type);
            } */
        }
        if (occurrences > 1) { /*
            switch (type) {
                case MANDATORY: */
                throw new ArgumentParseException(join(" ",
                    "Expected one match for argument ", _primaryName,
                    "but received " + occurrences, Arrays.toString(matches.toArray())
                )); /*
                case OPTIONAL: return Result.failure(null);
                default: throw new EnumNotPresentException(Type.class, type);
            } */
        }

        return Result.ok(null);
    }

    public String primaryName() {
        return _primaryName;
    }

    /**
     * A convenience method that can be called by implemented classes to assert well-defined instance state.
     */
    protected void assertPreconditions() {
        if (getStatus() == ParseStatus.SUCCESS) {
            throw new IllegalStateException("Cannot re-parse an argument instance which was created previously .");
        }

        if (names().size() == 0) {
            throw new IllegalStateException("Arguments without at least one name cannot be parsed");
        }
    }

    /**
     * TODO: Problem because this only considers exact matches.
     * @param tokens
     * @return the list of matchingIndices in the token list.
     */
    protected List<Integer> matchingIndices(List<String> tokens) {
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

    public ParseStatus getStatus() {
        return _status;
    }
}
