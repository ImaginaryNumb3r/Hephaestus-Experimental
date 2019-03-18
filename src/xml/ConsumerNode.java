package xml;

/**
 * @author Patrick Plieschnegger
 */
public abstract class ConsumerNode extends AbstractToken implements CharPredicate {
    private final StringBuilder _buffer;

    /*protected*/ ConsumerNode() {
        _buffer = new StringBuilder();
    }

    @Override
    public Status accept(char character) {
        assertParsing();

        if (!test(character)) {
            _parseStatus = Status.DONE;
            return Status.INVALID;
        }

        _buffer.append(character);
        return Status.PARSING;
    }

    @Override
    public String toString() {
        return _buffer.toString();
    }

    public static ConsumerNode of(CharPredicate predicate) {
        return new ConsumerNode() {
            @Override
            public boolean test(char character) {
                return predicate.test(character);
            }
        };
    }
}
