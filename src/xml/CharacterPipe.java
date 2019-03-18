package xml;

import java.util.Iterator;

/**
 * Creator: Patrick
 * Created: 16.03.2019
 * Purpose:
 */
public class CharacterPipe {
    private final String _input;
    private int _pos = 0;

    public CharacterPipe(String input) {
        _input = input;
    }

    public boolean hasNext() {
        return _input.length() > _pos;
    }

    public char next() {
        return _input.charAt(_pos++);
    }

    public char peek() {
        return _input.charAt(_pos);
    }



    public Iterator<Character> toIterator() {
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return CharacterPipe.this.hasNext();
            }

            @Override
            public Character next() {
                return CharacterPipe.this.next();
            }
        };
    }
}
