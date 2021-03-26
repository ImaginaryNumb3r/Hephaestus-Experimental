package lib.arguments;

/**
 * @author Patrick Plieschnegger
 * TODO: Move to essentials
 */
public interface Copyable<T extends Copyable<T>> {

    T deepCopy();

}
