package lib.arguments;

/**
 * @author Patrick Plieschnegger
 */
public interface Copyable<T extends Copyable<T>> {

    T deepCopy();

}
