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
    protected int parseImpl(String chars, int index) {
        char character = chars.charAt(index);

        return character == _character ? index + 1 : INVALID;
    }

    @Override
    public String toString() {
        return Character.toString(_character);
    }

    @Override
    public CharTerminal deepCopy() {
        return this;
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
