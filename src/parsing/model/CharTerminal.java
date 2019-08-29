package parsing.model;

import java.util.Objects;

/**
 * Creator: Patrick
 * Created: 20.03.2019
 * Purpose:
 */
public class CharTerminal extends AbstractParseNode implements CopyNode<CharTerminal> {
    private final char _character;

    public CharTerminal(char character) {
        _character = character;
    }

    @Override
    protected ParseResult parseImpl(String chars, int index) {
        char character = chars.charAt(index);

        if (character == _character) {
            return ParseResult.at(index + 1);
        } else {
            String message = "Character " + character + " does not match " + _character + " at index: " + index;
            return ParseResult.invalid(index, message);
        }
    }

    @Override
    public String toString() {
        return Character.toString(_character);
    }

    @Override
    public CharTerminal deepCopy() {
        return this;
    }

    @Deprecated
    @Override
    public void setData(CharTerminal other) {
        throw new UnsupportedOperationException("Cannot mutate terminal tokens.");
    }

    @Override
    public void reset() {
        // Noop.
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CharTerminal)) return false;
        CharTerminal that = (CharTerminal) o;
        return _character == that._character;
    }

    @Override
    public int hashCode() {
        return Objects.hash(_character);
    }
}
