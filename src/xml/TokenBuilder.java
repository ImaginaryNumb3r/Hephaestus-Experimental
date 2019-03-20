package xml;

/**
 * Creator: Patrick
 * Created: 16.03.2019
 * A AbstractToken is a well-defined single instance in a parse grammar.
 * A token contains a sequence that may consist of Strings and other Tokens.
 */
public interface TokenBuilder {

    /**
     * @param character next character that is added to the token.
     * @return INVALID if input parameter was not added to the token. This may or may not change the status of the token itself.
     *         DONE if the parsing is finished.
     *         PARSING if parsing is ongoing.
     * @throws IllegalStateException may be thrown if current status is not PARSING.
     */
    Status accept(char character);

    /**
     * @return DONE if the token was successfully parsed. This allows to call the string representation of token.
     *         PARSING if the token parsing is not finished.
     *         INVALID if the token cannot be parsed further.
     */
    TokenBuilder.Status getStatus();

    boolean canAccept();

    boolean isValid();

    /**
     * @return String representation of the token if parsing is finished.
     * @throws IllegalStateException if parsing is not finished.
     */
    String asString();

    /**
     * @return String representation of the token if parsing is finished. Otherwise null.
     */
    String toString();

    void reset();

    enum Status {
        PARSING, INVALID, DONE, CONTINUATION;

        public boolean canAccept() {
            switch (this) {
                case PARSING: case CONTINUATION:
                    return true;
                case DONE: case INVALID:
                    return false;
                default: throw new IllegalStateException();
            }
        }

        public boolean isValid() {
            switch (this) {
                case PARSING: case INVALID:
                    return false;
                case DONE: case CONTINUATION:
                    return true;
                default: throw new IllegalStateException();
            }
        }
    }

    static TokenBuilder empty() {
        return new AbstractToken() {
            @Override
            public Status accept(char character) {
                throw new IllegalStateException("Empty token cannot accept characters");
            }

            @Override
            protected void partialReset() {
                // You sure you should be calling this?
            }

            @Override
            public String toString() {
                return "";
            }
        };
    }

}
