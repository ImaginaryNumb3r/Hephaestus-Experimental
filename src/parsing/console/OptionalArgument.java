package parsing.console;

import parsing.console.common.AbstractArgument;

import java.util.List;

/**
 * An argument without associated values, which can only check for its existance.
 * It is explicitly optional.
 *
 * @author Patrick Plieschnegger
 */
public class OptionalArgument extends AbstractArgument<Boolean> {

    public OptionalArgument(String primaryName) {
        super(primaryName);
    }

    /**
     * @param tokens the separated command line arguments. Leading or trailing whitespaces must be trimmed.
     * @return true if the instance could be parsed, otherwise false.
     */
    @Override
    public boolean consume(List<String> tokens) {
        assertPreconditions();
        _value = tokens.removeAll(names());
        _status = _value ? ParseStatus.SUCCESS : ParseStatus.FAIL;

        return _value;
    }

    /**
     * @return true if the instance could be parsed, otherwise false.
     */
    @Override
    public Boolean fetchValue() {
        return super.fetchValue();
    }

    /**
     * @return OPTIONAL.
     */
    @Override
    public Type getType() {
        return Type.OPTIONAL;
    }
}
