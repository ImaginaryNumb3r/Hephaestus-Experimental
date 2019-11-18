package lib;

/**
 * @author Patrick Plieschnegger
 */
public class EnumNotPresentException extends EnumConstantNotPresentException {

    /**
     * Constructs an {@code EnumConstantNotPresentException} for the
     * specified constant.
     *
     * @param enumType     the type of the missing enum constant
     * @param value        the object which was not not expected.
     */
    public EnumNotPresentException(Class<? extends Enum> enumType, Object value) {
        super( enumType, value == null ? "null" : value.toString() );
    }
}
