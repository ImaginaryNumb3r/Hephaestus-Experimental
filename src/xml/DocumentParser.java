package xml;

import java.util.function.Predicate;

/**
 * Creator: Patrick
 * Created: 14.03.2019
 * Purpose:
 * TODO: Parse to own framework? -> Hephaestus-Parsing (along with abstract machine)
 */
public class DocumentParser {

    private enum Prologue implements Predicate<String> {
        START   { public String signal() { return "<?"; } },
        End     { public String signal() { return "?>"; } };

        public abstract String signal();

        public boolean test(String input) {
            return signal().equals(input);
        }
    }

    public enum OpenedTag implements Predicate<Character> {
        START   { public boolean test(Character input) { return input == '<'; } },
        TEXT    { public boolean test(Character input) { return true; } },
        End     { public boolean test(Character input) { return true; } };

        public abstract boolean test(Character input);
    }
}

/**
 * Token: Terminal
 * Node: Nonterminal
 *
 *
 * Blank: Char Consumer
 * (Whitespace)*
 *
 * Text: Content
 * '"' (Value)* '"'
 *
 * Attribute: Sequence
 * Text Blank '=' Blank Text
 *
 * Attributes: Multiple (Recursive)
 * (Blank Attribute)*
 *
 * ClosedTag: Sequence
 * '<' Text Blank Attributes Blank '/>'
 *
 * OpenedTag: Sequence
 * '<' Text Blank Attributes Blank '>' ( String ) '<' Text Blank '>'
 *                                            ^ take until the next applies. String is optional
 * Tag: OR
 * (ClosedTag | OpenedTag)
 *
 * Prologue: Content
 * '<?' Text '?>'
 *
 * Grammar:
 * (Prologue) (Tag)+
 *
 */
