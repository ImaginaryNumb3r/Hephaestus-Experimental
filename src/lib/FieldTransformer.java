package lib;

/**
 * Creator: Patrick
 * Created: 13.05.2019
 * Purpose:
 */
public interface FieldTransformer {
    FieldTransformer identity = (targetType, currentValue, newValue) -> newValue;

    Object transform(Class<?> targetType, Object currentValue, Object newValue);

}
