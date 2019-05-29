package lib.arguments;

/**
 * Creator: Patrick
 * Created: 17.05.2019
 */
public enum  ArgumentOption {
    /**
     * The argument cannot have additional options.
     */
    NONE,
    /**
     * The argument can have arbitrary options (including none).
     */
    ARRAY,
    /**
     * The argument can have up to one options.
     */
    OPTIONAL,
    /**
     * The argument must have exactly one option.
     */
    VALUE;
}
