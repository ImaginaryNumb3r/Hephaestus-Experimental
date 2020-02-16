package parsing.model;

import java.util.Objects;

/**
 * A terminal with length 1 or more. An empty literal is not permitted.
 * Terminals are immutable and an attempt to change the state will throw an exception.
 */
public final class StringTerminal extends AbstractParseNode implements CopyNode<StringTerminal> {
    private final String _terminal;

    /**
     * @param terminal value of the terminal that may not be empty.
     * @throws IllegalArgumentException if the provided string is empty.
     */
    public StringTerminal(String terminal) {
        if (terminal.isEmpty()) {
            throw new IllegalArgumentException("Cannot create terminal from an empty string.");
        }

        _terminal = terminal;
    }

    @Override
    protected ParseResult parseImpl(String chars, int index) {
        int offset = 0;
        for (char expected : _terminal.toCharArray()) {
            char ch = chars.charAt(index + offset++);

            if (ch != expected) return ParseResult.notMatch(index + offset, expected, ch);
        }

        return ParseResult.at(index + offset);
    }

    @Override
    public String toString() {
        return _terminal;
    }

    @Override
    public StringTerminal deepCopy() {
        return this;
    }

    @Override
    public void setData(StringTerminal other) {
        if (!this.equals(other) ) {
            throw new UnsupportedOperationException("Cannot mutate terminal tokens.");
        }
    }

    @Override
    public void reset() {
        // Noop.
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StringTerminal)) return false;
        StringTerminal that = (StringTerminal) o;
        return Objects.equals(_terminal, that._terminal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_terminal);
    }
}
